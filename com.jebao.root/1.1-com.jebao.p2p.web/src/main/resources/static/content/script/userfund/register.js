var model = {
    formSelector: "#defaultForm",
    form: {},
    banks: {},
    regions: [],
    cities:[],
    countries:[],
    error: {hasError: false, message: "提交错误信息显示的地方"},
    validBankCardNoArray:[] //校验正确的银行卡号缓存
};
var vm = new Vue({
    el: "#defaultForm",
    data: model,
    beforeCreate: function () {
        model.form = $(model.formSelector).serializeObject();
        model.form.bankCardNoFormat = "";
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
        $('.bankCard input').keyup(function () {
            var value=$(this).val().replace(/\s/g,'').replace(/(\d{4})(?=\d)/g,"$1 ");
            $(this).val(value);
        });
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
        "form.bankCardNoFormat":function(val,oldVal){
            model.form.bankCardNo = model.form.bankCardNoFormat.replace(/\s/g,'');
        }
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
                                callback: {
                                    message: '请输入正确的储蓄卡号',
                                    callback: function(value, validator) {
                                        return true;
                                    }
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
                    var loadIndex = layer.load(2);
                    $.post($form.attr('action'), model.form, function (response) {
                        if (response.success_is_ok) {
                            window.location.href = "/userfund/registerSuccess";
                            return;
                        } else {
                            layer.close(loadIndex);
                            layer.alert(response.error);
                        }
                    }, "json");

                })
                .on('success.field.bv',function(e,data){
                    if (data.field == "bankCardNo" && data.result){
                        vm.getBankCardInfo();
                    }
                });
        },
        submit: function () {
            $(model.formSelector).submit();//必须使用jquery的submit
        },
        validateBankCard:function(){
            var bankCardNo = event.target.value.replace(/\s/g,'');
            if (bankCardNo.length<16){
                $("#defaultForm").data('bootstrapValidator').updateStatus("bankCardNo","INVALID","callback");
                return false;
            }
            jQuery.ajax({
                url: "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json",
                dataType: "jsonp",
                jsonp: "_callback",
                timeout: 10000,
                data: '_input_charset=utf-8&cardNo=' + bankCardNo + '&cardBinCheck=true',
                success: function(data){
                    var isValid = false;
                    if (data.validated && data.cardType == 'DC'){ //cardType: DC储蓄卡, CC信用卡
                        model.validBankCardNoArray.push(bankCardNo);
                        isValid=true;
                    }
                    $("#defaultForm").data('bootstrapValidator').updateStatus("bankCardNo",isValid?"VALID":"INVALID","callback");
                },
                error: function(xhr, status, error){
                    //$("#defaultForm").data('bootstrapValidator').enableFieldValidators("bankCardNo",false);
                    $("#defaultForm").data('bootstrapValidator').updateStatus("bankCardNo","VALID","callback");
                }
            });
        },
        getBankCardInfo:function(){
            var bankCardNo = model.form.bankCardNo;
            $.get("/api/userfund/getBankCardInfo?bankCardNo="+bankCardNo,function(response){
                if (response.success_is_ok) {
                    var bankInfo = response.data;
                    //银行
                    for (var i=0;i<model.banks.length;i++){
                        if (model.banks[i].name.indexOf(bankInfo.bankName.trim())>-1){
                            model.form.bankCode = model.banks[i].code;
                            break;
                        }
                    }
                    //省
                    for (var i=0;i<model.regions.length;i++){
                        var provinceItem = model.regions[i];
                        if (provinceItem.name.indexOf(bankInfo.province.trim())>-1){
                            model.form.bankProvinceCode = provinceItem.code;
                            //城市
                            for (var j=0;j<provinceItem.children.length;j++){
                                var cityItem = provinceItem.children[j];
                                if (cityItem.name.indexOf(bankInfo.city.trim())>-1){
                                    model.form.bankCityCode = cityItem.code;
                                    break;
                                }
                            }
                            break;
                        }
                    }

                }
            });

        }
    }
});
