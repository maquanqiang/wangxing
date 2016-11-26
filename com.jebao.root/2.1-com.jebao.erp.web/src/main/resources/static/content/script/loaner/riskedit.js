/**
 * Created by Administrator on 2016/11/24.
 */
var model = {
    risk: {}
};
// 创建一个 Vue 实例 (ViewModel),它连接 View 与 Model
var vm = new Vue({
    el: ".content",
    data: model,
    //初始化远程数据
    created: function () {
        var rcptId = $("#defaultForm").find("[name=rcptId]").val();
        if (rcptId > 0) {
            var dataVal = $("#defaultForm").serializeObject();
            $.get("/api/risk/getRisk", dataVal, function (response) {
                if (response.success_is_ok) {
                    var data = response.data;
                    //设置kindEditorContent编辑器
                    KindEditor.html("#kindEditorContent",data.desc);
                    vm.risk = data;
                }
            });
        }
    }
});

$(document).ready(function() {
    initValidateForm();
    $('#btn-ok').click(function(){
        var loanerId = $("#order_search_form").find("[name=loanerId]").val();
        var bootstrapValidator = $("#order_search_form").data('bootstrapValidator').validate();
        if(!bootstrapValidator.isValid()){return;}
        //TODO 后台逻辑
        $.axForForm($('#order_search_form'),function(data){
            if(data.success_is_ok)
            {
                layer.alert('保存成功！', function () {
                    layer.closeAll();
                });
                var targetUrl="/loaner/risklist/"+loanerId;
                redirectUrl(targetUrl)
                return;
            }else{
                layer.alert(data.msg, function () {
                    layer.closeAll();
                });
            }
        });
    })
});

//表单登录验证封装
function initValidateForm() {
    $('#order_search_form').bootstrapValidator({
        fields: {
            name: {
                message: '项目名称验证失败',
                validators: {
                    notEmpty: {
                        message: '项目名称不能为空'
                    }
                }
            },
            borrowDesc: {
                message: '借款描述验证失败',
                validators: {
                    notEmpty: {
                        message: '借款描述不能为空'
                    }
                }
            },
            fundsPurpose: {
                message: '资金用途验证失败',
                validators: {
                    notEmpty: {
                        message: '资金用途不能为空'
                    }
                }
            },
            repayingSource: {
                message: '还款来源验证失败',
                validators: {
                    notEmpty: {
                        message: '还款来源不能为空'
                    }
                }
            },
            mortgageInfo: {
                message: '抵押信息验证失败',
                validators: {
                    notEmpty: {
                        message: '抵押信息不能为空'
                    }
                }
            },
            personalCredit: {
                message: '个人征信验证失败',
                validators: {
                    notEmpty: {
                        message: '个人征信不能为空'
                    }
                }
            },
            opinion: {
                message: '风控意见验证失败',
                validators: {
                    notEmpty: {
                        message: '风控意见不能为空'
                    }
                }
            }
            /*            password: {
             validators: {
             notEmpty: {
             message: '密码不能为空'
             },
             stringLength: {
             min: 6,
             max: 10,
             message: '密码长度必须在6到18位之间'
             },
             regexp: {
             regexp:/^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]{6,22}$/,
             message: '密码格式有误'
             },
             different: {
             field: 'username',
             message: '密码不能和用户名相同'
             }
             }
             },*/
        },
        submitHandler: function (form) {

        }
    });
}
