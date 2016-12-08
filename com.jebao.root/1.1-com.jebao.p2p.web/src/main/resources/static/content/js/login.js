/**
 * Created by lihui on 2016/12/5.
 */
$(document).ready(function() {
    initValidateForm();
    $(".login-in-btn").click(function(){
        var bootstrapValidator = $("#loginForm").data('bootstrapValidator').validate();
        if(!bootstrapValidator.isValid()){return;}
        //is ok
        //todo
        alert(bootstrapValidator.isValid());
    });
});
//表单登录验证封装
function initValidateForm(){
    $('#loginForm').bootstrapValidator({
        fields: {
            tel: {
                validators: {
                    notEmpty: {
                        message: '手机号不能为空'
                    },
                    regexp: {
                        regexp: /^1(3|4|5|7|8)\d{9}$/,
                        message: '手机号错误'
                    },
                    stringLength: {
                        max: 11,
                        message: '手机号长度必须在2到7位之间'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    regexp: {
                        regexp:/^[0-9_a-zA-Z]{6,20}$/,
                        message: '密码错误'
                    }
                }
            }
        }
    });
}

