/**
 * Created by Administrator on 2016/12/12.
 */
//Vue实例
//Model
var model = {
    //资金汇总
    fundSum:{incomeAmount:0,totalAssets:0,balance:0,freezeAmount:0,dueInPrincipal:0,dueInIncome:0},
    //投资中项目
    investIngs:[],
    //还款中项目
    paymentIngs:[],
    //收支明细列表
    fundsDetails:[]
};

// 创建一个 Vue 实例 (ViewModel),它连接 View 与 Model
var vm = new Vue({
    el: ".account-content",
    data: model,
    beforeCreate:function(){
    },
    //初始化远程数据
    created:function(){
        $.get("/api/invest/statistics",function(response){
            if (response.success_is_ok){
                var data=response.data;
                if(data!=null) {
                    vm.fundSum = data;
                }
            }
        });
        $.get("/api/funds/list", function (response) {
            if (response.success_is_ok) {
                var data = response.data;
                vm.fundsDetails = data;
            }
        });
        this.search(1);
    },
    //方法，可用于绑定事件或直接调用
    methods: {
        getDetailHref: function (id) {
            return "/product/detail/" + id;
        },
        search: function(fs){
            $.get("/api/invest/list", {freezeStatus:fs}, function (response) {
                if (response.success_is_ok) {
                    var data = response.data;
                    if(fs == 1){
                        vm.investIngs = data;
                    }else if(fs == 2){
                        vm.paymentIngs = data;
                    }
                }
            });
        }
    }
});