package com.jebao.common.utils.validation;

import java.util.regex.Pattern;

/**
 * Created by Jack on 2016/12/12.
 */
public class ValidatorUtil {
    public boolean isMobile(String input){
        return Pattern.matches("^1(3|4|5|7|8)\\d{9}$",input);
    }
}
