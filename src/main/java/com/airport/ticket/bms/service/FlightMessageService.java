package com.airport.ticket.bms.service;

import com.airport.ticket.bms.entity.FlightMessage;
import com.airport.ticket.bms.form.message.BaseMessageForm;
import net.sf.json.JSONArray;

import java.util.List;
import java.util.Map;

public interface FlightMessageService {

    Map<String,Object> fetchPageMessage(int pageNo, int pageSize) throws Exception;

    Map<String,Object> searchFlightMessage(String company,String origin,String destination,String date) throws Exception;

    boolean addFlightMessage(BaseMessageForm array) throws Exception;

    boolean updateFlightMessage(FlightMessage message) throws Exception;

    boolean updateFlightMessage(BaseMessageForm form) throws Exception;

    FlightMessage fetchFlightMessageByKey(int key) throws Exception;
}
