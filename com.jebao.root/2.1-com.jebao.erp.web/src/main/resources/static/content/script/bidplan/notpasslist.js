/**
 * Created by Lee on 2016/11/17.
 */

/**
 * Created by Jack on 2016/11/18.
 */
$(function () {

    $(".select2").select2();

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
});
//Vue实例
//Model
var model = {
    //查询条件
    search: {},
    //列表
    planlist: [],
    //销售级别
    ranks: []

};

// 创建一个 Vue 实例 (ViewModel),它连接 View 与 Model
var vm = new Vue({
    el: ".content",
    data: model,
    beforeCreate:function(){
        //初始化本地数据
        model.search = $("#order_search_form").serializeObject(); //初始化 model.search 对象
    },
    //初始化远程数据
    created:function(){
        var model = $("#order_search_form").serializeObject();
        $.get("/bidplan/dplan/getlist",model,function(response){
            if (response.success_is_ok){
                vm.planlist=response.data;
            }
        });

        /*$.get('/bidplan/dplan/getlist', model, function(response){ //从第1页开始请求。返回的json格式可以任意定义
            laypage({
                cont: $('#pageNum'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                pages: response.pages, //通过后台拿到的总页数
                curr: 1, //初始化当前页
                jump: function(e){ //触发分页后的回调
                    $.get('/bidplan/dplan/getlist', {curr: e.curr}, function(response){
                        e.pages = e.last = response.pages; //重新获取总页数，一般不用写
                        //渲染
                        vm.planlist=response.data;
                    });
                }
            });
        });*/
    },
    //方法，可用于绑定事件或直接调用
    methods: {
        search:function(event){
            var model = $("#order_search_form").serializeObject();
            $.get("/bidplan/dplan/getlist",model,function(response){
                console.log("model")
                if (response.success_is_ok){
                    vm.planlist=response.data;
                }
            })
        }
    }
});

$("#orderlist_table").on("click",'.cmd-delete',function(){
    var that = $(this); //解决方案
    var dataVal=that.attr('data-val');//自定义属性
    layer.open({
        content:'您是否删除信息?',
        btn: ['取消', '删除'],
        btn1: function(){
            layer.closeAll();
        },
        btn2: function(){
            window.location.href = "/bidplan/dplan/remove?bpId="+dataVal
            layer.msg('删除成功!');
        }
    });
});


