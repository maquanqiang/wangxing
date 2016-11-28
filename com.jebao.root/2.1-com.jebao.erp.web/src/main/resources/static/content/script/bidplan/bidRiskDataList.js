/**
 * Created by lee on 2016/11/18.
 */
$(function () {

    $(".select2").select2();
})


//var autoObj = {
//    addInsuredFormValidate: function () {
//        var $form = $("#form-inline");
//        $form.validate({
//            rules: {
//                Name: {
//                    required: true,
//                    realName: $("#insured_card_type")
//                },
//                CardNo: {
//                    required: true,
//                    idCard: $("#insured_card_type")
//                },
//                Birthday: {
//                    required: true,
//                    date: true
//                },
//                Gender: {
//                    required: true
//                }
//            },
//            messages: {
//                Name: {
//                    required: "请输入被保险人姓名",
//                    realName: "请输入正确的被保险人姓名"
//                },
//                CardNo: {
//                    required: "请输入被保险人证件号码"
//                },
//                Birthday: {
//                    required: "请输入被保险人出生日期"
//                },
//                Gender: {
//                    required: "请选择性别"
//                }
//            }
//
//        });
//    },
//    BindEvent: function () {
//
//    },
//    ButtonEvent: function () {
//
//    }
//};
//for (var prop in autoObj) {
//    if (typeof autoObj[prop] === "function") {
//        //autoObj[prop].apply();
//    }
//}

//Vue实例
//Model
var model = {
    //查询条件
    searchObj: {},
    //列表
    riskDataList: []
};

// 创建一个 Vue 实例 (ViewModel),它连接 View 与 Model
var vm = new Vue({
    el: ".content",
    data: model,
    beforeCreate: function () {

        //初始化本地数据
        model.searchObj.page = 0;
        model.searchObj.rows = 10;
    },
//初始化远程数据
    created: function () {
        this.search();

    },
//方法，可用于绑定事件或直接调用
    methods: {
        //表单登录验证封装
        myInitValidateForm: function (obj) {
            obj.bootstrapValidator({
                fields: {
                    name: {
                        validators: {
                            notEmpty: {
                                message: '材料名称不能为空'
                            }
                        }
                    },
                    remark: {
                        validators: {
                            notEmpty: {
                                message: '备注不能为空'
                            }
                        }
                    }
                }
            });
        },
        search: function (event) {
            var bpId = $("#bpId").val();
            model.searchObj.bpId = bpId;
            $.get("/api/bidRiskData/getRiskDataListForPage", model.searchObj, function (response) {
                if (response.success_is_ok) {
                    vm.riskDataList = response.data;
                    console.log(response.count);
                    if (response.count > 0) {
                        var pageCount = Math.ceil(response.count / model.searchObj.rows);
                        //调用分页
                        laypage({
                            cont: $('#pageNum'), //容器。值支持id名、原生dom对象，jquery对象,
                            pages: pageCount, //总页数
                            curr: model.searchObj.page + 1,
                            groups: 7, //连续显示分页数
                            jump: function (obj, first) { //触发分页后的回调
                                if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                                    console.log(obj.curr);
                                    vm.searchObj.page = obj.curr - 1;
                                    vm.search();
                                }
                            },
                            skin: '#3c8dbc'
                        });
                    }
                }
            })
        },
        removeBtn: function (id) {
            layer.confirm('确定要删除吗?', {icon: 3, title: '询问'}, function (index) {
                layer.load(2);
                $.post("/api/bidRiskData/removeRiskData", {id: id}, function (response) {
                    if (response.success_is_ok) {
                        layer.msg(response.msg);
                        vm.search();
                    } else {
                        layer.alert(response.msg);
                    }
                });
                layer.closeAll();
            });
        },
        //添加材料
        addBtn: function () {
            var tempObj = $('#addMaterialModal').clone();
            tempObj.find('form').prop('id', 'insertFormId');
            var tempHtml = tempObj.html();
            var pid = $("#defaultForm").find("[name=bpId]").val();
            layer.open({
                title: '添加材料',
                content: tempHtml,
                btn: ['添加', '取消'],
                area: ['500px'],
                btn1: function () {
                    vm.myInitValidateForm($('#insertFormId'));
                    var bootstrapValidator = $("#insertFormId").data('bootstrapValidator').validate();
                    if (!bootstrapValidator.isValid()) {
                        return false;
                    } else {
                        //TODO 后台逻辑
                        $.axForForm($('#insertFormId'), function (response) {
                            if (response.success_is_ok) {
                                layer.msg(response.msg);
                                vm.search();
                            } else {
                                layer.alert(response.msg);
                            }
                        });
                    }
                },
                btn2: function () {
                    layer.close();
                }
            });
            $("#insertFormId #fileupload").wrap("<form id='_myUpload_' action='/filePlugin/uploadFile?dir=image'method='post' enctype='multipart/form-data'></form>");
            $("#insertFormId #fileupload").change(function () {
                var fileUploadUrl = $('#insertFormId #uploadFileUrl');
                $("#_myUpload_").ajaxSubmit({
                    dataType: 'json', //数据格式为json
                    success: function (data) {
                        if (data) {
                            if (data.error == 0) {
                                //  alert(data.url);
                                fileUploadUrl.val(data.url);
                                return;
                            }
                            alert(data.message)
                            return;
                        }
                        alert("--上传失败---")
                        return;
                    },
                    error: function (xhr) {
                        alert(fileUploadUrl.html());
                        alert(xhr.responseText);
                    }
                });
            });
        },
        //预览材料图片
        viewBtn: function (bpId) {
            var tempObj= $('#viewMaterialModal').clone();
            tempObj.find('form').prop('id','ViewFormId');
            var tempHtml=tempObj.html();
            layer.open({
                title:'预览材料',
                content:tempHtml,
                btn: ['确定'],
                area:['500px'],
                btn1: function(){
                    layer.closeAll();
                }
            });
            /*加载材料*/
            $.get("/api/bidRiskData/getMaterials","bpId="+bpId,function(response){
                if (response.success_is_ok){
                    if (response.data != null){
                        var model=response.data;
                        var html= dataToHtml('#Template-Info', model);
                        $('#ViewFormId #Template-InfoView').html(html);
                    }
                }
            });
        }
    }
});

//$("#orderlist_table").on("click",'#modifyInformation',function(){
//    var that = $(this); //解决方案
//    var dataVal=that.attr('data-val');//自定义属性
//    window.location.href = "/bidRiskData/bidRiskDataView/"+dataVal;
//});
//$("#orderlist_table").on("click",'#modifyRiskData',function(){
//    var that = $(this); //解决方案
//    var dataVal=that.attr('data-val');//自定义属性
//    window.location.href = "/bidRiskData/bidRiskDataView/"+dataVal;
//});
