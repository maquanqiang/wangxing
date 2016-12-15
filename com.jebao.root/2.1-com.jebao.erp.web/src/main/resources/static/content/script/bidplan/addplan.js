

$(function () {
    $(".select2").select2();
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
                        vm.principalTotal += (vm.intentList[i].principal)*1;
                        vm.interestTotal += (vm.intentList[i].interest)*1;
                    }
                    vm.total = vm.principalTotal +vm.interestTotal;
                    vm.principalTotal=toDecimal2(vm.principalTotal);
                    vm.interestTotal=toDecimal2(vm.interestTotal);
                    vm.total=toDecimal2(vm.total);
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
                    layer.alert(response.error);
                }
            })
        },
        cancelBtn:function(){
            window.location.href = "/bidplan/index";
        },
        toIndex:function(){
            window.location.href = "/bidplan/reviewedPlanList";
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


/*开始时间选择*/
laydate({
    elem:'#bpStartTime',
    istime: true,
    istoday : false,
    format: 'YYYY-MM-DD hh:mm:ss',
    choose : function(datas) {
        var bpOpenTime = $("#bpOpenTime").val();
        if(bpOpenTime!=null && bpOpenTime != ""){
            var time = endTime(datas,bpOpenTime);
            $("#bpEndTime").val(time)
        }
    }
});


laydate({
    elem:'#bpExpectLoanDate',
    istime: false,
    format: 'YYYY-MM-DD',
    istoday : false,
    choose : function(datas){
        var d = $("#bpPeriodsDisplay").val();
        var cycle = $("#bpCycleType").val();
        var date = repayDate(datas, d, cycle);
        $("#bpExpectRepayDate").val(date);
    }
});


$("#bpOpenTime").change(function(){
    var bpStartTime = $("#bpStartTime").val();
    if(bpStartTime!=null && bpStartTime != ""){
        var time = endTime(bpStartTime,$(this).val());
        $("#bpEndTime").val(time);
    }
});


$("#bpPeriodsDisplay").change(function(){
    var datas = $("#bpExpectLoanDate").val();
    var cycle = $("#bpCycleType").val();
    var date = repayDate(datas, $(this).val(), cycle);
    $("#bpExpectRepayDate").val(date);
});

$("#bpCycleType").change(function(){
    var datas = $("#bpExpectLoanDate").val();
    var d = $("#bpPeriodsDisplay").val();
    var date = repayDate(datas, d, $(this).val());
    $("#bpExpectRepayDate").val(date);
});