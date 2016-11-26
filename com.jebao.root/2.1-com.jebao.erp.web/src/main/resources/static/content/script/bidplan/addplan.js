

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
    //列表
    projList: [],
    projectTemp : {},
    selected : '',
    intentList : [],
    createIntent : {}

};

// 创建一个 Vue 实例 (ViewModel),它连接 View 与 Model
var vm = new Vue({
    el: ".content",
    data: model,
    //初始化远程数据
    created:function(){
        var formData = $("#defaultForm [name='bpLoanerId']").val();
        var loanerId =  {
            bpLoanerId : formData
        }
        $.get("/bidplan/getProjList",loanerId,function(response){
            console.log(response.data);
            if (response.success_is_ok){
                vm.projList = response.data;
            }
        });
    },
    //watch可以监视数据变动，针对相应的数据设置监视函数即可
    watch: {
        //
        "selected": function (newVal,oldVal) {
            var optionVal = $("#rcptId").val();
            var rcptId = {
                rcptId : optionVal
            }
            $.get("/bidplan/getProjectTempById",rcptId,function(response){
                console.log(response.data);
                if (response.success_is_ok){
                    vm.projectTemp = response.data;
                }
            });
        }
    },
    //方法，可用于绑定事件或直接调用
    methods: {
        search:function(event){
        },
        createIntent:function(){
            var formValue = $("#defaultForm").serializeObject();
            $.get("/bidplan/getLoanFundIntents",formValue,function(response){
                console.log(response.data);
                if (response.success_is_ok){
                    vm.intentList = response.data;
                }
            });
        }

    }
});
$("#submitBtn").click(function () {
    //TODO 后台逻辑
    $.axForForm($('#defaultForm'), function (data) {
        if (data.success_is_ok) {
            var targetUrl = "/bidplan/reviewedPlanList"
            redirectUrl(targetUrl)
            return;
        } else {
            errorHandlerFun(data, "#error_place_id");
        }
    });
});

