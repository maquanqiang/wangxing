/**
 * Created by Administrator on 2016/11/28.
 */
var model = {
    //查询条件
    searchObj: {},
    materials: [],
    //弹窗vm实例
    openFormVm:{}
};
// 创建一个 Vue 实例 (ViewModel),它连接 View 与 Model
var vm = new Vue({
    el: ".content",
    data: model,
    beforeCreate:function(){
        //初始化本地数据
        model.searchObj=$("#search_form").serializeObject();
    },
    //初始化远程数据
    created:function(){
        this.search();
    },
    //方法，可用于绑定事件或直接调用
    methods: {
        search: function(){
            $.get("/api/risk/materialsList", model.searchObj, function (response) {
                if (response.success_is_ok) {
                    var data = response.data;
                    vm.materials = data;
                }
            });
        },
        openDeleteWin:function(id){
            layer.confirm('确定要删除吗?', {icon: 3, title:'询问'}, function(index){
                layer.load(2);
                $.post("/api/risk/deleteMaterials",{id:id},function(response){
                    if (response.success_is_ok){
                        layer.msg(response.msg);
                        vm.search();
                    }else{
                        layer.alert(response.msg);
                    }
                });
                layer.closeAll();
            });
        },
        //绑定表单验证
        bindFormValidate:function($form){
            $form.bootstrapValidator({
                fields: {
                    name:{
                        validators: {
                            notEmpty: {
                                message: '材料名称不能为空'
                            }
                        }
                    },
                    remark:{
                        validators: {
                            notEmpty: {
                                message: '备注不能为空'
                            }
                        }
                    }
                }
            });
        },
        add: function($form){
            var $button =$form.parent().parent().children(".layui-layer-btn").children("a:first");
            $button.addClass("btn disabled");
            layer.load(2);
            var submitModel = $form.serializeObject();
            $.post("/api/risk/addMaterials",submitModel,function(response){
                if (response.success_is_ok){
                    layer.msg(response.msg);
                    vm.search();
                }else{
                    vm.openFormVm.error.hide=false;
                    vm.openFormVm.error.message=response.msg;
                }
                $button.removeClass("disabled");
                layer.closeAll();
            });
        },
        createOpenVm:function(form,id){
            var openVmModel ={
                form:form,
                formData:$(form).serializeObject(),
                error:{hide:true,message:""}
            };
            openVmModel.formData.id=id;
            vm.openFormVm = new Vue({
                el: openVmModel.form,
                data: openVmModel,
                beforeCreate:function(){
                    var id =openVmModel.formData.id;
                    //填充窗体数据
                    if (id>0){
                        for (var i=0;i<vm.materials.length;i++){
                            var item =vm.materials[i];
                            if (item.id==id){
                                openVmModel.formData.projectId = item.projectId;
                                openVmModel.formData.no = item.no;
                                openVmModel.formData.name = item.name;
                                openVmModel.formData.idNumber = item.idNumber;
                                openVmModel.formData.remark = item.remark;
                                openVmModel.formData.path = item.path;
                                openVmModel.formData.url = item.url;
                                openVmModel.formData.createTime = item.createTime;
                            }
                        }
                    }
                },
                created:function(){

                },
                mounted:function(){
                    var $form = $(openVmModel.form);
                    vm.bindFormValidate($form);
                    $form.find(".btn-fileupload").wrap("<form id='_myUpload_' action='/filePlugin/uploadFile?dir=image'method='post' enctype='multipart/form-data'></form>");
                },
                methods: {
                    fileupload: function(){
                        var fileUploadUrl = $(openVmModel.form).find(".uploadFileUrl");
                        $("#_myUpload_").ajaxSubmit({
                            dataType:  'json', //数据格式为json
                            success:function(data){
                                if(data)
                                {
                                    if(data.error==0)
                                    {
                                        fileUploadUrl.val(data.url);
                                        return;
                                    }
                                    alert(data.message);
                                    return;
                                }
                                alert("--上传失败---");
                                //console.log("上传失败");
                                return;
                            },
                            error:function(xhr){
                                alert(fileUploadUrl.html());
                                alert(xhr.responseText);
                            }
                        });
                    }
                }
            });
        },
        openAddForm:function(id){
            if (isNaN(id)){id=0;}
            var tempObj= $('#addMaterialModal').clone();
            tempObj.find('form').prop('id','insertFormId');
            var tempHtml=tempObj.html();
            layer.open({
                title:'添加材料',
                content:tempHtml,
                btn: ['添加', '取消'],
                area:['500px'],
                btn1: function(){
                    var $form = $("#insertFormId");
                    var bootstrapValidator = $form.data('bootstrapValidator').validate();
                    if(!bootstrapValidator.isValid()){
                        return false;
                    }else{
                        vm.add($form);
                    }
                },
                btn2: function(){
                    layer.closeAll();
                    return false;
                }
            });
            vm.createOpenVm("#insertFormId",id);
        },
        openViewForm:function(id){
            if (isNaN(id)){id=0;}
            var tempObj= $('#viewMaterialModal').clone();
            tempObj.find('form').prop('id','ViewFormId');
            var tempHtml=tempObj.html();
            layer.open({
                title:'预览材料',
                content:tempHtml,
                btn: ['确定'],
                area:['600px','600px'],
                btn1: function(){
                    layer.closeAll();
                }
            });
            vm.createOpenVm("#ViewFormId",id);
        }
    }
});