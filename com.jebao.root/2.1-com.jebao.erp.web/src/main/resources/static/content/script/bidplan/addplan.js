

$(function () {
    $(".select2").select2();
    /*时间选择*/
    //$('.chooseDate').datepicker({
    //    format: 'yyyy-mm-dd hh:mm:ss',
    //    weekStart: 1,
    //    autoclose: true,
    //    todayBtn: 'linked',
    //    language: 'cn'
    //});

});

//Vue实例
//Model
var model = {
    //列表
    projList: [],
    projectTemp : {},
    selected : '',
    intentList : [],
    principalTotal : 0,
    interestTotal : 0,
    total : 0

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
        $.get("/api/bidPlan/getProjList",loanerId,function(response){
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
            $.get("/api/bidPlan/getProjectTempById",rcptId,function(response){
                if (response.success_is_ok){
                    vm.projectTemp = response.data;
                    KindEditor.html("#kindEditorContent", vm.projectTemp.bpRcptDesc);
                }
            });
        },
        "bpStartTime" : function(val, oldVal){
            console.log(val)
        }

    },
    //方法，可用于绑定事件或直接调用
    methods: {
        //表单登录验证封装
        myInitValidateForm: function (obj) {
            obj.bootstrapValidator({
                fields: {
                    bpNumber: {
                        validators: {
                            notEmpty: {
                                message: '项目编号不能为空'
                            }
                        }
                    },
                    bpName: {
                        validators: {
                            notEmpty: {
                                message: '标的名称不能为空'
                            }
                        }
                    },
                    bpType: {
                        validators: {
                            notEmpty: {
                                message: '产品类型不能为空'
                            }
                        }
                    },
                    bpStartTime: {
                        validators: {
                            notEmpty: {
                                message: '募集时间不能为空'
                            }
                        }
                    },
                    bpOpenTime: {
                        validators: {
                            notEmpty: {
                                message: '募集期限不能为空'
                            }
                        }
                    },
                    bpEndTime: {
                        validators: {
                            notEmpty: {
                                message: '募集结束不能为空'
                            }
                        }
                    },
                    bpStartMoney: {
                        validators: {
                            notEmpty: {
                                message: '起投金额不能为空'
                            }
                        }
                    },
                    bpRiseMoney: {
                        validators: {
                            notEmpty: {
                                message: '递增金额不能为空'
                            }
                        }
                    },
                    bpTopMoney: {
                        validators: {
                            notEmpty: {
                                message: '投资上限不能为空'
                            }
                        }
                    },
                    bpBidMoney: {
                        validators: {
                            notEmpty: {
                                message: '募集金额不能为空'
                            }
                        }
                    },
                    bpRate: {
                        validators: {
                            notEmpty: {
                                message: '借款利率不能为空'
                            }
                        }
                    },
                    bpPeriodsDisplay: {
                        validators: {
                            notEmpty: {
                                message: '借款期限不能为空'
                            }
                        }
                    },
                    bpCycleType: {
                        validators: {
                            notEmpty: {
                                message: '周期选项不能为空'
                            }
                        }
                    },
                    bpExpectLoanDate: {
                        validators: {
                            notEmpty: {
                                message: '预期放款日期不能为空'
                            }
                        }
                    },
                    bpExpectRepayDate: {
                        validators: {
                            notEmpty: {
                                message: '预期还款日期不能为空'
                            }
                        }
                    },
                    bpInterestPayType: {
                        validators: {
                            notEmpty: {
                                message: '还款方式不能为空'
                            }
                        }
                    },
                    bpServiceChargeRate: {
                        validators: {
                            notEmpty: {
                                message: '平台服务费不能为空'
                            }
                        }
                    },
                    bpLateRate: {
                        validators: {
                            notEmpty: {
                                message: '逾期罚息不能为空'
                            }
                        }
                    }
                }
            });
        },
        search:function(event){
        },
        createIntent:function(){
            vm.intentList = [];
            vm.principalTotal = 0;
            vm.interestTotal = 0;
            vm.total = 0;
            var formValue = $("#defaultForm").serializeObject();
            $.get("/api/bidPlan/getLoanFundIntents",formValue,function(response){
                if (response.success_is_ok){
                    vm.intentList = response.data;
                    for(var i=0; i<vm.intentList.length; i++){
                        vm.principalTotal +=vm.intentList[i].principal;
                        vm.interestTotal += vm.intentList[i].interest;
                    }
                    vm.total = vm.principalTotal +vm.interestTotal;
                }
            });
        },
        addBtn:function(){
            vm.myInitValidateForm($('#defaultForm'));
            var bootstrapValidator = $("#defaultForm").data('bootstrapValidator').validate();
            if (!bootstrapValidator.isValid()) {
                return false;
            }
            $("#bpPeriods").val($("#bpPeriodsDisplay").val())
            var formValue = $("#defaultForm").serializeObject();
            $.post("/api/bidPlan/doAddPlan",formValue,function(response){
                if (response.success_is_ok) {
                    layer.msg(response.msg);
                    vm.toIndex();
                } else {
                    layer.alert(response.msg);
                }
            })
        },
        cancelBtn:function(){
            vm.toIndex()
        },
        toIndex:function(){
            window.location.href = "/bidplan/index";
        }
    }
    //computed: {
    //    // 一个计算属性的 getter
    //    bpEndTime: function () {
    //
    //        alert($("#bpStartTime").val())
    //        date.setHours(date.getHours() + value);
    //        return date;
    //    }
    //}
});

function endTime(startTime, d){
    startTime = startTime.replace(/-/g,"/");
    var date = new Date(startTime);
    date = date.valueOf();
    date = date + d * 60 * 60 * 1000;
    var nd = new Date(date);
    var time1 = nd.toFormatString("yyyy-MM-dd HH:mm:ss");
    return time1;
}

function repayDate(loanDate, d, cycle){
    d *= 1;
    cycle *= 1;
    if(!isNaN(cycle) && isloanDate.length !=0 && !isNaN(d)){
        if(cycle==1){

        }else if(cycle==2){

        }else if(cycle == 3){

        }else if(cycle ==4 ){

        }
    }
}


/*开始时间选择*/
laydate({
    elem:'#bpStartTime',
    istime: true,
    istoday : true,
    format: 'YYYY-MM-DD hh:mm:ss',
    choose : function(datas) {
        var bpOpenTime = $("#bpOpenTime").val();
        if(bpOpenTime!=null && bpOpenTime != ""){
            var time = endTime(datas,bpOpenTime);
            $("#bpEndTime").val(time)
        }
    }
});
/*结束时间选择*/
//laydate({
//    elem:'#bpEndTime',
//    istime: true,
//    istoday : true,
//    format: 'YYYY-MM-DD hh:mm:ss'
//});

laydate({
    elem:'#bpExpectLoanDate',
    istime: true,
    format: 'YYYY-MM-DD',
    istoday : true,
    choose : function(datas){
        alert(datas)
    }
});

laydate({
    elem:'#bpExpectRepayDate',
    istime: true,
    format: 'YYYY-MM-DD'
});

$("#bpOpenTime").change(function(){
    var bpStartTime = $("#bpStartTime").val();
    if(bpStartTime!=null && bpStartTime != ""){
        var time = endTime(bpStartTime,$(this).val());
        $("#bpEndTime").val(time);
    }
});
