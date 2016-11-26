/**
 * Created by Jack on 2016/11/25.
 */

//写在document ready里ajax提交就无效了，不解
$("#upfile").fileinput({
    uploadAsync: true,
    uploadUrl: '/filePlugin/uploadFile', // you must set a valid URL here else you will get an error
    uploadExtraData:{dir:"file"},//额外请求数据
    allowedFileExtensions: null,
    maxFileSize: 1000,
    minFileCount: 1,
    maxFileCount: 1,
    previewFileIcon: '<i class="fa fa-file"></i>',
    previewFileIconSettings: {
        'docx': '<i class="fa fa-file-word-o text-primary"></i>',
        'xlsx': '<i class="fa fa-file-excel-o text-success"></i>',
        'pptx': '<i class="fa fa-file-powerpoint-o text-danger"></i>',
        'jpg': '<i class="fa fa-file-photo-o text-warning"></i>',
        'pdf': '<i class="fa fa-file-pdf-o text-danger"></i>',
        'zip': '<i class="fa fa-file-archive-o text-muted"></i>',
    },
    dropZoneEnabled:false,//不显示拖拽区域
    slugCallback: function (filename) {
        console.log("filename:" + filename)
        return filename.replace('(', '_').replace(']', '_');
    }
});
//filebatchselected  fileuploaded
$("#upfile").on("fileuploaded", function (event, data, previewId, index) {
    console.log("fileuploaded")
    console.log(arguments.length)
    for (var i = 0; i < arguments.length; i++) {
        console.log(arguments[i])
    }
});
$('#upfile').on('fileerror', function(event, data, msg) {
    console.log("fileerror")
    console.log(arguments.length)
    for (var i = 0; i < arguments.length; i++) {
        console.log(arguments[i])
    }
});

$('#upfile').on('fileselect', function (event, numFiles, label) {
    console.log("fileselect");
});
$('#upfile').on('filebatchuploaderror', function (event, previewId) {
    console.log("filebatchuploaderror");
});
$('#upfile').on('filebatchpreupload', function (event, previewId, index) {
    console.log('filebatchpreupload');
    console.log(previewId.response)
});
$('#upfile').on('filebatchuploadsuccess', function (event, previewId, index) {
    console.log('filebatchuploadsuccess');
});
$('#upfile').on('filebatchuploadcomplete', function (event, previewId, index) {
    console.log('filebatchuploadcomplete');
    for (var i = 0; i < arguments.length; i++) {
        console.log(arguments[i])
    }
});
