//验证扩展
$.fn.bootstrapValidator.validators.idCard = {
    validate: function (validator, $field, options) {
        var value = $field.val();
        if (value === '') {
            return true;
        }
        value = value.toUpperCase();
        var idCard = /^[1-9]\d{5}[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2]\d)|(3[0-1]))\d{3}[xX\d]$/;
        var city = {
            11: "北京",
            12: "天津",
            13: "河北",
            14: "山西",
            15: "内蒙古",
            21: "辽宁",
            22: "吉林",
            23: "黑龙江 ",
            31: "上海",
            32: "江苏",
            33: "浙江",
            34: "安徽",
            35: "福建",
            36: "江西",
            37: "山东",
            41: "河南",
            42: "湖北 ",
            43: "湖南",
            44: "广东",
            45: "广西",
            46: "海南",
            50: "重庆",
            51: "四川",
            52: "贵州",
            53: "云南",
            54: "西藏 ",
            61: "陕西",
            62: "甘肃",
            63: "青海",
            64: "宁夏",
            65: "新疆",
            71: "台湾",
            81: "香港",
            82: "澳门",
            91: "国外 "
        };
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
$.fn.bootstrapValidator.validators.bankCard = {
    validate:function(validator, $field, options){
        var value = $field.val();
        if (value === '') {
            return true;
        }
        if(value.length !=16 && value.length != 19){
            return false;
        }

        // 1.将未带校验位的 15（或18）位卡号从右依次编号 1 到 15（18），位于奇数位号上的数字乘以 2。
// 2.将奇位乘积的个十位全部相加，再加上所有偶数位上的数字。
// 3.将加法和加上校验位能被 10 整除。
        var verifyCode = value.substr(value.length-1);
        var baseCode = value.substr(0,value.length-1);
        var sum = 0;
        for (var i=baseCode.length -1;i>=0;i--){
            var currentNumber = +baseCode[i]; //循环的当前数值
            if (i % 2 == 0){
                sum+=currentNumber;
            }else{
                currentNumber = currentNumber * 2;
                sum+= currentNumber % 10 + parseInt(currentNumber/10);
            }
        }
        sum += +verifyCode;
        if (sum % 10 != 0){
            return false;
        }
        return true;
    }
}

