package com.airport.ticket.bms.model;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable{

    private int errCode = 0;

    private String errMsg = "";

    private T data;

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
