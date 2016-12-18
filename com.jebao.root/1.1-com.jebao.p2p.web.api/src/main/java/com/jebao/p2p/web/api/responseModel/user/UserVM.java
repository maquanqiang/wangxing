package com.jebao.p2p.web.api.responseModel.user;

import com.jebao.common.utils.regex.RegexUtil;
import com.jebao.jebaodb.entity.user.TbUserDetails;
import com.jebao.p2p.web.api.responseModel.ViewModel;
import org.apache.commons.lang.StringUtils;

/**
 * Created by Jack on 2016/12/16.
 */
public class UserVM extends ViewModel{

    public UserVM(TbUserDetails entity){
        this.mobile = entity.getUdPhone();
        this.nickName = entity.getUdNickName();
        if (entity.getUdBankParentBankName() != null){
            //正则提取，从字符串开头一直到 “银行”
            this.bankName = RegexUtil.getFirstMatch(entity.getUdBankParentBankName(),"^[\\u4e00-\\u9fa5]+银行");
        }
        String theBankCardNo = entity.getUdBankCardNo();
        if (theBankCardNo != null){
            this.bankCardNo = theBankCardNo.replaceAll("(?<=\\d{4})\\d+(?=\\d{4})"," **** **** "); //银行卡号 中间替换 *
        }
        this.hasFundAccount = !StringUtils.isBlank(entity.getUdThirdAccount());
    }

    /**
     * 手机号
     */
    private String mobile;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 开户银行
     */
    private String bankName;
    private boolean hasFundAccount;
    /**
     * 银行卡号
     */
    private String bankCardNo;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public boolean getHasFundAccount() {
        return hasFundAccount;
    }

    public void setHasFundAccount(boolean hasFundAccount) {
        this.hasFundAccount = hasFundAccount;
    }
}
