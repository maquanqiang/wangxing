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
    riskDataList:[],
    total : 0,
    loanMoney : 0,
    investInfoList : [],
    fundType : ['','本金','利息']


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
                vm.loanMoney = data.bpBidMoney-data.bpSurplusMoney;
            }
        });
        $.get("/api/bidRiskData/getRiskDataListForPage", dataVal, function (response) {
            if (response.success_is_ok) {
                vm.riskDataList = response.data;
            }
        })
        $.get("/api/investInfo/list", dataVal, function (response) {
            if (response.success_is_ok) {
                vm.investInfoList = response.data;
            }
        })
    },
    //方法，可用于绑定事件或直接调用
    methods: {
        search:function(event){
        },
        createIntentBtn:function(){
            var form = $("#defaultForm").serializeObject();
            $.post("/api/investInfo/createRepaymentDetails",form,function(response){
                if (response.success_is_ok){
                    vm.intentList = response.data;
                    for(var i=0; i<vm.intentList.length; i++){
                        vm.total += parseFloat(vm.intentList[i].money);
                    }
                    layer.alert("借款人还款明细生成，正在生成投资人收入明细");
                    $.post("/api/investInfo/createIncomeDetails",form,function(response){
                        if (response.success_is_ok){
                            layer.alert(response.msg);
                        }
                    })
                }
            });
        }
    }
});

laydate({
    elem:'#bpInterestSt',
    istime: true,
    format: 'YYYY-MM-DD',
    istoday : true,
    choose : function(datas){
        var d = vm.plan.bpPeriodsDisplay;
        var cycle = vm.plan.bpCycleType;
        var date = repayDate(datas, d, cycle);
        $("#bpRepayTime").val(date);
    }
});


function repayDate(loanDate, d, cycle){
    if(cycle!="" && loanDate != "" && d != ""){
        loanDate = loanDate.replace(/-/g,"/");
        var date = new Date(loanDate);
        if(cycle==1){   //天
            date.setDate(date.getDate() + d*1);
        }else if(cycle==2){ //月
            date.setMonth(date.getMonth() + d*1);
        }else if(cycle == 3){   //季
            date.setMonth(date.getMonth() + (d*3));
        }else if(cycle ==4 ){   //年
            date.setFullYear(date.getFullYear() + d*1);
        }
        return date.toFormatString("yyyy-MM-dd");
    }else{
        return null;
    }
}

