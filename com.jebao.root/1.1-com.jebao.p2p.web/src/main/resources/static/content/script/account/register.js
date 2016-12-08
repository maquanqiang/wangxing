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

    },
    methods:{
        refreshCaptcha:function(){
            
        }
    }
});