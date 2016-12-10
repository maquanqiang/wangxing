$(function () {

    //调用分页
    laypage({
        cont: $('.page'), //容器。值支持id名、原生dom对象，jquery对象,
        pages: 100, //总页数
        skip: true, //是否开启跳页
        skin: '#e88a6e',
        groups: 3 //连续显示分页数
    });

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
});


//Vue实例
//Model
var model = {
    //列表
    product: [],
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
            console.log($("#defaultForm").attr("action"))
            $.post(common.apiOrigin+$("#defaultForm").attr("action"), {bpId:$("#bpId").val()}, function (response) {
                if (response.success_is_ok) {
                    product = response.data;
                }
            });
        },
        investBtn: function (id) {
            window.location.href = "/bidplan/addplan/" + id
        }
    }
});

