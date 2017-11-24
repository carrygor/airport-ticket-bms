package com.airport.ticket.bms.dao;

import com.airport.ticket.bms.entity.FlightCustomer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightCustomerDao {
    int deleteByPrimaryKey(Integer id);

    int insert(FlightCustomer record);

    int insertSelective(FlightCustomer record);

    FlightCustomer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FlightCustomer record);

    int updateByPrimaryKey(FlightCustomer record);

    List<FlightCustomer> fetchAllFlightCustomers();

    List<FlightCustomer> fetchFlightCustomerByFullName(String name);

    List<FlightCustomer> fetchFlightCustomerByName(String name);

    List<FlightCustomer> fetchPageFlightCustomers(@Param("startNo") int startNo, @Param("pageSize") int pageSize);

    int totalCustomer();

    List<FlightCustomer> searchFlightCustomers(@Param("date") String date, @Param("company") String company,
                                               @Param("origin") String origin, @Param("destination") String destination);
}