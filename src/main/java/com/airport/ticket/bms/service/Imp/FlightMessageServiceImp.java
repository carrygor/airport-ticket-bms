package com.airport.ticket.bms.service.Imp;

import com.airport.ticket.bms.Enum.KeyEnum;
import com.airport.ticket.bms.ExceptionGMS.SystemException;
import com.airport.ticket.bms.dao.FlightMessageDao;
import com.airport.ticket.bms.entity.FlightMessage;
import com.airport.ticket.bms.service.FlightMessageService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlightMessageServiceImp implements FlightMessageService{

    @Autowired
    private FlightMessageDao flightMessageDao;

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

        for (int i=0;i<array.size();i++){
            String messageStr = array.getString(i);
            object = JSONObject.fromObject(messageStr);
//            if (!object.containsKey(KeyEnum.COMPANY.getValue()) || !object.containsKey(KeyEnum.INSERT_MESSAGES.getValue()))
        }

        int isSuccess = -1;
//        for (FlightMessage message:messages){
//            isSuccess = flightMessageDao.insert(message);
//        }

        System.out.println();
        return isSuccess == 0 ? false :true;
    }


}
