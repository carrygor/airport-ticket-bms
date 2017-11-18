package com.airport.ticket.bms.controller;

import com.airport.ticket.bms.pointCut.AccessToken;
import com.airport.ticket.bms.service.FlightMessageService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Authod fym
 * 关于航班信息的控制器
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class FlightMessageController {

    @Autowired
    private FlightMessageService flightMessageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightMessageController.class);

    /**
     * 获取每一页的航班信息
     * @return
     */
    @RequestMapping(value = "/pageMessage", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    @ResponseBody
    @AccessToken
    public String fetchPageMessage(@RequestBody String reqJson){

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

        return flightMessageService.fetchPageMessage(pageNo,pageSize);
    }


    @RequestMapping(value = "/searchMessage",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    @ResponseBody
//    @AccessToken
    public String searchFlightMessage(@RequestBody String reqJson){
        JSONObject object = JSONObject.fromObject(reqJson);

        String company = object.containsKey("company")? "%"+object.getString("company")+ "%": "%" ;
        String origin = object.containsKey("origin") ? "%"+object.getString("origin")+"%": "%";
        String destination = object.containsKey("destination")? "%"+ object.getString("destination") + "%":"%";
        String date = object.containsKey("date")? object.getString("date")+"%":"%";

        return flightMessageService.searchFlightMessage(company,origin,destination,date);
    }

}
