package com.airport.ticket.bms.service;

import com.airport.ticket.bms.entity.FlightCompany;
import com.airport.ticket.bms.form.Customer.FlightCustomerForm;
import com.airport.ticket.bms.form.company.CompanyForm;
import net.sf.json.JSONArray;

import java.util.List;
import java.util.Map;

public interface FlightCompanyService {

    List<FlightCompany> fetchCompanyByFullName(String name);

    Map<String,Object> fetchCompanyByName(String name);

    Map<String,Object> fetchPageCompany(int pageNo, int pageSize) throws Exception;

    boolean addCompanyMessage(CompanyForm array) throws Exception;

    boolean updateFlightCompany(FlightCompany company) throws Exception;

    boolean updateFlightCompany(CompanyForm form) throws Exception;

    FlightCompany fetchFlightCompanyByKey(int key) throws Exception;
}
