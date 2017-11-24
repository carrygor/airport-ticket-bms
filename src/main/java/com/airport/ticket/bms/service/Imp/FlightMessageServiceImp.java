package com.airport.ticket.bms.service.Imp;

import com.airport.ticket.bms.Enum.KeyEnum;
import com.airport.ticket.bms.ExceptionGMS.SystemException;
import com.airport.ticket.bms.dao.FlightCompanyDao;
import com.airport.ticket.bms.dao.FlightMessageDao;
import com.airport.ticket.bms.entity.CustomerList;
import com.airport.ticket.bms.entity.FlightCompany;
import com.airport.ticket.bms.entity.FlightMessage;
import com.airport.ticket.bms.form.message.BaseMessageForm;
import com.airport.ticket.bms.service.FlightMessageService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
    public boolean addFlightMessage(JSONArray array) throws Exception {

        JSONObject object = null;
        List<FlightMessage> messages = new ArrayList<FlightMessage>();

        for (int i=0;i<array.size();i++){
            String messageStr = array.getString(i);
            object = JSONObject.fromObject(messageStr);

            if (!object.containsKey(KeyEnum.COMPANY.getValue()) || !object.containsKey(KeyEnum.ORIGIN.getValue()) ||
                    !object.containsKey(KeyEnum.DESTINATION.getValue()) || !object.containsKey(KeyEnum.SEATS.getValue()) ||
                            !object.containsKey(KeyEnum.FLIGHT_PRICE) || object.containsKey(KeyEnum.FLIGHT_TIME.getValue())){
                SystemException se = new SystemException();
                se.setErrCode(3);
                se.setErrMsg("参数错误！");
                throw  se;
            }

            //加上对是否有该航空公司的判断
//            to do
            List<FlightCompany> companies = flightCompanyDao.fetchFlightCompanyByFullName(object.getString(KeyEnum.COMPANY.getValue()));
            if (companies.size() <= 0){
                SystemException se = new SystemException();
                se.setErrCode(3);
                se.setErrMsg("不存在该公司！");
                throw  se;
            }

            FlightMessage message = new FlightMessage();
            message.setSeats(object.getInt(String.valueOf(object.getInt(KeyEnum.SEATS.getValue()))));
            message.setFlightPrice(object.getLong(KeyEnum.FLIGHT_PRICE.getValue()));
            message.setOrigin(object.getString(KeyEnum.ORIGIN.getValue()));
            message.setDestination(object.getString(KeyEnum.DESTINATION.getValue()));
            message.setFlightTime((Date) object.get(KeyEnum.FLIGHT_TIME.getValue()));
            message.setCompany(object.getString(KeyEnum.COMPANY.getValue()));
            message.setResidualSeats(object.containsKey(KeyEnum.RESIDUAL_SEATS.getValue())?
                    object.getInt(KeyEnum.RESIDUAL_SEATS.getValue()):message.getSeats());

            messages.add(message);
        }

        int isSuccess = 1;
        for (FlightMessage message:messages){
            isSuccess = flightMessageDao.insert(message);
        }
        return isSuccess == 1 ? true :false;
    }

    public boolean updateFlightMessage(FlightMessage message) throws Exception{
        return flightMessageDao.updateByPrimaryKeySelective(message) == 1 ? true:false;
    }

    public boolean updateFlightMessage(BaseMessageForm form) throws Exception{
        FlightMessage message = new FlightMessage();
        message.setCompany(form.getCompany());
        message.setFlightTime(form.getDate());
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
