/**
 * Created by Lee on 2016/11/17.
 */

/**
 * Created by Jack on 2016/11/18.
 */
$(function () {

    $(".select2").select2();

    //var autoObj = {
    //    addInsuredFormValidate: function () {
    //        var $form = $("#form-inline");
    //        $form.validate({
    //            rules: {
    //                Name: {
    //                    required: true,
    //                    realName: $("#insured_card_type")
    //                },
    //                CardNo: {
    //                    required: true,
    //                    idCard: $("#insured_card_type")
    //                },
    //                Birthday: {
    //                    required: true,
    //                    date: true
    //                },
    //                Gender: {
    //                    required: true
    //                }
    //            },
    //            messages: {
    //                Name: {
    //                    required: "请输入被保险人姓名",
    //                    realName: "请输入正确的被保险人姓名"
    //                },
    //                CardNo: {
    //                    required: "请输入被保险人证件号码"
    //                },
    //                Birthday: {
    //                    required: "请输入被保险人出生日期"
    //                },
    //                Gender: {
    //                    required: "请选择性别"
    //                }
    //            }
    //
    //        });
    //    },
    //    BindEvent: function () {
    //
    //    },
    //    ButtonEvent: function () {
    //
    //    }
    //};
    //for (var prop in autoObj) {
    //    if (typeof autoObj[prop] === "function") {
    //        //autoObj[prop].apply();
    //    }
    //}
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
        model.searchObj.page=0;
        model.searchObj.rows=10;
        model.searchObj.bpStatus=1;
    },
    //初始化远程数据
    created:function(){
        this.search();

    },
    //方法，可用于绑定事件或直接调用
    methods: {
        search:function(event){
            $.get("/bidplan/dplan/getPlanListForPage",model.searchObj,function(response){
                if (response.success_is_ok){
                    vm.planlist=response.data;
                    if (response.count>0){
                        var pageCount = Math.ceil(response.count / model.searchObj.rows);
                        //调用分页
                        laypage({
                            cont: $('#pageNum'), //容器。值支持id名、原生dom对象，jquery对象,
                            pages: pageCount, //总页数
                            groups: 7, //连续显示分页数
                            jump: function(obj, first){ //触发分页后的回调
                                if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
                                    console.log(obj.curr);
                                    vm.searchObj.page=obj.curr -1;
                                    vm.search();
                                }
                            },
                            skin: '#3c8dbc'
                        });
                    }
                }
            })
        }
    }
});

$("#orderlist_table").on("click",'.cmd-delete',function(){
    var that = $(this); //解决方案
    var dataVal=that.attr('data-val');//自定义属性
    layer.open({
        content:'您是否删除信息?',
        btn: ['取消', '删除'],
        btn1: function(){
            layer.closeAll();
        },
        btn2: function(){
            window.location.href = "/bidplan/dplan/remove?bpId="+dataVal
            layer.msg('删除成功!');
        }
    });
});

$("#orderlist_table").on("click",'#modifyInformation',function(){
    var that = $(this); //解决方案
    var dataVal=that.attr('data-val');//自定义属性
    window.location.href = "/bidplan/updateplandetail/"+dataVal;
});
