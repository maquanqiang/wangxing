$(function () {

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
    statistics : {},
    cycleType:["","天","个月","季","年"],
    bpInterestPayTypeArr : ["","一次性还本付息","先息后本，按期付息"],
    bpStatusArr : ["待审核",'审核未通过',"立即投资","已满标",'已过期','','起息中','还款中','','','已结清'],
    sex:['','男','女'],
    fundType : ['','本金','利息'],
    repayStatus : ['未还款','已还款'],
    riskDataList : [],
    investInfoList : [],
    incomeDetailList : [],
    flag : false,
    mySwiper:null
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
    updated:function(){
        if(this.mySwiper == null){
            //文件轮播图
            mySwiper = new Swiper ('.swiper-container', {
                loop: true,
                slidesPerView : 2,
                fade: {
                    crossFade: true
                },
                paginationClickable: true
            });
            $('.button-prev').on('click', function(e){
                e.preventDefault();
                mySwiper.swipePrev()
            });
            $('.button-next').on('click', function(e){
                e.preventDefault();
                mySwiper.swipeNext()
            });
        }

    },
    //方法，可用于绑定事件或直接调用
    methods: {
        search: function (event) {
            var form = {
                bpId:$("#bpId").val()
            }
            //product
            $.post($("#defaultForm").attr("action"), form, function (response) {
                if (response.success_is_ok) {
                    vm.product = response.data;
                    sta_str=(vm.product.bpStartTime).replace(/-/g,"/");
                    end_str=(vm.product.bpEndTime).replace(/-/g,"/");//得到的时间的格式都是：yyyy-MM-dd hh24:mi:ss。
                    var end_str=new Date(end_str);//将字符串转化为时间
                    var st_str = new Date(sta_str);
                    var now = new Date();
                    if(end_str >= now){
                        window.setInterval(function () {
                            tick(end_str);
                        },1000);
                    }
                    if(st_str <= now){
                        vm.flag = true;
                    }
                    //loanerInfo
                    $.post("/api/product/loanerInfo", {lid:vm.product.bpLoanerId}, function (response) {
                        if (response.success_is_ok) {
                            vm.loanerInfo = response.data;
                        }
                    });
                }
            });
            //riskDataList
            $.post("/api/product/riskListByBpId", form, function (response) {
                if (response.success_is_ok) {
                    vm.riskDataList = response.data;

                }
            });
            //investInfoList
            $.post("/api/product/investInfoByBpId", form, function (response) {
                if (response.success_is_ok) {
                    vm.investInfoList = response.data;
                }
            });
            //incomeDetailList
            $.post("/api/product/incomeDetailByBpId", form, function (response) {
                if (response.success_is_ok) {
                    vm.incomeDetailList = response.data;
                }
            });

            //statistics
            $.get("/api/invest/statistics", function (response) {
                if (response.success_is_ok) {
                    vm.statistics = response.data;
                }
            });
        },
        investBtn: function () {
            var investMoney = $("#investMoney").val().trim() * 1;
            if(investMoney > 0){
                if(investMoney > vm.product.bpTopMoney){
                    layer.msg("投资金额大于投资上限"+vm.product.bpTopMoney+"元");
                    $("#investMoney").focus();
                    return;
                }
                if(investMoney > vm.product.bpSurplusMoney){
                    layer.msg("投资金额大于标的剩余金额"+vm.product.bpSurplusMoney+"元");
                    $("#investMoney").focus();
                    return;
                }
                if(investMoney > vm.statistics.balance){
                    layer.msg("投资金额大于您账户的剩余金额"+vm.statistics.balance+"元");
                    $("#investMoney").focus();
                    return;
                }
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
            //投资弹出框
            layer.open({
                title:'投资提示',
                content:'确认投资：金额为'+form.investMoney,
                btn: ['确认', '稍后'],
                area: ['340px', '180px'],
                yes: function() {
                    layer.closeAll();
                    $.post("/api/product/investBid", form, function (response) {
                        if (response.success_is_ok) {
                            var data = response.data;
                            if(data.flag=="true"){
                                window.location.href="/product/productSuccess?investMoney="+form.investMoney;
                            }else{
                                window.location.href="/product/productFail?msg="+data.msg;
                            }
                        }else{
                            layer.alert(response.error);
                            setTximeout(function(){window.location.reload();},5000)
                        }
                    });
                },
                btn2: function(){
                    layer.closeAll();
                }
            })
        },
        test1:function(){
            console.log("test1")
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


function tick(endTime) {
    var  end_str= endTime;
    var oNow=new Date();
    var  total=parseInt((end_str.getTime()-oNow.getTime())/1000);
    var  d=parseInt(total/86400);
    total%=86400;
    var  h=parseInt(total/3600);
    total%=3600;
    var  m=parseInt(total/60);
    total%=60;
    var  s=total;
    //console.log(d+'天'+h+'时'+m+'分'+s+'秒');
    $('#span1').html(d+'天'+h+'时'+m+'分'+s+'秒');
}


