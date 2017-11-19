package com.airport.ticket.bms.ExceptionGMS;

import java.io.Serializable;

public abstract class BaseException extends Exception implements Serializable{
    private int errCode ;
    private String errMsg;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

}
