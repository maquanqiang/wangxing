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
    incomeDetailList : [],
    fundType : ['','本金','利息'],
    repayStatus : ['', '未还款', '已还款']


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
        this.search();
    },
    //方法，可用于绑定事件或直接调用
    methods: {
        search:function(event){
            $.get("/api/incomeDetail/repaymentList",function(response){
                if (response.success_is_ok){
                    vm.incomeDetailList=response.data;
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

