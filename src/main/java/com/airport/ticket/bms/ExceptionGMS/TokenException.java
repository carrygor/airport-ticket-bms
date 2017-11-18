package com.airport.ticket.bms.ExceptionGMS;

import java.io.Serializable;

public class TokenException extends RuntimeException implements Serializable{

    private static final long serialVersionUID = 1L;

    private int resultCode;
    private String errorMsg;

    public TokenException(){
        super();
    }

    public TokenException(String errorMsg){
        super(errorMsg);
        this.setErrorMsg(errorMsg);
    }


    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
