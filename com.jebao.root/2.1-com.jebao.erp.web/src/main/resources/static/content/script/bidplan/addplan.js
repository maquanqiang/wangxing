

$(function () {
    $(".select2").select2();
    /*时间选择*/
    $('.chooseDate').datepicker({
        format: 'yyyy-mm-dd',
        weekStart: 1,
        autoclose: true,
        todayBtn: 'linked',
        language: 'cn'
    });
});
$("#submitBtn").click(function () {
    //TODO 后台逻辑
    $.axForForm($('#defaultForm'), function (data) {
        if (data.success_is_ok) {
            var targetUrl = "/bidplan/reviewedPlanList"
            redirectUrl(targetUrl)
            return;
        } else {
            errorHandlerFun(data, "#error_place_id");
        }
    });
});

