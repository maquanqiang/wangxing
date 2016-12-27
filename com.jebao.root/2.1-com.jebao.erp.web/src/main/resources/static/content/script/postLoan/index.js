/**
 * Created by Lee on 2016/11/17.
 */

/**
 * Created by Jack on 2016/11/18.
 */
$(function () {

    $(".select2").select2();

    /*时间选择*/
    $('.chooseDate').datepicker({
        format: 'yyyy-mm-dd',
        weekStart: 1,
        autoclose: true,
        todayBtn: 'linked',
        language: 'cn'
    });
});
//Vue实例
//Model
var model = {
    //查询条件
    searchObj: {},
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
        model.searchObj = $("#defaultForm").serializeObject(); //初始化 model.search 对象
        model.searchObj.bpStatus = 7;
        model.searchObj.pageIndex=0;
        model.searchObj.pageSize=10;
    },
    //初始化远程数据
    created:function(){
        this.search();
    },
    //方法，可用于绑定事件或直接调用
    methods: {
        search:function(event){

            if (typeof event !== "undefined"){ //点击查询按钮的话，是查询第一页数据
                model.searchObj.pageIndex=0;
            }

            $("#searchBtn").addClass("disabled");//禁用按钮
            $.get("/api/incomeDetail/postLoanIncomeDetail",model.searchObj,function(response){
                if (response.success_is_ok){
                    vm.incomeDetailList=response.data;
                    if (response.count>0){
                        var pageCount = Math.ceil(response.count / model.searchObj.pageSize);
                        //调用分页
                        laypage({
                            cont: $('#pageNum'), //容器。值支持id名、原生dom对象，jquery对象,
                            pages : pageCount, //总页数
                            curr : model.searchObj.pageIndex+1,
                            groups: 7, //连续显示分页数
                            jump: function(obj, first){ //触发分页后的回调
                                if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
                                    console.log(obj.curr);
                                    vm.searchObj.pageIndex=obj.curr -1;
                                    vm.search();
                                }
                            },
                            skin: '#3c8dbc'
                        });
                    }
                }
                $("#searchBtn").removeClass("disabled");//解除禁用
            });
        },
        openView : function(bpId, period, fundType){
            window.location.href = "/postLoan/incomeDetail/"+bpId+"/"+period + "/"+fundType;
        }
    }
});


