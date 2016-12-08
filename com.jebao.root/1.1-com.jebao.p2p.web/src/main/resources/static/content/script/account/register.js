/**
 * Created by Jack on 2016/12/8.
 */
//点击切换验证码图片
$(".verification").click(function(){
    $(this).attr('src','/captcha/getCode?'+Math.random());
});

var model = {

};
var vm = new Vue({
    el:".register-main",
    data:model,
    mounted:function(){
        //前端写的代码
        initValidateForm();
        $(".register-btn").click(function(){
            var bootstrapValidator = $("#registerForm").data('bootstrapValidator').validate();
            if(!bootstrapValidator.isValid()){return;}
            //is ok
            //todo
            alert(bootstrapValidator.isValid());
        });
        $(".next-btn").click(function(){
            var bootstrapValidator = $("#registerForm").data('bootstrapValidator').validate();
            if(!bootstrapValidator.isValid()){return;}
            //is ok
            //todo
            alert(bootstrapValidator.isValid());
        });
        //短信倒计时
        var  oBtn=$('.get_code_btn');
        var  n=90;
        var  bFlag=false;
        oBtn.click(function(){
            if(bFlag){
                return;
            }
            bFlag=true;
            tick();
            var timer=setInterval(tick,1000);
            function tick(){
                n--;
                oBtn.val(n+'s后可重发');
                oBtn.addClass('color');
                if(n==0){
                    oBtn.val('重新获取验证码');
                    clearInterval(timer);
                    n=90;
                    bFlag=false;
                }
            }
        });
        //点击切换验证码图片
        $(".verification").click(function(){
            if($(this).attr('src')=='images/text-code.jpg'){
                $(this).attr('src','images/CaptchaImg.png');
            }else{
                $(this).attr('src','images/text-code.jpg');
            }
        });
        //注册点击服务协议
        $('.remember a').click(function () {
            $('.shadow-bg').show();
            $('.agreement-wrap').show();
        });
        $('.close-btn').click(function () {
            $('.shadow-bg').hide();
            $('.agreement-wrap').hide();
        });
    },
    methods:{
        refreshCaptcha:function(event){
            event.target.src="/captcha/getCode?"+Math.random();
        }
    }
});
//表单登录验证封装
function initValidateForm(){
    $('#registerForm').bootstrapValidator({
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
            },
            passwordAgain: {
                validators: {
                    notEmpty: {
                        message: '再次设置密码不能为空'
                    },
                    regexp: {
                        regexp:/^[0-9_a-zA-Z]{6,20}$/,
                        message: '密码格式错误'
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
            telCode: {
                validators: {
                    notEmpty: {
                        message: '手机验证码不能为空'
                    }
                }
            }
        }
    });
}