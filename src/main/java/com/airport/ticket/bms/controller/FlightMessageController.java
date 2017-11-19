package com.airport.ticket.bms.controller;

import com.airport.ticket.bms.Enum.KeyEnum;
import com.airport.ticket.bms.form.BaseForm;
import com.airport.ticket.bms.form.BasePageForm;
import com.airport.ticket.bms.form.BaseSearchForm;
import com.airport.ticket.bms.model.BaseResponse;
import com.airport.ticket.bms.pointCut.AccessToken;
import com.airport.ticket.bms.service.FlightMessageService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Authod fym
 * 关于航班信息的控制器
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class FlightMessageController extends SuperController{

    @Autowired
    private FlightMessageService flightMessageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightMessageController.class);

    @ExceptionHandler
    @Override
    public BaseResponse exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException {
        return super.exceptionRealHandler(request,response,exception);
    }
    /**
     * 获取每一页的航班信息
     * @return
     */
    @RequestMapping(value = "/pageMessage", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    @AccessToken
    public BaseResponse fetchPageMessage(BasePageForm form){

        BaseResponse response = new BaseResponse();

        int pageNo = form.getPageNo();
        int pageSize = form.getPageSize();

        Map<String,Object> map = null;
        try {
            map = flightMessageService.fetchPageMessage(pageNo,pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setData(map);
        return response;
    }


    @RequestMapping(value = "/searchMessage",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    @AccessToken
    public BaseResponse searchFlightMessage(BaseSearchForm form){

        BaseResponse response = new BaseResponse();

        String company = !"".equals(form.getCompany())? "%"+form.getCompany()+ "%": "%" ;
        String origin = !"".equals(form.getOrigin()) ? "%"+form.getOrigin()+"%": "%";
        String destination = !"".equals(form.getDestination())? "%"+ form.getDestination() + "%":"%";
        String date = !"".equals(form.getDate())? form.getDate()+"%":"%";

        Map<String,Object> map = null;
        try{
            map = flightMessageService.searchFlightMessage(company,origin,destination,date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setData(map);
        return response;
    }

    @RequestMapping(value = "/addMessages",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    public BaseResponse addFlightMessages(@RequestBody String reqJson){
        JSONObject object = JSONObject.fromObject(reqJson);
        String messagesStr = object.getString(KeyEnum.INSERT_MESSAGES.getValue());





        return null;
    }

}
