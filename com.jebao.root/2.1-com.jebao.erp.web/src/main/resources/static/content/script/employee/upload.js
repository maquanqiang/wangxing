/**
 * Created by Jack on 2016/11/25.
 */

//写在document ready里ajax提交就无效了，不解
$("#upfile").fileinput({
    uploadUrl: '/api/employee/upload', //上传地址
    uploadExtraData:{dir:"file"},//额外请求数据
    allowedFileExtensions: ["xls","xlsx"],
    maxFileSize: 4096,//kb
    minFileCount: 1,
    maxFileCount: 1,
    dropZoneEnabled:false,//不显示拖拽区域
});

//上传成功
$("#upfile").on("fileuploaded", function (event, data, previewId, index) {
    console.log("fileuploaded")

});
//上传完成，不论对错
$('#upfile').on('filebatchuploadcomplete', function (event, previewId, index) {

});


