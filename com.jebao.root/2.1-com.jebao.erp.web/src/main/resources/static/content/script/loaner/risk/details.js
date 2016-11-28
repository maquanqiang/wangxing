/**
 * Created by Administrator on 2016/11/28.
 */
/**
 * Created by Administrator on 2016/11/24.
 */
var model = {
    details: {}
};
// 创建一个 Vue 实例 (ViewModel),它连接 View 与 Model
var vm = new Vue({
    el: ".content",
    data: model,
    //初始化远程数据
    created:function(){
        var dataVal = $("#search_form").serializeObject();
        $.get("/api/risk/details",dataVal,function(response){
            if (response.success_is_ok){
                var data=response.data;
                vm.details=data;
            }
        });
    }
});