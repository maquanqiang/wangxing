$(document).ready(function () {
    //表单登录验证封装
    function initValidateForm() {
        $('#loginForm').bootstrapValidator({
            live: "submitted",
            fields: {
                jebUsername: {
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
                        stringLength: {
                            min: 6,
                            max: 20,
                            message: '密码格式错误'
                        }
                    }
                }
            }
        }).on('success.form.bv', function (e) {
            // Prevent form submission
            e.preventDefault();
            var $errorPlace = $("#login_message").addClass("none");
            // Get the form instance
            var $form = $(e.target);

            // Use Ajax to submit form data
            $.post($form.attr('action'), $form.serialize(), function (response) {
                if (response.success_is_ok) {
                    //var code = response.msg;
                    var redirectUrl = common.getUrlParam("redirectUrl") || "/";
                    //window.location.href = "/account/token?code="+code+"&redirectUrl"+redirectUrl;
                    window.location.href=redirectUrl;
                    return;
                } else {
                    $errorPlace.removeClass("none").find("span").html(response.error);
                }
            });
        });
    }

    initValidateForm();
    $(".login-in-btn").click(function () {
        //$('#loginForm').trigger('success.form.bv');
        $('#loginForm').submit();
    });
});


