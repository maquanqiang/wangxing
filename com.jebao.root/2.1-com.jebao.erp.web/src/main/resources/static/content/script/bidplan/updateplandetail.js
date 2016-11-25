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
    //
    plan: {},
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
        var dataVal = $("#defaultForm").serializeObject();
        console.log("请求"+dataVal);
        $.get("/bidplan/getBidPlanById",dataVal,function(response){
            if (response.success_is_ok){
                var data=response.data;
                vm.plan=data;
                $('#productTypeBtn').select2().select2("val", data.bpType);
                $('#cycleTypeBtn').select2().select2("val", data.bpCycleType);
                $('#bpInterestPayTypeBtn').select2().select2("val", data.bpInterestPayType);
            }
        });
    },
    //方法，可用于绑定事件或直接调用
    methods: {
        search:function(event){
            var model = $("#order_search_form").serializeObject();
            $.get("/bidplan/dplan/getlist",model,function(response){
                if (response.success_is_ok){
                    vm.planlist=response.data;
                }
            })
        }
    }
});
$("#submitBtn").click(function () {
    //TODO 后台逻辑
    $.axForForm($('#defaultForm'), function (data) {
        if (data.success_is_ok) {
            var targetUrl = "/bidplan/notPassList"
            redirectUrl(targetUrl)
            return;
        } else {
            errorHandlerFun(data, "#error_place_id");
        }
    });
});