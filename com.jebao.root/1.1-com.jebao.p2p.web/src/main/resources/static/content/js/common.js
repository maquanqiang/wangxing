/**
 * Created by lihui on 2016/11/30.
 */
$(function(){
    // 初始化让#gotTop隐藏
    $('#gotoTop').hide();
    // #gotTop 点击事件
    $('#gotoTop').click(function(){
        $('html,body').animate({'scrollTop':0},500);
        $('#gotoTop').hide();
    });
    // window窗口滚动事件
    $(window).scroll(function(){
        var winTop = $(window).scrollTop();
        if (winTop <= 100) {
            $('#gotoTop').hide();
        }else{
            $('#gotoTop').show();
        }
    });
});