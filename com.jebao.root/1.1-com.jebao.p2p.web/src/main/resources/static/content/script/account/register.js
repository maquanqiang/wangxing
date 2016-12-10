/**
 * Created by Jack on 2016/12/8.
 */
//点击切换验证码图片
$(".verification").click(function () {
    $(this).attr('src', '/captcha/getCode?' + Math.random());
});

var model = {
    form: {},
    codeDisabled:false
};
var vm = new Vue({
    el: ".register-content",
    data: model,
    beforeCreate: function () {
        //初始化 model.form
        model.form = $("#registerForm").serializeObject();
        model.form.agree = false;
        var invitationCode = common.getUrlParam("code");
        if (invitationCode){
            model.form.invitationCode = invitationCode;
            model.codeDisabled = true;
        }
    },
    mounted: function () {
        //前端写的代码
        //注册点击服务协议
        $('.remember a').click(function () {
            $('.shadow-bg').show();
            $('.agreement-wrap').show();
        });
        $('.close-btn').click(function () {
            $('.shadow-bg').hide();
            $('.agreement-wrap').hide();
        });

        this.initValidateForm();
    },
    methods: {
        refreshCaptcha: function (event) {
            event.target.src = "/captcha/getCode?" + Math.random();
        },
        //短信验证码
        sendMessage: function (event) {
            var $target = $(event.target);
            if ($target.hasClass("disabled")) {
                return false;
            }
            $target.addClass("disabled");
            var initVal = $target.val();
            var leftSeconds = 90;
            var sendTimerInterval = setInterval(function () {
                leftSeconds--;
                if (leftSeconds === 0) {
                    clearInterval(sendTimerInterval);
                    $target.removeClass("disabled").val(initVal);
                } else {
                    $target.val(leftSeconds + ' s后可重发');
                }
            }, 1000);
        },
        initValidateForm: function () {
            $('#registerForm').bootstrapValidator({
                fields: {
                    mobile: {
                        validators: {
                            notEmpty: {
                                message: '手机号不能为空'
                            },
                            regexp: {
                                regexp: /^1(3|4|5|7|8)\d{9}$/,
                                message: '手机号错误'
                            }
                        }
                    },
                    password: {
                        validators: {
                            notEmpty: {
                                message: '密码不能为空'
                            },
                            regexp: {
                                regexp: /^(?![0-9]+$)(?![a-zA-Z]+$)[A-Za-z0-9_-]{6,16}$/,
                                message: '请设置6-16密码（由字母、数字组成）'
                            }
                        }
                    },
                    passwordAgain: {
                        validators: {
                            notEmpty: {
                                message: '请输入确认密码'
                            },
                            identical: {//相同
                                field: 'password', //需要进行比较的input name值
                                message: '两次密码不一致'
                            }
                        }
                    },
                    imgCode: {
                        validators: {
                            notEmpty: {
                                message: '图形验证码不能为空'
                            }
                        }
                    },
                    messageCode: {
                        validators: {
                            notEmpty: {
                                message: '短信验证码不能为空'
                            }
                        }
                    }
                }
            }).on('success.form.bv', function (e) {
                // Prevent form submission
                e.preventDefault();
                var $errorPlace = $(".error-place").addClass("hidden");
                // Get the form instance
                var $form = $(e.target);

                // Use Ajax to submit form data
                $.post($form.attr('action'), $form.serializeObject(), function (response) {
                    if (response.success_is_ok) {
                        window.location.href = "/account/login";
                        return;
                    } else {
                        $errorPlace.removeClass("hidden").find("span").html(response.msg);
                    }
                }, "json");
            });
        },
        register: function () {
            $('#registerForm').submit();
        }
    }
});
