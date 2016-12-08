/**
 * Created by lihui on 2016/12/3.
 */
$(function(){
    //我的账户公共左侧导航切换
    $('.account-menu a').click(function(){
        $('.account-menu a').removeClass('on');
        $(this).addClass('on');
    });
    //投资中项目和还款中项目
    tab($('.project-menu h4'),$('.project-item'));
    //充值提现--我要充值、我要提现
    tab($('.account-rex-tit h4'),$('.account-rex-item'));
    //充值提现--快捷充值、快捷充值
    tab($('.rechange-type-title h5'),$('.rechange-item'));
    //切换封装
    function tab(btn,box){
        btn.click(function (){
            btn.removeClass('active');
            box.removeClass('active');
            $(this).addClass('active');
            var index=$(this).index();
            box.eq(index).addClass('active');
        });
    }
    //账户设置--收起
    $('.install-btn').click(function(){
        if($(this).html() == '修改'){
            $(this).css('background-color','#ccc').html('收起');
            $(this).parent().next().show();
        }else{
            $(this).css('background-color','#36B1DF').html('修改');
            $(this).parent().next().hide();
        }
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
                oBtn.removeClass('color');
                clearInterval(timer);
                n=90;
                bFlag=false;
            }
        }
    });
});