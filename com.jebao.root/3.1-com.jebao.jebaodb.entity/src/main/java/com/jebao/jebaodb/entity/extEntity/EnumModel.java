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

    public enum BankCardChangeStatus{
        正常(0),更换审核中(1);
        private int value;
        private BankCardChangeStatus(int val){
            this.value=val;
        }
        public int getValue(){
            return this.value;
        }
    }

    /**
     * POS机签约状态
     */
    public enum PosStatus{
        未签约(0),已签约(1);
        private int value;
        private PosStatus(int val){
            this.value = val;
        }
        public int getValue(){
            return this.value;
        }
    }

    /**
     * 资金收支状态
     */
    public enum FdBalanceStatus{
        收入(1),支出(2);
        private int value;
        private FdBalanceStatus(int val){
            this.value = val;
        }
        public int getValue(){
            return this.value;
        }
    }

    /**
     * 资金交易状态
     */
    public enum FdSerialStatus{
        失败(-1),处理中(0),成功(1);
        private int value;
        private FdSerialStatus(int val){
            this.value = val;
        }
        public int getValue(){
            return this.value;
        }
    }

    /**
     * 是否有效状态
     */
    public enum IsDel{
        有效(1),无效(2);
        private int value;
        private IsDel(int val){
            this.value = val;
        }
        public int getValue(){
            return this.value;
        }
    }

    /**
     * 交易类型
     */
    public enum SerialType{
        充值(1),
        提现(2),
        投资(3),
        借款(4);
        private int value;
        private SerialType(int val){
            this.value = val;
        }
        public int getValue(){
            return this.value;
        }
    }

    /**
     * 资金类型
     */
    public enum FundType{
        本金(1),利息(2);
        private int value;
        private FundType(int val){
            this.value = val;
        }
        public int getValue(){
            return this.value;
        }
    }

    /**
     * 收款状态(还款状态)
     */
    public enum IncomeStatus{
        未还(1),已还(2);
        private int value;
        private IncomeStatus(int val){
            this.value = val;
        }
        public int getValue(){
            return this.value;
        }
    }
}
