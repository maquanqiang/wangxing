/**
 * Created by Administrator on 2016/11/24.
 */
var model = {
    risks: []
};
// 创建一个 Vue 实例 (ViewModel),它连接 View 与 Model
var vm = new Vue({
    el: ".content",
    data: model,
    //初始化远程数据
    created: function () {
        var dataVal = $("#defaultForm").serializeObject();
        $.get("/api/risk/risklist", dataVal, function (response) {
            if (response.success_is_ok) {
                var data = response.data;
                vm.risks = data;
            }
        });
    },
    //方法，可用于绑定事件或直接调用
    methods: {
        getEditHref: function (lid, rid) {
            return "/loaner/riskedit/" + lid + "/rId/" + rid;
        },
        getDetailHref: function (id) {
            return "/loaner/riskdetails/" + id;
        },
        getMaterialsHref: function(id){
            return "/loaner/riskmaterials/" + id;
        }
    }
});
