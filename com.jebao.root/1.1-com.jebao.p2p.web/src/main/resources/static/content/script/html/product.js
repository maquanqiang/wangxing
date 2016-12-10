$(function () {
    $('.project-filter dl dt').click(function () {
        model.searchObj.bpInterestPayType = null;
        model.searchObj.bpPeriodStr = null;
        model.searchObj.searchMoneyStr = null;
        model.searchObj.bpStatus = null;
        $(this).parent().find('dt').removeClass('active');
        $(this).addClass('active');

        $('.project-filter dl dt').each(function () {

            var clazz = $(this).attr("class");
            if (clazz == "active") {
                //获得被选中的a链接的类型和值
                var fType = $(this).attr("fType");
                var fValue = $(this).attr("fValue");
                if (fType == "bpInterestPayType") {
                    model.searchObj.bpInterestPayType = fValue;
                } else if (fType == "bpPeriodStr") {
                    model.searchObj.bpPeriodStr = fValue;
                } else if (fType == "searchMoneyStr") {
                    model.searchObj.searchMoneyStr = fValue;
                } else if (fType == "bpStatus") {
                    model.searchObj.bpStatus = fValue;
                }
            }
        });

        vm.search()
    });
});


//Vue实例
//Model
var model = {
    //查询条件
    searchObj: {},
    //列表
    products: [],
    cycleType:["","天","个月","季","年"]
};

// 创建一个 Vue 实例 (ViewModel),它连接 View 与 Model
var vm = new Vue({
    el: ".project-list",
    data: model,
    beforeCreate: function () {
        //初始化本地数据

        model.searchObj.pageIndex = 0;
        model.searchObj.pageSize = 10;
    },
    //初始化远程数据
    created: function () {
        this.search();
    },
    //方法，可用于绑定事件或直接调用
    methods: {
        search: function (event) {
            $.post($("#loginForm").attr("action"), model.searchObj, function (response) {
                if (response.success_is_ok) {
                    console.log(response.data)
                    vm.products = response.data;
                    if (response.count > 0) {
                        var pageCount = Math.ceil(response.count / model.pageSize);
                        //调用分页
                        laypage({
                            cont: $('#pageNum'), //容器。值支持id名、原生dom对象，jquery对象,
                            pages: pageCount, //总页数
                            groups: 7, //连续显示分页数
                            jump: function (obj, first) { //触发分页后的回调
                                if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                                    console.log(obj.curr);
                                    vm.searchObj.pageIndex = obj.curr - 1;
                                    vm.search();
                                }
                            },
                            skin: '#3c8dbc'
                        });
                    }
                }
            });
        },
        investBtn: function (id) {
            window.location.href = "/bidplan/addplan/" + id
        }
    }
});

