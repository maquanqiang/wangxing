$(function () {
    var mySwiper = new Swiper ('.swiper-container', {
        pagination: '.swiper-pagination',
        effect : 'fade',
        fade: {
            crossFade: true
        },
        paginationClickable: true,
        autoplay:3500,
        autoplayDisableOnInteraction:false,
        loop:true,
        // 如果需要前进后退按钮
        nextButton: '.swiper-button-next',
        prevButton: '.swiper-button-prev'
    })

});


//Vue实例
//Model
var model = {
    //查询条件
    searchObj: {},
    //列表
    products: [],
    cycleType:["","天","个月","季","年"],
    status:["","","立即投资","满标","募集结束","","","还款中","","","已还款"]
};

// 创建一个 Vue 实例 (ViewModel),它连接 View 与 Model
var vm = new Vue({
    el: ".project-list",
    data: model,
    beforeCreate: function () {
        //初始化本地数据

        model.searchObj.pageIndex = 0;
        model.searchObj.pageSize = 7;
    },
    //初始化远程数据
    created: function () {
        this.search();
    },
    //方法，可用于绑定事件或直接调用
    methods: {
        search: function (event) {
            $.post(common.apiOrigin+"/api/product/list", model.searchObj, function (response) {
                if (response.success_is_ok) {
                    vm.products = response.data;
                }
            });
        },
        openDetail: function (id) {
            window.location.href = "/product/detail/" + id;
        }
    }
});

