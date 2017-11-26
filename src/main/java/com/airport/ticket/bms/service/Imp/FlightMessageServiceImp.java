package com.airport.ticket.bms.service.Imp;

import com.airport.ticket.bms.ExceptionGMS.SystemException;
import com.airport.ticket.bms.dao.FlightCompanyDao;
import com.airport.ticket.bms.dao.FlightMessageDao;
import com.airport.ticket.bms.entity.FlightCompany;
import com.airport.ticket.bms.entity.FlightMessage;
import com.airport.ticket.bms.form.message.BaseMessageForm;
import com.airport.ticket.bms.service.FlightMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class FlightMessageServiceImp implements FlightMessageService{

    @Autowired
    private FlightMessageDao flightMessageDao;

    @Autowired
    private FlightCompanyDao flightCompanyDao;

    public Map<String,Object> fetchPageMessage(int pageNo, int pageSize) throws Exception {

        List<FlightMessage> messages = null;

        int totalPage = 0;
        try {
            totalPage = flightMessageDao.totalMessage();
            messages = flightMessageDao.fetchPageFlightMessage((pageNo - 1) * pageSize,pageSize);
        } catch (Exception e){
            SystemException systemException = new SystemException();
            systemException.setErrCode(2);
            systemException.setErrMsg("数据库查询错误！");
            throw systemException;
        }

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("total",totalPage);
        map.put("list",messages);
        return map;
    }

    public Map<String,Object> searchFlightMessage(String company, String origin, String destination, String date) throws Exception{

        List<FlightMessage> messages = null;
        int totalPage = 0;

        try {
            messages = flightMessageDao.searchFlightMessage(company,origin,destination,date);
            totalPage = flightMessageDao.searchTotalMessage(company,origin,destination,date);
        } catch (Exception e){
            SystemException systemException = new SystemException();
            systemException.setErrCode(2);
            systemException.setErrMsg("数据库查询错误！");
            throw systemException;
        }

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("total",totalPage);
        map.put("list",messages);
        return map;
    }


    @Transactional(rollbackFor=Exception.class)
    public boolean addFlightMessage(BaseMessageForm array) throws Exception {

        //加上对是否有该航空公司的判断
//            to do
        List<FlightCompany> companies = flightCompanyDao.fetchFlightCompanyByFullName(array.getCompany());
        if (companies.size() <= 0){
            SystemException se = new SystemException();
            se.setErrCode(3);
            se.setErrMsg("不存在该公司！");
            throw  se;
        }

        FlightMessage message = new FlightMessage();
        message.setSeats(array.getSeats());
        message.setFlightPrice(array.getPrice());
        message.setOrigin(array.getOrigin());
        message.setDestination(array.getDestination());
        message.setFlightTime(array.getFlightTime());
        message.setCompany(array.getCompany());
        message.setResidualSeats(array.getResidualSeats() == 0?array.getSeats():array.getResidualSeats());
        message.setStatus(array.isStatus());


        int isSuccess =  flightMessageDao.insert(message);
        return isSuccess == 1 ? true :false;
    }

    public boolean updateFlightMessage(FlightMessage message) throws Exception{
        return flightMessageDao.updateByPrimaryKeySelective(message) == 1 ? true:false;
    }

    public boolean updateFlightMessage(BaseMessageForm form) throws Exception{
        FlightMessage message = new FlightMessage();
        message.setCompany(form.getCompany());
        message.setFlightTime(form.getFlightTime());
        message.setDestination(form.getDestination());
        message.setOrigin(form.getOrigin());
        message.setFlightPrice(form.getPrice());
        message.setSeats(form.getSeats());
        message.setResidualSeats(form.getResidualSeats() == 0?form.getSeats():form.getResidualSeats());
        message.setId(form.getId());
        message.setStatus(form.isStatus());
        message.setUpdateTime(new Date());

        return flightMessageDao.updateByPrimaryKeySelective(message) == 1 ? true:false;

    }

    public FlightMessage fetchFlightMessageByKey(int key) throws Exception{
        return flightMessageDao.selectByPrimaryKey(key);
    }


}
