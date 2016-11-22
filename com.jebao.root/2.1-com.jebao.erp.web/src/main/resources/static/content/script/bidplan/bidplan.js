/**
 * Created by Lee on 2016/11/17.
 */

$(function() {
    $.ajax({
        type: "POST",
        url: "/bidplan/dplan/getlist",
        dataType: 'json',
        contentType: 'application/json',
        success: function (result) {
            new Vue({
                el: '#orderlist_table',
                data: {
                    planlist: result.data
                }
            })
        }
    })
})