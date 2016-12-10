package com.jebao.jebaodb.entity.extEntity;

/**
 * Created by Jack on 2016/12/9.
 */
public class EnumModel {
    /**
     * 注册平台
     */
    public enum Platform{
        pc(1),
        android(2),
        ios(3),
        weixin(4),
        mobile(5),//非android APP、ios APP、weixin 的其他移动平台,例如手机网页
        other(6);

        private int value;
        private Platform(int val){
            this.value=val;
        }
        public int getValue(){
            return this.value;
        }
    }


}
