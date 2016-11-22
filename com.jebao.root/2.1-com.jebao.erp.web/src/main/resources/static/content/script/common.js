/**
 * Created by Jack on 2016/11/18.
 */
(function () {
    function storageGet(key, isLocal) {
        var value = isLocal ? localStorage.getItem(key) : sessionStorage.getItem(key);//获取指定key本地存储的值
        try {
            return JSON.parse(value);
        } catch (e) {
            return value;
        }
    }
    function storageSet(key, value, isLocal) {
        if (value == null) {
            isLocal ? localStorage.removeItem(key) : sessionStorage.removeItem(key);
            return true;
        }
        if (typeof value === "object") {
            value = JSON.stringify(value);
        }
        //将value存储到key字段 -- storage只支持存储字符串
        isLocal ? localStorage.setItem(key, value) : sessionStorage.setItem(key, value);
    }
    //页面公共对象
    var common = {
        apiOrigin:"http://localhost:9089",
        //获取url参数
        getUrlParam: function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]);
            else return null;
        },
        //html 5 的缓存 数据存储.key/value形式存储。value 任意格式数据. local指示是否持久化存储。默认会话存储
        storage: function (key, value, local) {
            if (arguments.length == 1) {
                return storageGet(key, false);
            } else if (arguments.length == 3) {
                return storageSet(key, value, local);
            } else {
                if (typeof arguments[1] === "boolean") {
                    return storageGet(key, arguments[1]);
                } else {
                    return storageSet(key, value, false);
                }
            }
        },
        //显示文本内容
        toText: function (obj) {
            if (obj == null) {
                return '';
            }
            return obj.toString();
        },
        //将json日期格式化显示 数字转日期
        formatJsonDate: function (str, format) {
            if (str == null || str.length == 0) { return ''; }
            str = str.replace(/T/g, " ").replace(/-/g, "/").substr(0, 19);
            var d = new Date(str);
            format = format || 'yyyy-MM-dd';
            return d.toFormatString(format);
        }
    };

    window.common = common;
})(jQuery);

/********js/jquery等 扩展**********/
///日期格式化方法
Date.prototype.toFormatString = function (format) {
    var strMonth = (this.getMonth() + parseInt(1)).toString();
    strMonth = (strMonth.length == 1) ? ("0" + strMonth) : strMonth;
    var strDate = this.getDate().toString();
    strDate = (strDate.length == 1) ? ("0" + strDate) : strDate;
    var strHour = this.getHours().toString();
    strHour = (strHour.length == 1) ? ("0" + strHour) : strHour;
    var strMin = this.getMinutes().toString();
    strMin = (strMin.length == 1) ? ("0" + strMin) : strMin;
    var strSen = this.getSeconds().toString();
    strSen = (strSen.length == 1) ? ("0" + strSen) : strSen;
    if (format) {
        if (format.indexOf("yyyy")>-1) {
            format = format.replace("yyyy", this.getFullYear());
        } else {
            format = format.replace("yy", this.getYear() - 100);
        }
        format = format.replace("MM", strMonth);
        format = format.replace("dd", strDate);
        format = format.replace("HH", strHour);
        format = format.replace("mm", strMin);
        format = format.replace("ss", strSen);
        return format;
    } else {
        return this.getFullYear() + "-" + strMonth + "-" + strDate;
    }
};
//jQuery扩展
(function ($) {
    $.extend($.fn, {
        //序列化表单元素为一个对象
        serializeObject: function () {
            var obj = {};
            $(this).find("input[name]:not([type='button']),textarea[name],select[name]").map(function (index,item) {
                obj[item.name] = $(item).val();
            });
            return obj;
        },
        //设置禁用时长delay //$(this).setDisabled(3000);
        setDisabled: function (timeout) {
            var $target = $(this).addClass("disabled");
            setTimeout(function () {
                $target.removeClass("disabled");
            }, timeout);
        }
    });

}(jQuery));


