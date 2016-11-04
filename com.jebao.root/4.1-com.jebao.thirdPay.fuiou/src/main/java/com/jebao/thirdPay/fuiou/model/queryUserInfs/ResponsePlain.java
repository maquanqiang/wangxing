package com.jebao.thirdPay.fuiou.model.queryUserInfs;

import com.jebao.thirdPay.fuiou.model.base.BasePlain;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/27.
 */
public class ResponsePlain extends BasePlain {
    private String mchnt_nm="";
    @XmlElementWrapper(name="results")
    @XmlElement(name="result")
    List<ResponsePlainResult> results=new ArrayList<ResponsePlainResult>();

    public String getMchnt_nm() {
        return mchnt_nm;
    }

    public void setMchnt_nm(String mchnt_nm) {
        this.mchnt_nm = mchnt_nm;
    }
}
