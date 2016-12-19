/**
 * Created by Jack on 2016/12/16.
 */
var model = {
    hasFundAccount:false,
    bankName:"",
    bankCardNo:""
};
var vm = new Vue({
    el:".project",
    data:model,
    beforeCreate:function(){
        $.get("/api/user/getuser",function(response){
            if (response.success_is_ok) {
                model.hasFundAccount = response.data.hasFundAccount;
                model.bankName=response.data.bankName;
                model.bankCardNo=response.data.bankCardNo;
            }
        });
    },
    methods:{
        goChangeCard:function(){
            window.location.href= common.apiOrigin + "/api/userfund/goChangeCard";
        }
    }
});