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
    cycleType:["","天","个月","季","年"],
    bpInterestPayTypeArr : ["","一次性还本付息","先息后本，按期付息"],
    bpStatusArr : ["待审核",'审核未通过',"招标中","已满标",'已过期','','起息中','还款中','','','已结清']
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
            $.post(common.apiOrigin+$("#defaultForm").attr("action"), {bpId:$("#bpId").val()}, function (response) {
                if (response.success_is_ok) {
                    vm.product = response.data;
                }
            });
        },
        investBtn: function (id) {
            window.location.href = "/bidplan/addplan/" + id
        }
    }
});

