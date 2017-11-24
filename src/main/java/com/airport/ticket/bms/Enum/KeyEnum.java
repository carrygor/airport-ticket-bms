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
    FLIGHT_TIME("flight_time"),
    RESIDUAL_SEATS("residual_seats"),

    NAME("name"),
    COMPANY_PHONE("phone"),
    COMPANY_EMAIL("email"),
    COMPANY_ADDRESS("address"),

    USER_REALNAME("real_name"),
    USER_PASSWORD("password"),
    USER_PHONE("phone"),
    USER_EMAIL("email"),

    ID_CARD("id_card"),
    HOUSEHOLD_RESGFITER("household_register"),
    STATUS("status"),

    ;

    private String value;
    KeyEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
