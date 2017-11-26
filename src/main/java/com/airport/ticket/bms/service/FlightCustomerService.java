package com.airport.ticket.bms.service;

import com.airport.ticket.bms.entity.FlightCustomer;
import com.airport.ticket.bms.form.Customer.FlightCustomerForm;
import net.sf.json.JSONArray;

import java.util.List;
import java.util.Map;

public interface FlightCustomerService {

    List<FlightCustomer> fetchFlightCustomerByFullName(String name);

    Map<String,Object> fetchFlightCustomerByName(String name);

    Map<String,Object> fetchPageFlightCustomer(int pageNo, int pageSize) throws Exception;

    boolean addFlightCustomer(FlightCustomerForm array) throws Exception;

    boolean updateFlightCustomer(FlightCustomer customer) throws Exception;

    boolean updateFlightCustomer(FlightCustomerForm form) throws Exception;

    FlightCustomer fetchFlightCustomerByKey(int key) throws Exception;

    Map<String,Object> searchFlightCustomer(String company, String origin, String destination, String date);

}
