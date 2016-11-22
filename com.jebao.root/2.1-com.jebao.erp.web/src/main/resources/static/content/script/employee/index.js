/**
 * Created by Jack on 2016/11/18.
 */
$(function () {

    $(".select2").select2();

    var autoObj = {
        addInsuredFormValidate: function () {
            var $form = $("#insured_form");
            $form.validate({
                rules: {
                    Name: {
                        required: true,
                        realName: $("#insured_card_type")
                    },
                    CardNo: {
                        required: true,
                        idCard: $("#insured_card_type")
                    },
                    Birthday: {
                        required: true,
                        date: true
                    },
                    Gender: {
                        required: true
                    }
                },
                messages: {
                    Name: {
                        required: "请输入被保险人姓名",
                        realName: "请输入正确的被保险人姓名"
                    },
                    CardNo: {
                        required: "请输入被保险人证件号码"
                    },
                    Birthday: {
                        required: "请输入被保险人出生日期"
                    },
                    Gender: {
                        required: "请选择性别"
                    }
                },

            });
        },
        BindEvent: function () {

        },
        ButtonEvent: function () {

        }
    };
    for (var prop in autoObj) {
        if (typeof autoObj[prop] === "function") {
            //autoObj[prop].apply();
        }
    }
});
//Vue实例
//Model
var model = {
    //查询条件
    searchObj: {},
    //所属团队
    teams: [],
    //销售级别
    ranks: [],
    //员工列表
    employees:[]
};

// 创建一个 Vue 实例 (ViewModel),它连接 View 与 Model
var vm = new Vue({
    el: "#content",
    data: model,
    beforeCreate:function(){
        //初始化本地数据
        model.searchObj=$("#search_form").serializeObject();
        model.searchObj.pageIndex=0;
        model.searchObj.pageSize=10;
        //在这里的远程数据更新，必须是在对象已经具备相应属性。否则有可能不会绑定映射
        //团队
        $.get("/api/department/list",function(response){
            if (response.success_is_ok){
                vm.teams=response.data;
            }
        });
        //销售级别
        $.get("/api/rank/list",function(response){
            if (response.success_is_ok){
                vm.ranks=response.data;
            }
        });
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
            $("#btnSearch").addClass("disabled");//禁用按钮
            $.get("/api/employee/list",model.searchObj,function(response){
                if (response.success_is_ok){
                    vm.employees=response.data;
                    if (response.count>0){
                        var pageCount = Math.ceil(response.count / model.pageSize);
                        //调用分页
                        laypage({
                            cont: $('#pageNum'), //容器。值支持id名、原生dom对象，jquery对象,
                            pages: pageCount, //总页数
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
                $("#btnSearch").removeClass("disabled");//解除禁用
            });
        }
    }
});
