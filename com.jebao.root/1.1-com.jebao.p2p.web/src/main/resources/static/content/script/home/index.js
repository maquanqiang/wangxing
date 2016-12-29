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
});

//    排行榜
var myVue = new Vue({
    el: ".rank-list",
    data: {
        //排行
        items:[],
        //最近投资
        recentInvest :[],
        limarqueeObj:null
    },
    updated : function(){
        //无缝滚动
        if(this.limarqueeObj == null && this.recentInvest.length > 0){
            this.limarqueeObj = $('.dowebok').liMarquee({
                direction: 'up'
            });
        }

    },
    created: function () {
        //recentInvest
        $.post("/api/product/recentInvestment",function(response){
            if(response.success_is_ok){
                myVue.recentInvest = response.data;
            }
        }),
            //items
        $.post("/api/product/investmentTop",function(response){
            if(response.success_is_ok){
                myVue.items = response.data;
            }
        })

    },
    filters:{
        currency:function (val) {
            return '¥' + val;
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
            //同步账户金额
            $.get("/api/user/syncUserBalance");
            window.location.href = "/product/detail/" + id;
        },
        getArticle:function(typeId,count){
            var dataVal = {"typeId":typeId,"pageIndex":0,"pageSize":count};
            $.get("/api/article/list", dataVal, function (response) {
                if (response.success_is_ok) {
                    var data = response.data;
                    vm.articles = data;
                }
            });
        }
    }
});

var aVm = new Vue({
    el: ".new-announcement",
    data: {
        articles: []
    },
    beforeCreate: function () {
    },
    //初始化远程数据
    created: function () {
        this.getArticle(1,3);//公司动态
    },
    //方法，可用于绑定事件或直接调用
    methods: {
        getArticle:function(typeId,count){
            var dataVal = {"typeId":typeId,"pageIndex":0,"pageSize":count};
            $.get("/api/article/list", dataVal, function (response) {
                if (response.success_is_ok) {
                    var data = response.data;
                    aVm.articles = data;
                }
            });
        }
    }
});

var mVm = new Vue({
    el: ".media",
    data: {},
    beforeCreate: function () {
    },
    //初始化远程数据
    created: function () {
        this.getArticle(3,3);
    },
    //方法，可用于绑定事件或直接调用
    methods: {
        getArticle:function(typeId,count){
            var dataVal = {"typeId":typeId,"pageIndex":0,"pageSize":count};
            $.get("/api/article/index", dataVal, function (response) {
                if (response.success_is_ok) {
                    var data = response.data;
                   // console.log(data);
                   // $("#mn_0").html(mVm.mediaNews[0].content);
                    for(var i=0;i<data.length;i++){
                        $("#media_ul").append("<li><a href='/html/mediaNews/details/"+data[i].id+"'>"+data[i].content+"</a></li>");
                    }
                }
            });
        }
    }
});
