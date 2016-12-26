/**
 * Created by Administrator on 2016/12/13.
 */
//Vue实例
//Model
var model = {
    //查询条件
    searchObj: {},
    //投资中项目列表
    investings: [],
    isHasDateii: true,
    //还款中项目列表
    paymentings: [],
    isHasDatepi: true,
    //已还款项目列表
    paymenteds: [],
    isHasDatepd: true
};

// 创建一个 Vue 实例 (ViewModel),它连接 View 与 Model
var vm = new Vue({
    el: ".account-content",
    data: model,
    beforeCreate: function () {
        //初始化本地数据
        model.searchObj.freezeStatus = 1;
        model.searchObj.pageIndex = 0;
        model.searchObj.pageSize = 10;
    },
    //初始化远程数据
    created: function () {
        this.search(1);
    },
    //方法，可用于绑定事件或直接调用
    methods: {
        getDetailHref: function (id) {
            return "/product/detail/" + id;
        },
        search: function (fs) {
            model.searchObj.freezeStatus = fs;
            $.get("/api/invest/record", model.searchObj, function (response) {
                if (response.success_is_ok) {
                    var data = response.data;
                    if (fs == 1) {
                        vm.investings = data;
                        if (data != null && data.length > 0) {
                            vm.isHasDateii = true;
                        } else {
                            vm.isHasDateii = false;
                        }
                    } else if (fs == 2) {
                        vm.paymentings = data;
                        if (data != null && data.length > 0) {
                            vm.isHasDatepi = true;
                        } else {
                            vm.isHasDatepi = false;
                        }
                    } else {
                        vm.paymenteds = data;
                        if (data != null && data.length > 0) {
                            vm.isHasDatepd = true;
                        } else {
                            vm.isHasDatepd = false;
                        }
                    }
                    var pageCount = Math.ceil(response.count / model.searchObj.pageSize);
                    if (pageCount > 0) {
                        //调用分页
                        laypage({
                            cont: $('#pageNum' + fs), //容器。值支持id名、原生dom对象，jquery对象,
                            pages: pageCount, //总页数
                            groups: 7, //连续显示分页数
                            jump: function (obj, first) { //触发分页后的回调
                                if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                                    vm.searchObj.pageIndex = obj.curr - 1;
                                    vm.search(fs);
                                }
                            },
                            skin: '#3c8dbc'
                        });
                    }
                }
            });
        }
    }
});
