function redirectUrl(targetUrl) {
    window.location.href=targetUrl;
};

$(window).load(function(){
    activeMenu();
});
//根据url地址来判断左侧菜单选中
function activeMenu() {
    var hrefVal=window.location.pathname;
    var menuObj=$('#ulList').find("a[href$='"+hrefVal+"']");
    if(typeof menuObj=="undefined"){
        return;
    }
    menuObj.parent().addClass('active');
    var menuParent={};
    var idVal="";
    var __i=0;
    while(idVal!="ulList")
    {
        menuParent=  menuObj.parent();
        idVal=menuParent.attr('id');
        if(menuParent.hasClass('treeview')&&menuParent.hasClass('active')==false)
        {
            menuParent.addClass('active');
        }
        menuObj=menuParent;
        __i=__i+1;
        if(__i>4)break;
    }
   /* do {
        menuParent=  menuObj.parent();
        if(menuParent.hasClass('treeview')&&menuParent.hasClass('active')==false)
        {
            menuParent.addClass('active');
        }
        menuObj=menuParent;
        idVal=menuParent.attr('id');
    }while (idVal!="ulList")*/
}
//表单登录验证封装
function initValidateForm(obj){
    obj.bootstrapValidator({
        fields: {
            user: {
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
            tel:{
                validators: {
                    notEmpty: {
                        message: '手机号不能为空'
                    },
                    stringLength: {
                        min: 11,
                        max: 11,
                        message: '手机号长度必须11位'
                    }
                }
            },
            sex: {
                validators: {
                    notEmpty: {
                        message: '性别不能为空'
                    },
                    stringLength: {
                        min: 1
                    }
                }
            },
            ID: {
                validators: {
                    notEmpty: {
                        message: '身份证不能为空'
                    }
                }
            },
            residence:{
                validators: {
                    notEmpty: {
                        message: '户口所在地不能为空'
                    }
                }
            },
            address:{
                validators: {
                    notEmpty: {
                        message: '家庭住址不能为空'
                    }
                }
            },
            department:{
                validators: {
                    notEmpty: {
                        message: '部门不能为空'
                    }
                }
            },
            staffLevel:{
                validators: {
                    notEmpty: {
                        message: '员工级别不能为空'
                    }
                }
            },
            team:{
                validators: {
                    notEmpty: {
                        message: '所属团队不能为空'
                    }
                }
            },
            inDepartment:{
                validators: {
                    notEmpty: {
                        message: '所属部门不能为空'
                    }
                }
            },
            company:{
                validators: {
                    notEmpty: {
                        message: '公司不能为空'
                    }
                }
            },
            group:{
                validators: {
                    notEmpty: {
                        message: '集团不能为空'
                    }
                }
            }
        }
    });
}

