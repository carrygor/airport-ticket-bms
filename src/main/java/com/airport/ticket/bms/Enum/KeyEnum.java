package com.airport.ticket.bms.Enum;


/**
 * 存储key的枚举类
 */
public enum  KeyEnum {

    ERROR_CODE("errCode"),
    ERROR_MSG("errMsg"),

    USER("user"),
    INSERT_MESSAGES("messages"),
    COMPANY("company"),
    ORIGIN("origin"),
    DESTINATION("destination"),
    SEATS("seats"),
    FLIGHT_PRICE("price"),
    FLIGHT_TIME("flight_time");

    private String value;
    KeyEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
