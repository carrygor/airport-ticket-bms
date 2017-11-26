package com.airport.ticket.bms.controller;

import com.airport.ticket.bms.ExceptionGMS.SystemException;
import com.airport.ticket.bms.entity.FlightMessage;
import com.airport.ticket.bms.form.BasePageForm;
import com.airport.ticket.bms.form.message.BaseMessageForm;
import com.airport.ticket.bms.model.BaseResponse;
import com.airport.ticket.bms.pointCut.AccessToken;
import com.airport.ticket.bms.service.FlightMessageService;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Authod fym
 * 关于航班信息的控制器
 */

@Controller
public class FlightMessageController extends SuperController {

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
    @RequestMapping(value = "/flightmessage/pageMessage", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    @AccessToken
    @ResponseBody
    public BaseResponse fetchPageMessage(BasePageForm form) throws Exception {

        BaseResponse response = new BaseResponse();

        int pageNo = form.getPageNo();
        int pageSize = form.getPageSize();

        Map<String,Object> map = flightMessageService.fetchPageMessage(pageNo,pageSize);
        response.setData(map);
        return response;
    }


    @RequestMapping(value = "/flightmessage/searchMessage",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    @AccessToken
    @ResponseBody
    public BaseResponse searchFlightMessage(BaseMessageForm form) throws Exception {

        BaseResponse response = new BaseResponse();

        String company = !"".equals(form.getCompany())? "%"+form.getCompany()+ "%": "%" ;
        String origin = !"".equals(form.getOrigin()) ? "%"+form.getOrigin()+"%": "%";
        String destination = !"".equals(form.getDestination())? "%"+ form.getDestination() + "%":"%";
        String date = !"".equals(form.getFlightTime())? form.getFlightTime()+"%":"%";

        Map<String,Object> map = flightMessageService.searchFlightMessage(company,origin,destination,date);

        response.setData(map);
        return response;
    }

    @RequestMapping(value = "/flightmessage/addMessages",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    @ResponseBody
    @AccessToken
    public BaseResponse addFlightMessages(BaseMessageForm reqJson) throws Exception {
        BaseResponse response = new BaseResponse();

        boolean isSuccess = flightMessageService.addFlightMessage(reqJson);

        response.setErrCode(isSuccess?0:2);
        response.setErrMsg(isSuccess?"":"数据插进数据库失败！");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("isSuccess",isSuccess);
        response.setData(map);
        return response;
    }

    @RequestMapping(value = "/flightmessage/removeMessages",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    @ResponseBody
    @AccessToken
    public BaseResponse removeFlightMessages(@Param("id")int id) throws Exception {
        BaseResponse response = new BaseResponse();

        FlightMessage message = flightMessageService.fetchFlightMessageByKey(id);

        if (compareDate(message.getFlightTime(),new Date()) < 0){
            SystemException se = new SystemException();
            se.setErrCode(4);
            se.setErrMsg("不能删除该数据");
            throw se;
        }
        message.setStatus(false);

        boolean isSuccess= flightMessageService.updateFlightMessage(message);
        response.setErrCode(isSuccess?0:2);
        response.setErrMsg(isSuccess?"":"数据更新到数据库失败！");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("isSuccess",isSuccess);
        response.setData(map);
        return response;
    }


    @RequestMapping(value = "/flightmessage/updateMessages",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    @ResponseBody
    @AccessToken
    public BaseResponse updateFlightMessages(BaseMessageForm form) throws Exception {
        BaseResponse response = new BaseResponse();

        boolean isSuccess= flightMessageService.updateFlightMessage(form);
        response.setErrCode(isSuccess?0:2);
        response.setErrMsg(isSuccess?"":"数据更新到数据库失败！");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("isSuccess",isSuccess);
        response.setData(map);
        return response;
    }







    @RequestMapping(value = "/flightmessage/test",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    @ResponseBody
    public BaseResponse test(){
        JSONObject object = new JSONObject();
        object.put("key","key");
        BaseResponse response = new BaseResponse();
        response.setData(object);
        return response;
    }

}
