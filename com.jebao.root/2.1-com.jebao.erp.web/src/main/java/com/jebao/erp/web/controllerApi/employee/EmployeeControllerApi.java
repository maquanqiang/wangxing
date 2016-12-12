package com.jebao.erp.web.controllerApi.employee;

import com.jebao.erp.service.inf.employee.IDepartmentServiceInf;
import com.jebao.erp.service.inf.employee.IEmployeeServiceInf;
import com.jebao.erp.service.inf.employee.IRankServiceInf;
import com.jebao.erp.web.controller.FilePluginController;
import com.jebao.erp.web.controller._BaseController;
import com.jebao.erp.web.responseModel.base.JsonResult;
import com.jebao.erp.web.responseModel.base.JsonResultList;
import com.jebao.erp.web.responseModel.employee.EmployeeVM;
import com.jebao.erp.web.utils.excel.ExcelUtil;
import com.jebao.erp.web.utils.session.CurrentUser;
import com.jebao.erp.web.utils.session.LoginSessionUtil;
import com.jebao.erp.web.utils.validation.ValidationResult;
import com.jebao.erp.web.utils.validation.ValidationUtil;
import com.jebao.jebaodb.entity.employee.EmployeeInfo;
import com.jebao.jebaodb.entity.employee.TbDepartment;
import com.jebao.jebaodb.entity.employee.TbRank;
import com.jebao.jebaodb.entity.employee.input.EmployeeIM;
import com.jebao.jebaodb.entity.employee.search.DepartmentSM;
import com.jebao.jebaodb.entity.employee.search.EmployeeSM;
import com.jebao.jebaodb.entity.employee.search.RankSM;
import com.jebao.jebaodb.entity.extEntity.ResultData;
import com.jebao.jebaodb.entity.extEntity.ResultInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jack on 2016/11/16.
 */
@RestController
@RequestMapping("api/employee/")
public class EmployeeControllerApi extends _BaseController {

    @Autowired
    private IEmployeeServiceInf employeeService;
    @Autowired
    private IDepartmentServiceInf departmentService;
    @Autowired
    private IRankServiceInf rankService;

    @RequestMapping("list")
    public JsonResult list(EmployeeSM model) {
        if (model==null){return new JsonResultList<>(null);}

        List<EmployeeInfo> employeeInfoList = employeeService.getEmployeeInfoList(model);
        List<EmployeeVM> viewModelList = new ArrayList<>();
        employeeInfoList.forEach(o -> viewModelList.add(new EmployeeVM(o)));

        int count=0;
        if (model.getPageIndex()==0){
            count = employeeService.getEmployeeInfoListCount(model);
        }

        return new JsonResultList<>(viewModelList,count);
    }
    @RequestMapping(value = "post",method = RequestMethod.POST)
    public ResultInfo post(EmployeeIM model){
        //region 校验
        ValidationResult resultValidation = ValidationUtil.validateEntity(model);
        if (resultValidation.isHasErrors()) {
            return new ResultInfo(false,resultValidation.getErrorMsg().toString());
        }
        if (model!=null){
            CurrentUser user = LoginSessionUtil.User(request,response);
            model.setUserId(user.getId());
        }
        ResultInfo resultInfo = employeeService.saveEmployeeInfo(model);

        return resultInfo;
    }
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public ResultInfo delete(int empId){
        CurrentUser user = LoginSessionUtil.User(request,response);
        ResultInfo resultInfo = employeeService.deleteEmployeeInfo(empId,user.getId());
        return resultInfo;
    }

    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public ResultInfo upload(){
        FilePluginController fileUploader = new FilePluginController(null);
        FilePluginController.UploadReturnJson returnJson = fileUploader.uploadFile("file",request);

        if (returnJson.getError()==0){
            String filename = returnJson.getUrl().substring(returnJson.getUrl().lastIndexOf("/")+1);
            String filePath = Paths.get(FilePluginController.ROOT, "projectFile\\file\\p0",filename).toString();
            List<Object[]> rowList = new ExcelUtil().readFile(filePath);
            return new ResultData<List<Object[]>>(rowList,filename);
        }else{
            return new ResultInfo(false,returnJson.getMessage());
        }
    }
    @RequestMapping(value = "uploadconfirm",method = RequestMethod.POST)
    public ResultInfo uploadconfirm(String filename){
        String filePath = Paths.get(FilePluginController.ROOT, "projectFile\\file\\p0",filename).toString();
        List<HashMap<String,Object>> mapList =new ExcelUtil().readFileToKv(filePath);
        List<EmployeeIM> modelList = new ArrayList<>();

        for (int i=0;i<mapList.size();i++){
            HashMap<String,Object> item = mapList.get(i);

            //region 检测存在
            String cardNo =item.get("身份证号").toString();
            EmployeeSM searchModel = new EmployeeSM();
            searchModel.setCardNo(cardNo);
            List<EmployeeInfo> employeeList = employeeService.getEmployeeInfoList(searchModel);
            if (employeeList!=null && employeeList.size()>0){
                continue;
            }
            String mobile =item.get("手机号").toString();
            EmployeeSM searchModel2 = new EmployeeSM();
            searchModel2.setMobile(mobile);
            employeeList = employeeService.getEmployeeInfoList(searchModel2);
            if (employeeList!=null && employeeList.size()>0){
                continue;
            }
            //endregion
            EmployeeIM model = new EmployeeIM();
            model.setName(item.get("员工姓名").toString());
            model.setMobile(mobile);
            model.setCardNo(cardNo);
            RankSM rankSM = new RankSM();
            rankSM.setName(item.get("员工职级").toString());
            List<TbRank> rankList = rankService.getRankList(rankSM);
            if (rankList == null || rankList.size() == 0){
                return new ResultInfo(false,"员工:"+model.getName()+" 数据错误:员工职级填写错误");
            }
            model.setRankId(rankList.get(0).getRankId());
            DepartmentSM departmentSM = new DepartmentSM();
            String teamName = item.get("所属团队").toString();
            if(StringUtils.isBlank(teamName)){
                teamName=item.get("所属部门").toString();
            }
            departmentSM.setName(teamName);
            List<TbDepartment> departmentList = departmentService.getDepartmentList(departmentSM);
            if (departmentList == null || departmentList.size() == 0){
                return new ResultInfo(false,"员工:"+model.getName()+" 数据错误:员工所属部门/团队填写错误");
            }
            model.setDepartmentId(departmentList.get(0).getDepId());
            model.setTeamId(departmentList.get(0).getDepId());
            //region 校验
            ValidationResult resultValidation = ValidationUtil.validateEntity(model);
            if (resultValidation.isHasErrors()) {
                return new ResultInfo(false,"员工:"+model.getName()+" 数据错误:"+resultValidation.getErrorMsg().toString());
            }
            modelList.add(model);
        }
        int existsNum =mapList.size()-modelList.size();
        int successNum = 0;
        for (int i=0;i<modelList.size();i++){
            ResultInfo saveResult = employeeService.saveEmployeeInfo(modelList.get(i));
            if (saveResult.getSuccess_is_ok()){
                successNum++;
            }else{
                return new ResultInfo(false,"已导入"+successNum+"条。"+modelList.get(i).getName()+" 出现错误："+saveResult.getMsg());
            }
        }
        return new ResultInfo(true,"执行完毕，导入数据"+successNum+"条。已存在："+existsNum+"条" );
    }


    @RequestMapping("test")
    public List<Object[]> test() {
        List<Object[]> list = new ExcelUtil().readFile("D:\\test.xlsx");
        return list;
    }


}
