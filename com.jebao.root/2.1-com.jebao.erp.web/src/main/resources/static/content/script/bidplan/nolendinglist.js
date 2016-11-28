
/**
 * Created by lee on 2016/11/18.
 */
$(function () {

    $(".select2").select2();

});
//Vue实例
//Model
var model = {
    //查询条件
    searchObj: {},
    //列表
    planlist: []

};

// 创建一个 Vue 实例 (ViewModel),它连接 View 与 Model
var vm = new Vue({
    el: ".content",
    data: model,
    beforeCreate:function(){
        //初始化本地数据
        model.searchObj = $("#order_search_form").serializeObject(); //初始化 model.search 对象
    },
    //初始化远程数据
    created:function(){
        this.search();
    },
    //方法，可用于绑定事件或直接调用
    methods: {
        search:function(event){
            $.get("/api/bidPlan/getPlanListForPage",model.searchObj,function(response){
                if (response.success_is_ok){
                    vm.planlist=response.data;
                }
            })
        },
        modifyPlanBtn:function(bpId){
            window.location.href = "/bidplan/updateplandetail/"+bpId;
        }
    }
});