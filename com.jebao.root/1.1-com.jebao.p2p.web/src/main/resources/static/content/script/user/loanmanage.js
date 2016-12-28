/**
 * Created by Administrator on 2016/12/12.
 */
//Vue实例
//Model
var model = {
    //资金汇总
    loanSum:{},
    //查询条件
    searchObj: {},
    //还款中项目
    paymentIngs:[],
    //已结清
    paymented:[],
    cycleType:["","天","个月","季","年"]
};

// 创建一个 Vue 实例 (ViewModel),它连接 View 与 Model
var vm = new Vue({
    el: ".account-content",
    data: model,
    beforeCreate:function(){
        model.searchObj.pageIndex = 0;
        model.searchObj.pageSize = 10;
    },
    //初始化远程数据
    created:function(){
        $.get("/api/loanManage/loanMoneyCount", function (response) {
            if (response.success_is_ok) {
                var data = response.data;
                vm.loanSum = data;
            }
        });
        this.repayingList()
    },
    //方法，可用于绑定事件或直接调用
    methods: {
        repay:function(period, bpId, repayMoney){
            //还款弹出框
            layer.open({
                title:'还款提示',
                content:'确认现在还款?',
                btn: ['确认', '稍后'],
                area: ['340px', '180px'],
                yes: function(){
                    layer.closeAll();
                    var index = layer.load();
                    $.post("/api/loanManage/repay",{period:period, bpId:bpId, repayMoney:repayMoney}, function (response) {
                        layer.closeAll();
                        if(response.success_is_ok){
                            layer.alert(response.msg);
                            window.location.reload();
                        }else{
                            layer.alert(response.error);
                        }
                    })
                },
                btn2: function(){
                    layer.closeAll();
                }
            });
        },
        repayedList:function(){
            $.get("/api/loanManage/repayedDetails", model.searchObj, function (response) {
                if (response.success_is_ok) {
                    var data = response.data;
                    vm.paymented = data;
                    if (response.count > 0) {
                        var pageCount = Math.ceil(response.count / model.searchObj.pageSize);
                        //调用分页
                        laypage({
                            skip: true, //是否开启跳页
                            skin: '#e88a6e',
                            cont: $('#page2'), //容器。值支持id名、原生dom对象，jquery对象,
                            pages: pageCount, //总页数
                            curr : model.searchObj.pageIndex + 1,
                            groups: 7, //连续显示分页数
                            jump: function (obj, first) { //触发分页后的回调
                                if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                                    console.log(obj.curr);
                                    vm.searchObj.pageIndex = obj.curr - 1;
                                    vm.repayedList();
                                }
                            }
                        });
                    }
                }
            });
        },
        repayingList:function(){
            $.get("/api/loanManage/repayingDetails",model.searchObj,function(response){
                if (response.success_is_ok){
                    var data=response.data;
                    if(data!=null) {
                        vm.paymentIngs = data;
                        if (response.count > 0) {
                            var pageCount = Math.ceil(response.count / model.searchObj.pageSize);
                            //调用分页
                            laypage({
                                skip: true, //是否开启跳页
                                skin: '#e88a6e',
                                cont: $('#page1'), //容器。值支持id名、原生dom对象，jquery对象,
                                pages: pageCount, //总页数
                                groups: 7, //连续显示分页数
                                jump: function (obj, first) { //触发分页后的回调
                                    if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                                        console.log(obj.curr);
                                        vm.searchObj.pageIndex = obj.curr - 1;
                                        vm.repayingList();
                                    }
                                }
                            });
                        }
                    }
                }
            });
        }
    }
});