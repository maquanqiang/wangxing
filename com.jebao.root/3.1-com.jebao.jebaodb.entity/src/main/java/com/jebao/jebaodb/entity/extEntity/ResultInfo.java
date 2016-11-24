package com.jebao.jebaodb.entity.extEntity;

/**
 * Created by Jack on 2016/11/23.
 */
public class ResultInfo {
    public ResultInfo(boolean success){
        this.setSuccess_is_ok(success);
    }
    public ResultInfo(boolean success,String msg){
        this.setSuccess_is_ok(success);
        this.setMsg(msg);
    }
    private boolean success_is_ok;
    private String msg;

    public boolean isSuccess_is_ok() {
        return success_is_ok;
    }

    public void setSuccess_is_ok(boolean success_is_ok) {
        this.success_is_ok = success_is_ok;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
