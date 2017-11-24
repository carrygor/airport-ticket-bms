package com.airport.ticket.bms.service;

import com.airport.ticket.bms.entity.CustomerList;
import com.airport.ticket.bms.form.Customer.CustomerForm;
import net.sf.json.JSONArray;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface CustomerListService {

    List<CustomerList> fetchCustomerByFullName(String name);

    Map<String,Object> fetchCustomerByName(String name);

    Map<String,Object> fetchPageCustomer(int pageNo, int pageSize) throws Exception;

    boolean addCustomerMessage(JSONArray array) throws Exception;

    boolean updateCustomer(CustomerList customerList) throws Exception;

    boolean updateCustomer(CustomerForm form) throws Exception;

    CustomerList fetchFlightCustomerByKey(int key) throws Exception;

    Map<String,Object> searchCustomer(String name, String email, String idCard);

    boolean removeCustomer(int key);

//    byte[] exportFromExcel() throws Exception;
}
