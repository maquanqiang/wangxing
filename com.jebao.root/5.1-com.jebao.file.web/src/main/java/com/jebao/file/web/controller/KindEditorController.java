package com.jebao.file.web.controller;

import com.jebao.file.web.responseModel.UploadReturnJson;
import com.jebao.file.web.utils.constants.Constants;
import com.jebao.file.web.utils.constants.ProjectSetting;
import com.jebao.file.web.utils.file.FileUtil;
import com.jebao.file.web.utils.map.MapUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * KindEditor-文件上传插件
 * Created by Administrator on 2016/11/17.
 */
@RestController
@RequestMapping("/kindEditor/")
public class KindEditorController {
    public static final String FILE_UPLOAD_SERVICE_URL = Constants.FILE_UPLOAD_SERVICE_URL;
    public static final String FILE_UPLOAD_KEY = Constants.FILE_UPLOAD_KEY;
    // 图片的存储路径：public static final String ROOT = "upload-dir";//上传文件的根目录--相对地址
    public static final String ROOT = Constants.FILE_UPLOAD_DIR;//上传文件的根目录--绝对地址
    private final ResourceLoader resourceLoader;
    private static final HashMap<String, String> FILE_PATH_MAP = getFilePathMap();
    private static final HashMap<String, String> FILE_EXT_MAP = getFileExtMap();

    @Autowired
    public KindEditorController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @RequestMapping("uploadFile")
    public UploadReturnJson uploadFile(String dir,String key, HttpServletRequest request) {
        if(StringUtils.isBlank(key)||!key.equals(FILE_UPLOAD_KEY))
        {
            return new UploadReturnJson(1,"上传密钥不正确");
        }
        UploadReturnJson errorResult = filterRequest(dir, request);
        if (errorResult != null) {
            return errorResult;
        }
        //
        Map<String, MultipartFile> multipartFileMap = ((MultipartHttpServletRequest) request).getFileMap();
        MultipartFile file = MapUtil.getFirstOrNull(multipartFileMap);
        String fileName = UUID.randomUUID().toString() + FileUtil.getFileExtension(file);
        String fileDir = FILE_PATH_MAP.get(dir);
        String filePath = Paths.get(ROOT, fileDir).toString();
        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(FileUtil.getFileOutputStream(filePath, fileName));
            stream.write(bytes);
            stream.close();
            String fileUrl=String.format("%s/kindEditor/file/%s/%s",FILE_UPLOAD_SERVICE_URL,dir,fileName);
            return new UploadReturnJson(fileUrl);
        } catch (Exception e) {
            return new UploadReturnJson(1, e.getMessage());
        }
    }

    /**
     * 图片读取
     */
    @RequestMapping(method = RequestMethod.GET, value = "/file/{dir}/{filename:.+}")
    public ResponseEntity<?> readFile(@PathVariable String dir, @PathVariable String filename) {

        //todo 调试输入图片的保存路径
        //String savePath=Paths.get(ROOT,IMG_PATH,imgPath ,filename).toAbsolutePath().toString();
        //System.out.println(savePath);
        String fileDir = FILE_PATH_MAP.get(dir);
        if (fileDir == null) {
            return new ResponseEntity<String>("目录不存在.", HttpStatus.NOT_FOUND);
        }
        if (filename == null) {
            return new ResponseEntity<String>("文件名不能为空.", HttpStatus.NOT_FOUND);
        }
        String filePath = Paths.get(ROOT, fileDir, filename).toString();
        try {
            Resource resource = resourceLoader.getResource("file:" + filePath);
            if (!resource.exists()) {
                return new ResponseEntity<String>("File Not found.", HttpStatus.NOT_FOUND);
                //return ResponseEntity.notFound().build();
            }
            //如果是文件则是下载
            if (dir.equals("file")) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", filename));
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                return ResponseEntity
                        .ok()
                        .headers(headers)
                        .contentLength(resource.contentLength())
                        .contentType(MediaType.parseMediaType("application/octet-stream"))
                        .body(resource);
            }
            return ResponseEntity.ok(resourceLoader.getResource("file:" + filePath));
        } catch (Exception e) {
            return new ResponseEntity<String>("Exception Not found.", HttpStatus.NOT_FOUND);
        }
    }

    //过滤请求
    private UploadReturnJson filterRequest(String dir, HttpServletRequest request) {
        //
        if (!FILE_EXT_MAP.containsKey(dir)) {
            return new UploadReturnJson(1, "目录不存在");
        }
        //
        Boolean isMultipartFile = request instanceof StandardMultipartHttpServletRequest;
        if (!isMultipartFile) {
            //过滤非MultipartFile的请求
            return new UploadReturnJson(1, "request is not StandardMultipartHttpServletRequest object");
        }
        //
        Map<String, MultipartFile> multipartFileMap = ((MultipartHttpServletRequest) request).getFileMap();
        MultipartFile file = MapUtil.getFirstOrNull(multipartFileMap);
        if (file == null) {
            return new UploadReturnJson(1, "当前文件为空，请选择文件");
        }
        //
        String fileExt = FileUtil.getFileExtension(file).replace(".", "");
        if (!Arrays.asList(FILE_EXT_MAP.get(dir).split(",")).contains(fileExt)) {
            String msg = "上传文件扩展名" + fileExt + "是不允许的扩展名,目前只允许" + FILE_EXT_MAP.get(dir) + "格式";
            return new UploadReturnJson(1, msg);
        }
        //
        return null;
    }
    private static HashMap<String, String> getFileExtMap() {
        // 定义允许上传的文件扩展名
        HashMap<String, String> extMap = new HashMap<String, String>();
        extMap.put("image", "gif,jpg,jpeg,png,bmp");
        extMap.put("flash", "swf,flv");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
        return extMap;
    }

    private static HashMap<String, String> getFilePathMap() {
        // 定义允许上传的文件保存路径
        HashMap<String, String> pathMap = new HashMap<String, String>();
        pathMap.put("image", "kindEditorFile\\image\\p0");
        pathMap.put("file", "kindEditorFile\\file\\p0");
        return pathMap;
    }
}
