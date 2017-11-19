package com.airport.ticket.bms.service;

import com.airport.ticket.bms.entity.FlightMessage;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface FlightMessageService {

    Map<String,Object> fetchPageMessage(int pageNo, int pageSize) throws Exception;

    Map<String,Object> searchFlightMessage(String company,String origin,String destination,String date) throws Exception;

    boolean addFlightMessage(JSONArray array) throws Exception;
}
