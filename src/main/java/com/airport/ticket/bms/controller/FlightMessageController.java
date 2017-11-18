package com.airport.ticket.bms.controller;


import com.airport.ticket.bms.dao.FlightMessageDao;
import com.airport.ticket.bms.entity.FlightMessage;
import com.airport.ticket.bms.util.JsonDateValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Authod fym
 * 关于航班信息的控制器
 */

@Controller
public class FlightMessageController {

    @Autowired
    private FlightMessageDao flightMessageDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightMessageController.class);

    /**
     * 获取所有的航班信息
     * @return
     */
    @RequestMapping(value = "/pageMessage", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String fetchAllMessage(@RequestBody String reqJson){

        List<FlightMessage> messages = null;

        boolean isLegal = false;
        int pageNo = 0,pageSize = 0;

        if ("".equals(reqJson)){
            isLegal = false;
        } else {
            JSONObject json = JSONObject.fromObject(reqJson);
            String pageNoStr = json.getString("pageNo");
            String pageSizeStr = json.getString("pageSize");
            if (pageNoStr == null || "".equals(pageNoStr) || pageSizeStr == null || "".equals(pageSizeStr)){
                isLegal = false;
            } else {
                pageNo = Integer.parseInt(pageNoStr);
                pageSize = Integer.parseInt(pageSizeStr);
                isLegal = true;
            }
        }

        if (!isLegal){
            JSONObject result = new JSONObject();
            result.put("resultCode",-1);
            result.put("msg","参数错误！");
            return result.toString();
        }

        int totalPage = flightMessageDao.totalMessage();
        messages = flightMessageDao.fetchPageFlightMessage((pageNo - 1) * pageSize,pageSize);

        Map<String,List<FlightMessage>> map = new HashMap<String, List<FlightMessage>>();

        map.put("data",messages);

        JsonConfig config = new JsonConfig();
        JsonValueProcessor jsonValueProcessor = new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss");
        config.registerJsonValueProcessor(Date.class, jsonValueProcessor);

        JSONObject object = new JSONObject();
        object.put("totalPage",totalPage);
        object.putAll(map,config);

        return object.toString();
    }

}
