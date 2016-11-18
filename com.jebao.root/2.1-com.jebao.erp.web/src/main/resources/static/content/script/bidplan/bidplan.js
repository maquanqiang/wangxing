/**
 * Created by Lee on 2016/11/17.
 */

$(function() {
    $.ajax({
        type: "POST",
        url: "/bidplan/plan/getlist",
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {
            new Vue({
                el: '#orderlist_table',
                data: {
                    planlist: data
                }
            })
        }
    })
})