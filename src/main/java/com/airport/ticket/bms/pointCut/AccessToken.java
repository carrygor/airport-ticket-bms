package com.airport.ticket.bms.pointCut;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//这个注解是应用在方法上
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessToken {
/*    String userId();
    String token();*/
}