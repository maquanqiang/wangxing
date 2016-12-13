$(function () {

    var mySwiper = new Swiper ('.swiper-container', {
        effect : 'fade',
        fade: {
            crossFade: true
        },
        paginationClickable: true,
        loop:true,
        // 如果需要前进后退按钮
        nextButton: '.swiper-button-next',
        prevButton: '.swiper-button-prev'
    })
    //输入金额实时
    $('.entry-num').bind('input propertychange', function() {
        $('.money-num').html(parseInt($(this).val()*1));
        var bpExpectLoanDate = new Date(model.product.bpExpectLoanDate);
        var bpExpectRepayDate = new Date(model.product.bpExpectRepayDate);
        var days = bpExpectRepayDate.getTime() - bpExpectLoanDate.getTime();
        var time = parseInt(days / (1000 * 60 * 60 * 24));
        var interest = parseInt(($(this).val())*time*(model.product.bpRate)/100/365);
        $('.money-income').html(interest);
    });
});



//Vue实例
//Model
var model = {
    product: {},
    loanerInfo : {},
    cycleType:["","天","个月","季","年"],
    bpInterestPayTypeArr : ["","一次性还本付息","先息后本，按期付息"],
    bpStatusArr : ["待审核",'审核未通过',"招标中","已满标",'已过期','','起息中','还款中','','','已结清'],
    sex:['','男','女'],
    fundType : ['','本金','利息'],
    repayStatus : ['未还款','已还款'],
    riskDataList : [],
    investInfoList : [],
    incomeDetailList : []
};

// 创建一个 Vue 实例 (ViewModel),它连接 View 与 Model
var vm = new Vue({
    el: ".project",
    data: model,
    beforeCreate: function () {
        //初始化本地数据
    },
    //初始化远程数据
    created: function () {
        this.search();
    },
    //方法，可用于绑定事件或直接调用
    methods: {
        search: function (event) {
            var form = {
                bpId:$("#bpId").val()
            }
            //product
            $.post(common.apiOrigin+$("#defaultForm").attr("action"), form, function (response) {
                if (response.success_is_ok) {
                    vm.product = response.data;
                    //loanerInfo
                    $.post(common.apiOrigin+"/api/product/loanerInfo", {lid:vm.product.bpLoanerId}, function (response) {
                        if (response.success_is_ok) {
                            vm.loanerInfo = response.data;
                        }
                    });
                }
            });
            //riskDataList
            $.post(common.apiOrigin+"/api/product/riskListByBpId", form, function (response) {
                if (response.success_is_ok) {
                    vm.riskDataList = response.data;
                }
            });
            //investInfoList
            $.post(common.apiOrigin+"/api/product/investInfoByBpId", form, function (response) {
                if (response.success_is_ok) {
                    vm.investInfoList = response.data;
                }
            });
            //incomeDetailList
            $.post(common.apiOrigin+"/api/product/incomeDetailByBpId", form, function (response) {
                if (response.success_is_ok) {
                    vm.incomeDetailList = response.data;
                }
            });
        },
        investBtn: function () {
            var investMoney = $("#investMoney").val().trim() * 1;
            if(investMoney > 0){
                if((investMoney-vm.product.bpStartMoney)>=0){
                    if((investMoney-vm.product.bpStartMoney) % vm.product.bpRiseMoney !=0){
                        layer.msg("投资金额不符合递增规则");
                        $("#investMoney").focus();
                        return;
                    }
                }else{
                    layer.msg("投资金额小于起投金额"+vm.product.bpStartMoney+"元")
                    $("#investMoney").focus();
                    return;
                }
            }else{
                layer.msg("投资金额输入有误");
                $("#investMoney").focus();
                return;
            }


            var form = {bpId:$("#bpId").val(), investMoney:$("#investMoney").val().trim()}
            $.post(common.apiOrigin+"/api/product/investBid", form, function (response) {
                if (response.success_is_ok) {
                    layer.alert(response.msg);
                }else{
                    layer.alert(response.error);
                }
            });
        }
    }
});

//切换封装
function tab(btn,box){
    btn.click(function (){
        btn.removeClass('active');
        box.removeClass('active');
        $(this).addClass('active');
        var index=$(this).index();
        box.eq(index).addClass('active');
    });
}
//项目详情
tab($('.project-detail-tit h4'),$('.project-detail-info'));