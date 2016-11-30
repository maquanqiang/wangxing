/**
 * Created by Administrator on 2016/11/30.
 */
var model = {
    //文章信息
    article: {typeId:0},//  typeId:0 初始化下拉框的值，默认选中项
    //查询条件
    searchObj: {},
    //文章类别
    aTypes: []
};
// 创建一个 Vue 实例 (ViewModel),它连接 View 与 Model
var vm = new Vue({
    el: ".content",
    data: model,
    beforeCreate:function(){
        //初始化本地数据
        model.searchObj = $("#search_form").serializeObject();
        //文章类别
        $.get("/api/articletype/list",function(response){
            if (response.success_is_ok){
                vm.aTypes=response.data;
            }
        });
    },
    //初始化远程数据
    created: function () {
        this.init();
    },
    mounted:function(){
        this.bindFormValidate();
        $('.chooseDate').datepicker({
            format: 'yyyy-mm-dd',
            weekStart: 1,
            autoclose: true,
            todayBtn: 'linked',
            language: 'cn'
        });
    },
    //方法，可用于绑定事件或直接调用
    methods: {
        init: function() {
            $.get("/api/article/details", model.searchObj, function (response) {
                if (response.success_is_ok) {
                    var data = response.data;
                    if(data != null) {
                        //设置kindEditorContent编辑器
                        KindEditor.html("#kindEditorContent", data.content);
                        vm.article = data;
                    }
                }
            });
        },
        //绑定表单验证
        bindFormValidate:function(){
            $("#order_search_form").bootstrapValidator({
                fields: {
                    title: {
                        message: '文章标题验证失败',
                        validators: {
                            notEmpty: {
                                message: '文章标题不能为空'
                            }
                        }
                    },
                    editDate: {
                        message: '编辑日期验证失败',
                        validators: {
                            notEmpty: {
                                message: '编辑日期不能为空'
                            }
                        }
                    },
                    content: {
                        message: '文章内容验证失败',
                        validators: {
                            notEmpty: {
                                message: '文章内容不能为空'
                            }
                        }
                    },
                    typeId:{
                        validators: {
                            greaterThan: {
                                value: 1,
                                message: '请选择文章类别'
                            }
                        }
                    }
                }
            });
        },
        save: function(){
            $("#btn-save").addClass("btn disabled");
            layer.load(2);
            var bootstrapValidator = $("#order_search_form").data('bootstrapValidator').validate();
            if(!bootstrapValidator.isValid()){
                return false;
            }
            console.log("post111");
            var submitModel = $("#order_search_form").serializeObject();
            console.log(submitModel);
            $.post("/api/article/post",submitModel,function(response){
                console.log("post2222");
                if (response.success_is_ok){
                    layer.msg(response.msg);
                    var targetUrl="/article/index";
                    redirectUrl(targetUrl);
                    return;
                }else{
                    layer.msg(response.msg);
                }
                $("#btn-save").removeClass("disabled");
                layer.closeAll();
            });
        },
        cancel: function(){
            var targetUrl="/article/index";
            redirectUrl(targetUrl);
            return;
        }
    }
});