/**
 * Created by Jack on 2016/11/18.
 */
$(function () {

    $(".select2").select2();

    var autoObj = {
        addInsuredFormValidate: function () {
            var $form = $("#insured_form");
            $form.validate({
                rules: {
                    Name: {
                        required: true,
                        realName: $("#insured_card_type")
                    },
                    CardNo: {
                        required: true,
                        idCard: $("#insured_card_type")
                    },
                    Birthday: {
                        required: true,
                        date: true
                    },
                    Gender: {
                        required: true
                    }
                },
                messages: {
                    Name: {
                        required: "请输入被保险人姓名",
                        realName: "请输入正确的被保险人姓名"
                    },
                    CardNo: {
                        required: "请输入被保险人证件号码"
                    },
                    Birthday: {
                        required: "请输入被保险人出生日期"
                    },
                    Gender: {
                        required: "请选择性别"
                    }
                },

            });
        },
        BindEvent: function () {

        },
        ButtonEvent: function () {

        }
    };
    for (var prop in autoObj) {
        if (typeof autoObj[prop] === "function") {
            //autoObj[prop].apply();
        }
    }
});
//Vue实例
//Model
var model = {
    //查询条件
    searchObj: $("#search_form").serializeObject(),
    //所属团队
    teams: [],
    //销售级别
    ranks: [],

};

// 创建一个 Vue 实例 (ViewModel),它连接 View 与 Model
var vm = new Vue({
    el: "#content",
    data: model,
    //初始化远程数据
    created:function(){
        //团队
        $.get("/api/department/list",function(response){
            if (response.success_is_ok){
                vm.teams=response.data;
            }
        });
        //销售级别
        $.get("/api/rank/list",function(response){
            if (response.success_is_ok){
                vm.ranks=response.data;
            }
        });
    },
    //方法，可用于绑定事件或直接调用
    methods: {
        search:function(event){
            $(event.target).addClass("disabled");//禁用按钮
            $.get("/api/employee/list",model.searchObj,function(response){


                $(event.target).removeClass("disabled");//解除禁用
            });
        }
    }
});
