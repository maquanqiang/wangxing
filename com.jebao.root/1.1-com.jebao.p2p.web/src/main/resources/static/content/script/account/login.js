$(document).ready(function () {
    var lgn = $.cookie("jebao_lgn");
    if (lgn){
        $("#loginForm .userName input").val(lgn);
    }
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
            var submitData = $form.serializeObject();
            // Use Ajax to submit form data
            $.post($form.attr('action'), submitData, function (response) {
                if (response.success_is_ok) {
                    if(submitData.remember === "1"){
                        $.cookie("jebao_lgn",submitData.jebUsername,{ expires: 3 });
                    }

                    //同步第三方用户信息
                    $.get("/api/user/syncThirdAccount");

                    var redirectUrl = common.getUrlParam("redirectUrl") || "/";
                    window.location.href=redirectUrl;
                    return;
                } else {
                    $errorPlace.removeClass("none").find("span").html(response.error);
                }
            });

        });
    }

    initValidateForm();
    $(".login-in-btn").click(function (event) {
        event.preventDefault();
        $('#loginForm').submit();
    });
});


