package com.airport.ticket.bms.controller;

import com.airport.ticket.bms.entity.FlightCustomer;
import com.airport.ticket.bms.form.BasePageForm;
import com.airport.ticket.bms.form.Customer.FlightCustomerForm;
import com.airport.ticket.bms.model.BaseResponse;
import com.airport.ticket.bms.pointCut.AccessToken;
import com.airport.ticket.bms.service.FlightCustomerService;
import net.sf.json.JSONArray;
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
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/flightcustomer")
public class FlightCustomerController extends SuperController {

    @Autowired
    private FlightCustomerService flightCustomerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightCustomerController.class);

    @ExceptionHandler(value = Exception.class)
    @Override
    public BaseResponse exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException {
        return super.exceptionRealHandler(request,response,exception);
    }

    /**
     * 获取每一页的航班信息
     * @return
     */
    @RequestMapping(value = "/pageflightcustomer", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    @AccessToken
    @ResponseBody
    public BaseResponse fetchPageFlightCustomer(BasePageForm form) throws Exception {

        BaseResponse response = new BaseResponse();

        int pageNo = form.getPageNo();
        int pageSize = form.getPageSize();

        Map<String,Object> map = flightCustomerService.fetchPageFlightCustomer(pageNo,pageSize);
        response.setData(map);
        return response;
    }


    @RequestMapping(value = "/searchflightcustomer",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    @AccessToken
    @ResponseBody
    public BaseResponse searchFlightCustomer(FlightCustomerForm form) throws Exception {

        BaseResponse response = new BaseResponse();

        String company = !"".equals(form.getCompany())? "%"+form.getCompany()+ "%": "%" ;
        String origin = !"".equals(form.getOrigin()) ? "%"+form.getOrigin()+"%": "%";
        String destination = !"".equals(form.getDestination())? "%"+ form.getDestination() + "%":"%";
        String date = !"".equals(form.getFlightTime())? form.getFlightTime()+"%":"%";

        Map<String,Object> map = flightCustomerService.searchFlightCustomer(company,origin,destination,date);

        response.setData(map);
        return response;
    }

    @RequestMapping(value = "/addflightcustomers",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    @ResponseBody
    @AccessToken
    public BaseResponse addFlightCustomers(FlightCustomerForm reqJson) throws Exception {
        BaseResponse response = new BaseResponse();

        boolean isSuccess = flightCustomerService.addFlightCustomer(reqJson);

        response.setErrCode(isSuccess?0:2);
        response.setErrMsg(isSuccess?"":"数据插进数据库失败！");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("isSuccess",isSuccess);
        response.setData(map);
        return response;
    }

    @RequestMapping(value = "/removeflightcustomer",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    @ResponseBody
    @AccessToken
    public BaseResponse removeFlightCustomer(@Param("id")int id) throws Exception {
        BaseResponse response = new BaseResponse();

        FlightCustomer customer = flightCustomerService.fetchFlightCustomerByKey(id);
        customer.setStatus(false);

        boolean isSuccess= flightCustomerService.updateFlightCustomer(customer);
        response.setErrCode(isSuccess?0:2);
        response.setErrMsg(isSuccess?"":"数据更新到数据库失败！");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("isSuccess",isSuccess);
        response.setData(map);
        return response;
    }


    @RequestMapping(value = "/updateflightcustomer",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    @ResponseBody
    @AccessToken
    public BaseResponse updateFlightMessages(FlightCustomerForm form) throws Exception {
        BaseResponse response = new BaseResponse();

        boolean isSuccess= flightCustomerService.updateFlightCustomer(form);
        response.setErrCode(isSuccess?0:2);
        response.setErrMsg(isSuccess?"":"数据更新到数据库失败！");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("isSuccess",isSuccess);
        response.setData(map);
        return response;
    }
}
