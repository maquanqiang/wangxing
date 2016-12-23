$(function () {
    var mySwiper = new Swiper ('.swiper-container', {
        loop: true,
        autoplay: 2500,
        pagination : '.pagination',
//            pagination: '.swiper-pagination',
//            effect : 'fade',
        fade: {
            crossFade: true
        },
        paginationClickable: true
    });
    $('.button-prev').on('click', function(e){
        e.preventDefault()
        mySwiper.swipePrev()
    });
    $('.button-next').on('click', function(e){
        e.preventDefault()
        mySwiper.swipeNext()
    });
    //无缝滚动
    $('.dowebok').liMarquee({
        direction: 'up'
    });


});

//    排行榜
var myVue = new Vue({
    el: ".rank-list",
    data: {
        items:[
            {
                name:"司**",
                money:'4350000'
            },
            {
                name:"王**",
                money:'3000000'
            },
            {
                name:"高**",
                money:'670000'
            },
            {
                name:"吴**",
                money:'670000'
            },
            {
                name:"王**",
                money:'625000'
            },
            {
                name:"杨**",
                money:'600000'
            },
            {
                name:"谢**",
                money:'580000'
            }
        ],
        recentInvest :[]
    },
    created: function () {
        $.post("/api/product/recentInvestment",function(response){
            if(response.success_is_ok){
                myVue.recentInvest = response.data;
            }
        })
    },
    filters:{
        currency:function (val) {
            return '$' + val;
        }
    }
})
//Vue实例
//Model
var model = {
    //查询条件
    searchObj: {},
    //列表
    products1: [],
    products2: [],
    recentInvest : [],
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
        model.searchObj.pageSize = 2;
    },
    //初始化远程数据
    created: function () {
        this.search();
    },
    //方法，可用于绑定事件或直接调用
    methods: {
        search: function (event) {
            model.searchObj.bpType = 1;
            $.post("/api/product/list", model.searchObj, function (response) {
                if (response.success_is_ok) {
                    vm.products1 = response.data;
                }
            });
            model.searchObj.pageSize = 1;
            model.searchObj.bpType = 2;  //新手标
            $.post("/api/product/list", model.searchObj, function (response) {
                if (response.success_is_ok) {
                    vm.products2 = response.data;
                }
            });
        },
        openDetail: function (id) {
            window.location.href = "/product/detail/" + id;
        }
    }
});

