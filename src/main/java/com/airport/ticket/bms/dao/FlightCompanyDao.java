package com.airport.ticket.bms.dao;

import com.airport.ticket.bms.entity.FlightCompany;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FlightCompanyDao {
    int deleteByPrimaryKey(Integer id);

    int insert(FlightCompany record);

    int insertSelective(FlightCompany record);

    FlightCompany selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FlightCompany record);

    int updateByPrimaryKey(FlightCompany record);

    List<FlightCompany> fetchFlightCompanyByName(String name);

    List<FlightCompany> fetchFlightCompanyByFullName(String name);

    int totalCompany();

    List<FlightCompany> fetchPageFlightCompany(@Param("startNo") int startNo, @Param("pageSize") int pageSize);

}