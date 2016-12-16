/**
 * Created by Administrator on 2016/12/9.
 */
$(function() {
    //我的账户公共左侧导航切换
    $('.sidebar-menu a').click(function () {
        $('.sidebar-menu a').removeClass('on');
        $(this).addClass('on');
    });

})
