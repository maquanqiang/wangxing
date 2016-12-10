package com.jebao.p2p.web.api.utils.captcha;

import com.jebao.common.cache.redis.sharded.ShardedRedisUtil;
import com.jebao.common.utils.sms.SmsSendUtil;
import com.jebao.p2p.web.api.utils.constants.Constants;
import org.apache.commons.lang.StringUtils;

import java.util.Random;

/**
 * Created by Jack on 2016/12/9.
 */
public class MessageUtil {
    private final String VERIFY_CODES = "0123456789";
    private final int EXPIRE_TIME_SECONDS=60 * 10;//10分钟有效期
    private final int MOBILE_MAX_SEND_NUMBER = 10;//单个手机号
    private final int   IP_MAX_SEND_NUMBER=20;
    /**
     * 发送短信验证码
     * @param mobile 目标手机号码
     * @return 短信验证码
     */
    public String sendMessageVerifyCode(String mobile,String ip){
        //校验发送次数
        //mobile redis存储格式: key:{mobile},value:{verifyCode}|{number}
        String redisValue = getRedis(mobile);//获取已发送的次数
        int sendNumber = redisValue == null ? 0 : Integer.parseInt(redisValue.split("|")[1]); //该手机号已发送的短信次数


        String verifyCode = generateVerifyCode(4);
        SmsSendUtil.sendVerifyCode(mobile,verifyCode);
        setRedis(mobile,verifyCode);
        return verifyCode;
    }
    /**
     * 获取短信验证码
     * @param mobile 目标手机号码
     * @return 短信验证码
     */
    public String getRedis(String mobile){
        String key = getRedisKey(mobile);
        ShardedRedisUtil redisUtil = ShardedRedisUtil.getInstance();
        return redisUtil.get(key);
    }

    /**
     * 校验短信验证码
     * @param mobile 目标手机号码
     * @param code 用户输入验证码
     * @return 是否正确
     */
    public boolean isValidCode(String mobile,String code){
        String messageVerifyCode = getRedis(mobile);
        return !StringUtils.isBlank(messageVerifyCode) && messageVerifyCode.equalsIgnoreCase(code);
    }

    public String generateVerifyCode(int length){
        return generateVerifyCode(length,VERIFY_CODES);
    }
    public String generateVerifyCode(int length, String sources){
        if(StringUtils.isBlank(sources)){
            sources = VERIFY_CODES;
        }
        int codesLen = sources.length();
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder(length);
        for(int i = 0; i < length; i++){
            verifyCode.append(sources.charAt(rand.nextInt(codesLen-1)));
        }
        return verifyCode.toString();
    }

    private void setRedis(String mobile,String code)
    {
        String key=getRedisKey(mobile);
        String value=code;
        ShardedRedisUtil redisUtil = ShardedRedisUtil.getInstance();
        String result=redisUtil.set(key, value,"XX","EX",EXPIRE_TIME_SECONDS);
        if(result==null)
        {
            redisUtil.set(key, value,"NX","EX",EXPIRE_TIME_SECONDS);
        }
    }

    private String getRedisKey(String simpleKey)
    {
        return Constants.CAPTCHA_TOKEN_CACHE_NAME+simpleKey;
    }

}
