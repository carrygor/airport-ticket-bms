package com.airport.ticket.bms.dao;

import com.airport.ticket.bms.entity.FlightMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightMessageDao {
    int deleteByPrimaryKey(Integer id);

    int insert(FlightMessage record);

    int insertSelective(FlightMessage record);

    FlightMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FlightMessage record);

    int updateByPrimaryKey(FlightMessage record);

    List<FlightMessage> fetchAllFlightMessage();

    List<FlightMessage> fetchPageFlightMessage(@Param("startNo") int startNo, @Param("pageSize") int pageSize);

    List<FlightMessage> searchFlightMessage(@Param("company")String company,@Param("origin")String origin,@Param("destination") String destination,@Param("date")String date);

    int searchTotalMessage(@Param("company")String company,@Param("origin")String origin,@Param("destination") String destination,@Param("date")String date);

    int totalMessage();
}