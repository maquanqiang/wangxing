/**
 * Created by Jack on 2016/11/18.
 */
$(function () {
    $("#search_form select.select2").select2();
});
(function($) {
    $.fn.bootstrapValidator.validators.idCard = {
        validate: function(validator, $field, options) {
            var value = $field.val();
            if (value===''){return true;}
            value = value.toUpperCase();
            var idCard = /^[1-9]\d{5}[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2]\d)|(3[0-1]))\d{3}[xX\d]$/;
            var city = { 11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江 ", 31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖北 ", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西藏 ", 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外 " };
            if (!idCard.test(value)) {
                return false;
            }
            else if (!city[value.substr(0, 2)]) {
                return false;
            }
            else {
                //18位身份证需要验证最后一位校验位
                if (value.length == 18) {
                    value = value.split('');
                    //∑(ai×Wi)(mod 11)
                    //加权因子
                    var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
                    //校验位
                    var parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2];
                    var sum = 0;
                    var ai = 0;
                    var wi = 0;
                    for (var i = 0; i < 17; i++) {
                        ai = value[i];
                        wi = factor[i];
                        sum += ai * wi;
                    }
                    var last = parity[sum % 11];
                    if (parity[sum % 11] != value[17]) {
                        return false;
                    }
                }
            }
            return true;
        }
    };
}(window.jQuery));
//Vue实例
//Model
var model = {
    //查询条件
    searchObj: {},
    //所属团队
    teams: [],
    //部门
    departments:[],
    //销售级别
    ranks: [],
    //员工列表
    employees:[],
    //弹窗vm实例
    openFormVm:{}
};

// 创建一个 Vue 实例 (ViewModel),它连接 View 与 Model
var vm = new Vue({
    el: "#content",
    data: model,
    beforeCreate:function(){
        //初始化本地数据
        model.searchObj=$("#search_form").serializeObject();
        model.searchObj.pageIndex=0;
        model.searchObj.pageSize=10;
        //在这里的远程数据更新，必须是在对象已经具备相应属性。否则有可能不会绑定映射
        //团队
        $.get("/api/department/list",function(response){
            if (response.success_is_ok){
                var teams = response.data;
                var departments = new Array();
                teams.forEach(function(item){
                    if(item.isDepartment){
                        departments.push(item);
                    }
                });
                vm.teams=response.data;
                vm.departments=departments;
            }
        });
        //销售级别
        $.get("/api/rank/list",function(response){
            if (response.success_is_ok){
                vm.ranks=response.data;
            }
        });
    },
    //初始化远程数据
    created:function(){
        this.search();
    },
    //方法，可用于绑定事件或直接调用
    methods: {
        search:function(event){
            if (typeof event !== "undefined"){ //点击查询按钮的话，是查询第一页数据
                model.searchObj.pageIndex=0;
            }
            model.searchObj.teamId=$("#teamId").val();
            model.searchObj.rankId=$("#rankId").val();
            $("#btnSearch").addClass("disabled");//禁用按钮
            $.get("/api/employee/list",model.searchObj,function(response){
                if (response.success_is_ok){
                    //递归查询所属部门
                    var findParentDepartmentFunc = function(teamId){
                        var parentId=0;
                        //先检测它本身是否是部门级别，不是的话返回它的父级id
                        for(var i=0;i<vm.teams.length;i++){
                            var item = vm.teams[i];
                            if(item.id === teamId){
                                if (item.isDepartment){
                                    return item.name;
                                }
                                parentId = item.parentId;
                            }
                        }
                        for(var i=0;i<vm.teams.length;i++){
                            var item = vm.teams[i];
                            if(item.id === parentId){
                                return item.isDepartment ? item.name :findParentDepartmentFunc(item.id);
                            }
                        }
                        return "";
                    }
                    for (var i=0;i<response.data.length;i++){
                        var item =response.data[i];
                        item.departmentName =findParentDepartmentFunc(item.teamId);
                    }
                    vm.employees=response.data;
                    if (response.count>0){
                        var pageCount = Math.ceil(response.count / model.pageSize);
                        //调用分页
                        laypage({
                            cont: $('#pageNum'), //容器。值支持id名、原生dom对象，jquery对象,
                            pages: pageCount, //总页数
                            curr:model.searchObj.pageIndex+1,//设置当前页
                            groups: 7, //连续显示分页数
                            jump: function(obj, first){ //触发分页后的回调
                                if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
                                    console.log(obj.curr);
                                    vm.searchObj.pageIndex=obj.curr -1;
                                    vm.search();
                                }
                            },
                            skin: '#3c8dbc'
                        });
                    }
                }
                $("#btnSearch").removeClass("disabled");//解除禁用
            });
        },
        findParentDepartmentFunc:function(teamId){
            var parentId=0;
            //先检测它本身是否是部门级别，不是的话返回它的父级id
            for(var i=0;i<vm.teams.length;i++){
                var item = vm.teams[i];
                if(item.id === teamId){
                    if (item.isDepartment){
                        return {id:item.id,name:item.name};
                    }
                    parentId = item.parentId;
                }
            }
            for(var i=0;i<vm.teams.length;i++){
                var item = vm.teams[i];
                if(item.id === parentId){
                    return item.isDepartment ? {id:item.id,name:item.name} :findParentDepartmentFunc(item.id);
                }
            }
            return {id:0,name:""};
        },
        //绑定表单验证
        bindFormValidate:function($form){
            $form.bootstrapValidator({
                fields: {
                    name: {
                        validators: {
                            notEmpty: {
                                message: '姓名不能为空'
                            },
                            stringLength: {
                                min: 2,
                                max: 5,
                                message: '姓名长度必须在2到5位之间'
                            }
                        }
                    },
                    mobile:{
                        validators: {
                            notEmpty: {
                                message: '手机号不能为空'
                            },
                            regexp: {
                                regexp: /^1[3-8]\d{9}$/,
                                message: '请输入正确的手机号码'
                            }
                        }
                    },
                    cardNo: {
                        validators: {
                            notEmpty: {
                                message: '身份证不能为空'
                            },
                            idCard: {
                                message: '请输入正确的身份证号码'
                            }
                        }
                    },
                    rankId:{
                        validators: {
                            greaterThan: {
                                value: 1,
                                message: '请选择员工级别'
                            }
                        }
                    },
                    departmentId:{
                        validators: {
                            greaterThan: {
                                value: 1,
                                message: '请选择部门'
                            }
                        }
                    },
                    teamId:{
                        validators: {
                            greaterThan: {
                                value: 1,
                                message: '请选择团队'
                            }
                        }
                    }
                }
            });
        },
        post:function($form){
            var $button =$form.parent().parent().children(".layui-layer-btn").children("a:first");
            $button.addClass("btn disabled");
            layer.load(2);
            var submitModel = $form.serializeObject();
            $.post("/api/employee/post",submitModel,function(response){
                if (response.success_is_ok){
                    layer.msg(response.msg);
                    vm.search();
                }else{
                    vm.openFormVm.error.hide=false;
                    vm.openFormVm.error.message=response.msg;
                }
                $button.removeClass("disabled");
                layer.closeAll();
            });
        },
        createOpenVm:function(form,empId){
            var openVmModel ={
                form:form,
                ranks:vm.ranks,
                departments:vm.departments,
                formData:$(form).serializeObject(),
                noTeamRankId:1, //不显示团队select的职级id
                error:{hide:true,message:""}
            };
            openVmModel.formData.empId=empId;
            vm.openFormVm = new Vue({
                el: openVmModel.form,
                data: openVmModel,
                computed:{
                    //根据选择部门动态变化所属团队
                    partOfTeams:function(){
                        var departmentId = this.formData.departmentId;
                        var localPartOfTeams = new Array();
                        //递归查找子级
                        var recursiveFunc = function(teamId){
                            for(var i=0;i<vm.teams.length;i++){
                                var item = vm.teams[i];
                                if(item.parentId === teamId){
                                    localPartOfTeams.push(item);
                                    recursiveFunc(item.id);//继续寻找子级的子级
                                }
                            }
                        }
                        if(departmentId>0){
                            recursiveFunc(departmentId);
                        }
                        if(openVmModel.formData.empId==0){
                            this.formData.teamId=0;//回归 请选择
                        }
                        return localPartOfTeams;
                    },
                    isEdit:function(){
                        return this.formData.empId>0;
                    }
                },
                beforeCreate:function(){
                    var empId =openVmModel.formData.empId;
                    //填充窗体数据
                    if (empId>0){
                        for (var i=0;i<vm.employees.length;i++){
                            var item =vm.employees[i];
                            if (item.id==empId){
                                openVmModel.formData.name = item.name;
                                openVmModel.formData.mobile = item.mobile;
                                openVmModel.formData.cardNo = item.cardNo;
                                openVmModel.formData.rankId = item.rankId;
                                openVmModel.formData.departmentId = vm.findParentDepartmentFunc(item.teamId).id;
                                openVmModel.formData.teamId = item.teamId;
                            }
                        }
                    }
                },
                created:function(){

                },
                mounted:function(){
                    var $form = $(openVmModel.form);
                    vm.bindFormValidate($form);
                }
            });
        },
        openPostForm:function(empId){
            if (isNaN(empId)){empId=0;}
            var tempObj= $('#addInforModal').clone();
            tempObj.find('form').prop('id','insertFormId');
            var tempHtml=tempObj.html();
            layer.open({
                title:empId>0?'修改员工信息':'添加员工',
                content:tempHtml,
                btn: ['保存', '重置'],
                area:['500px'],
                btn1: function(){
                    var $form = $("#insertFormId");
                    var bootstrapValidator = $form.data('bootstrapValidator').validate();
                    if(!bootstrapValidator.isValid()){
                        return false;
                    }else{
                        vm.post($form);
                    }
                },
                btn2: function(){
                    var $form =$("#insertFormId");
                    var bootstrapValidator =$form.data('bootstrapValidator');
                    if(typeof bootstrapValidator !== "undefined"){
                        bootstrapValidator.resetForm();
                    }
                    $form[0].reset();
                    return false;
                }
            });
            vm.createOpenVm("#insertFormId",empId);

        },
        openEditForm:function(empId){
            vm.openPostForm(empId);
        },
        openDeleteWin:function(empId){
            layer.confirm('确定要删除吗?', {icon: 3, title:'询问'}, function(index){
                layer.load(2);
                $.post("/api/employee/delete",{empId:empId},function(response){
                    if (response.success_is_ok){
                        layer.msg(response.msg);
                        vm.search();
                    }else{
                        layer.alert(response.msg);
                    }
                });
                layer.closeAll();
            });
        }
    }
});
