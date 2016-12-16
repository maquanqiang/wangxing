/**
 * Created by Administrator on 2016/12/14.
 */
//Vue实例
//Model
var model = {
    //银行卡信息
    bankInfo: {}
};

// 创建一个 Vue 实例 (ViewModel),它连接 View 与 Model
var vm = new Vue({
    el: ".account-main",
    data: model,
    beforeCreate: function () {
    },
    //初始化远程数据
    created: function () {
        this.init();
    },
    mounted: function () {
        //this.bindFormValidate();
    },
    //方法，可用于绑定事件或直接调用
    methods: {
        init: function () {
            $.get("/api/user/details", function (response) {
                if (response.success_is_ok) {
                    var data = response.data;
                    console.log(data);
                    if (data != null) {
                        vm.bankInfo = data;
                    }
                }
            });
        },
/*        //绑定表单验证
        bindFormValidate: function () {
           return $("#quickPay_form").bootstrapValidator({
               fields: {
                   money: {
                       validators: {
                           notEmpty: {
                               message: '充值金额不能为空'
                           },
                           numeric: {
                               message: '请输入数字'
                           }
                       }
                   }
               }
            });
              *//*  .on('success.form.bv', function (e) {
                var $form = $(e.target);
                $form.attr("action",common.apiOrigin + $form.attr("action"));
                $form[0].submit();
            });*//*
        },*/
        quickPay: function () {
        /*    var bootstrapValidator = $("#quickPay_form").data('bootstrapValidator').validate();
            if(!bootstrapValidator.isValid()){
                return false;
            }*/
            var $form = $("#quickPay_form");
            $form.attr("action",common.apiOrigin + $form.attr("action"));
            $form[0].submit();
         //   $("#quickPay_form").submit();//必须使用jquery的submit
        },
        fastRecharge: function () {
            $("#fastRecharge_form").submit();//必须使用jquery的submit
        },
        withdrawDeposit: function(){
            $("#withdrawDeposit_form").submit();//必须使用jquery的submit
        }
    }
});