package com.airport.ticket.bms.controller;

import com.airport.ticket.bms.ExceptionGMS.SystemException;
import com.airport.ticket.bms.entity.FlightCompany;
import com.airport.ticket.bms.entity.FlightMessage;
import com.airport.ticket.bms.form.BasePageForm;
import com.airport.ticket.bms.form.company.CompanyForm;
import com.airport.ticket.bms.model.BaseResponse;
import com.airport.ticket.bms.pointCut.AccessToken;
import com.airport.ticket.bms.service.FlightCompanyService;
import com.airport.ticket.bms.service.FlightMessageService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping(value = "/company")
public class FlightCompanyController extends SuperController {

    @Autowired
    private FlightCompanyService flightCompanyService;

    @Autowired
    private FlightMessageService flightMessageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightCompanyController.class);

    @ExceptionHandler
    @Override
    public BaseResponse exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException {
        return super.exceptionRealHandler(request,response,exception);
    }

    /**
     * 获取每一页的航班信息
     * @return
     */
    @RequestMapping(value = "/pagecompany", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    @AccessToken
    @ResponseBody
    public BaseResponse fetchPageCompany(BasePageForm form) throws Exception {

        BaseResponse response = new BaseResponse();

        int pageNo = form.getPageNo();
        int pageSize = form.getPageSize();

        Map<String,Object> map = flightCompanyService.fetchPageCompany(pageNo,pageSize);
        response.setData(map);
        return response;
    }


    @RequestMapping(value = "/searchcompany",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    @AccessToken
    @ResponseBody
    public BaseResponse searchFlightCompany(@Param("company_name") String company) throws Exception {

        BaseResponse response = new BaseResponse();

        Map<String,Object> map = flightCompanyService.fetchCompanyByName(company);

        response.setData(map);
        return response;
    }

    @RequestMapping(value = "/addcompanies",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    @ResponseBody
    @AccessToken
    public BaseResponse addCompanies(@Param("data") String reqJson) throws Exception {
        BaseResponse response = new BaseResponse();

        JSONArray object = JSONArray.fromObject(reqJson);
        boolean isSuccess = flightCompanyService.addCompanyMessage(object);

        response.setErrCode(isSuccess?0:2);
        response.setErrMsg(isSuccess?"":"数据插进数据库失败！");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("isSuccess",isSuccess);
        response.setData(map);
        return response;
    }

    @RequestMapping(value = "/removecompany",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    @ResponseBody
    @AccessToken
    public BaseResponse removeCompany(@Param("id")int id) throws Exception {
        BaseResponse response = new BaseResponse();

        FlightCompany company = flightCompanyService.fetchFlightCompanyByKey(id);
        company.setStatus(false);

        List<FlightMessage> messages =
                (List<FlightMessage>) flightMessageService.searchFlightMessage
                        (company.getName(),"%","%","%")
                        .get("list");

        for (FlightMessage message:messages){
            if (compareDate(message.getFlightTime(),new Date()) < 0){
                SystemException se = new SystemException();
                se.setErrMsg("不能删除该数据");
                se.setErrCode(4);
                throw se;
            }
            message.setStatus(false);
        }

        for (FlightMessage message:messages){
            flightMessageService.updateFlightMessage(message);
        }

        boolean isSuccess= flightCompanyService.updateFlightCompany(company);
        response.setErrCode(isSuccess?0:2);
        response.setErrMsg(isSuccess?"":"数据更新到数据库失败！");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("isSuccess",isSuccess);
        response.setData(map);
        return response;
    }

    @RequestMapping(value = "/updatecompany",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    @ResponseBody
    @AccessToken
    public BaseResponse updateCompany(CompanyForm form) throws Exception {
        BaseResponse response = new BaseResponse();

        boolean isSuccess= flightCompanyService.updateFlightCompany(form);
        response.setErrCode(isSuccess?0:2);
        response.setErrMsg(isSuccess?"":"数据更新到数据库失败！");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("isSuccess",isSuccess);
        response.setData(map);
        return response;
    }

}
