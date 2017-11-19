package com.airport.ticket.bms.Enum;

import com.airport.ticket.bms.ExceptionGMS.SystemException;
import com.airport.ticket.bms.ExceptionGMS.TokenException;

public enum ExceptionEnum {

    SYSTEM_EXCEPTION(new SystemException()),
    TOKEN_EXCEPTION(new TokenException());


    private Exception exception;

    private ExceptionEnum(Exception e){
        this.exception = e;
    }
}
