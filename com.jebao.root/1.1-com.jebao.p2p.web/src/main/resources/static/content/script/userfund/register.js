var model = {
    formSelector: "#defaultForm",
    form: {},
    banks: {},
    regions: [],
    cities:[],
    countries:[],
    error: {hasError: false, message: "提交错误信息显示的地方"}
};
var vm = new Vue({
    el: "#defaultForm",
    data: model,
    beforeCreate: function () {
        model.form = $(model.formSelector).serializeObject();
        $.get("/api/data/bankList", function (response) {
            if (response.success_is_ok) {
                model.banks = response.data;
            }
        });
        $.get("/api/data/regionList", function (response) {
            if (response.success_is_ok) {
                model.regions = response.data;
            }
        });
    },
    mounted: function () {
        this.initValidateForm();
    },
    watch: {
        "form.bankProvinceCode": function (val, oldVal) {
            for (var i=0;i<model.regions.length;i++){
                if(model.regions[i].code === val){
                    model.cities = model.regions[i].children;
                    break;
                }
            }
        },
    },
    methods: {
        initValidateForm: function () {
            return $('#defaultForm').bootstrapValidator({
                    fields: {
                        realName: {
                            threshold: 2,
                            validators: {
                                notEmpty: {
                                    message: '请输入真实姓名'
                                },
                                regexp: {
                                    regexp: /^[\u4e00-\u9fa5]+(·[\u4e00-\u9fa5]+)*$/,
                                    message: '真实姓名只能是中文'
                                }
                            }
                        },
                        idCard: {
                            validators: {
                                notEmpty: {
                                    message: '请输入身份证号'
                                },
                                idCard: {
                                    message: '请输入正确的身份证号'
                                }
                            }
                        },
                        bankCode: {
                            validators: {
                                notEmpty: {
                                    message: '请选择银行'
                                }
                            }
                        },
                        bankProvinceCode: {
                            validators: {
                                greaterThan: {
                                    value: 0,
                                    inclusive: false,
                                    message: '请选择开户行省市'
                                }
                            }
                        },
                        bankCityCode: {
                            validators: {
                                greaterThan: {
                                    value: 0,
                                    inclusive: false,
                                    message: '请选择开户行区县'
                                }
                            }
                        },
                        bankCardNo: {
                            validators: {
                                notEmpty: {
                                    message: '请输入银行卡号'
                                },
                                bankCard:{
                                    message:'银行卡号输入错误'
                                }
                            }
                        },
                        payPassword: {
                            validators: {
                                notEmpty: {
                                    message: '请输入支付密码'
                                },
                                regexp: {
                                    regexp: /^(?![0-9]+$)(?![a-zA-Z]+$)[A-Za-z0-9_-]{8,20}$/,
                                    message: '请设置8-20位密码（由字母、数字组成）'
                                }
                            }
                        },
                        payPasswordAgain: {
                            validators: {
                                notEmpty: {
                                    message: '请输入支付确认密码'
                                },
                                identical: {
                                    field: 'payPassword',
                                    message: '两次密码不一致'
                                }
                            }
                        },

                    }
                })
                .on('success.form.bv', function (e) {
                    e.preventDefault();

                    var $form = $(e.target);

                    $.post($form.attr('action'), $form.serializeObject(), function (response) {
                        if (response.success_is_ok) {
                            window.location.href = "/userfund/registerSuccess";
                            return;
                        } else {
                            layer.alert(response.error);
                        }
                    }, "json");

                });
        },
        submit: function () {
            $(model.formSelector).submit();//必须使用jquery的submit
        }
    }
});