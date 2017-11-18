package com.airport.ticket.bms.service;

import org.springframework.stereotype.Service;

public interface FlightMessageService {

    String fetchPageMessage(int pageNo,int pageSize);

    String searchFlightMessage(String company,String origin,String destination,String date);
}
