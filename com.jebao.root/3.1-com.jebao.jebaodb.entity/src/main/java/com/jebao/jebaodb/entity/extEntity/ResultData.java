package com.jebao.jebaodb.entity.extEntity;

/**
 * Created by Jack on 2016/11/23.
 */
public class ResultData <T> extends ResultInfo{
    public ResultData(T data){
        super(true);
        this.setData(data);
    }
    public ResultData(T data,String msg){
        super(true,msg);
        this.setData(data);
    }
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
