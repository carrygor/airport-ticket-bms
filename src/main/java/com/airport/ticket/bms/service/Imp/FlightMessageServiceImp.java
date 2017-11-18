package com.airport.ticket.bms.service.Imp;

import com.airport.ticket.bms.dao.FlightMessageDao;
import com.airport.ticket.bms.entity.FlightMessage;
import com.airport.ticket.bms.service.FlightMessageService;
import com.airport.ticket.bms.util.JsonDateValueProcessor;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FlightMessageServiceImp implements FlightMessageService{

    @Autowired
    private FlightMessageDao flightMessageDao;

    public String fetchPageMessage(int pageNo, int pageSize) {

        List<FlightMessage> messages = null;

        int totalPage = flightMessageDao.totalMessage();
        messages = flightMessageDao.fetchPageFlightMessage((pageNo - 1) * pageSize,pageSize);


        JSONObject object = listToJSON(messages);
        object.put("totalPage",totalPage);
        return object.toString();
    }

    public String searchFlightMessage(String company, String origin, String destination, String date) {

        List<FlightMessage> messages = flightMessageDao.searchFlightMessage(company,origin,destination,date);
        JSONObject object = listToJSON(messages);
        int totalPage = flightMessageDao.searchTotalMessage(company,origin,destination,date);
        object.put("totalPage",totalPage);

        return object.toString();

    }

    private JSONObject listToJSON(List<FlightMessage> messages){
        Map<String,List<FlightMessage>> map = new HashMap<String, List<FlightMessage>>();

        map.put("data",messages);

        JsonConfig config = new JsonConfig();
        JsonValueProcessor jsonValueProcessor = new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss");
        config.registerJsonValueProcessor(Date.class, jsonValueProcessor);

        JSONObject object = new JSONObject();
        object.putAll(map,config);

        return object;
    }
}
