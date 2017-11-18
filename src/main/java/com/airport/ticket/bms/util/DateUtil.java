package com.airport.ticket.bms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static Date formatDate(Date date,String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String str = sdf.format(date);
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
