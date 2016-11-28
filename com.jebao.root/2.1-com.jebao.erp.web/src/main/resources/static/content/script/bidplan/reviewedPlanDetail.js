/**
 * Created by Lee on 2016/11/17.
 */

/**
 * Created by Jack on 2016/11/18.
 */
$(function () {

    $(".select2").select2();
});
//Vue实例
//Model
var model = {
    //查询条件
    search: {},
    //标的
    plan: {},
    //台账列表
    intentList : [],
    //
    riskDataList:[]

};

// 创建一个 Vue 实例 (ViewModel),它连接 View 与 Model
var vm = new Vue({
    el: ".content",
    data: model,
    beforeCreate:function(){
        //初始化本地数据
    },
    //初始化远程数据
    created:function(){
        var val = $("#bpId").val();
        var dataVal = {
            bpId : val
        }
        $.get("/api/bidPlan/getBidPlanById",dataVal,function(response){
            if (response.success_is_ok){
                var data=response.data;
                vm.plan=data;
            }
        });
        $.get("/api/bidRiskData/getRiskDataListForPage", dataVal, function (response) {
            if (response.success_is_ok) {
                vm.riskDataList = response.data;
            }
        })
    },
    //方法，可用于绑定事件或直接调用
    methods: {
        search:function(event){
        },
        createIntentBtn:function(){
            $.get("/api/bidPlan/getLoanFundIntents",vm.plan,function(response){
                if (response.success_is_ok){
                    vm.intentList = response.data;
                }
            });
        },
        reviewBtn : function(state){
            var val = $("#bpId").val();
            var formValue={};
            formValue.bpId = val;
            formValue.status = state;
            if(state==1){
                var remark  = prompt("请写明拒绝原因","")
                formValue.remark = remark;
            }

            $.post("/api/bidPlan/reviewedPlan",formValue,function(response){
                if (response.success_is_ok){
                    layer.msg(response.msg);
                    vm.reviewedPlanList();
                }else{
                    layer.alert(response.msg);
                }
            });
        },
        reviewedPlanList : function(){
            window.location.href="/bidplan/reviewedPlanList";
        }
    }
});