package com.airport.ticket.bms.controller;

import com.airport.ticket.bms.entity.CustomerList;
import com.airport.ticket.bms.entity.FlightCustomer;
import com.airport.ticket.bms.form.BasePageForm;
import com.airport.ticket.bms.form.Customer.CustomerForm;
import com.airport.ticket.bms.form.company.CompanyForm;
import com.airport.ticket.bms.model.BaseResponse;
import com.airport.ticket.bms.pointCut.AccessToken;
import com.airport.ticket.bms.service.CustomerListService;
import com.airport.ticket.bms.service.FlightCustomerService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/customerlist")
public class CustomerListController extends SuperController {

    @Autowired
    private CustomerListService customerListService;

    @Autowired
    private FlightCustomerService flightCustomerService;

    @ExceptionHandler
    @Override
    public BaseResponse exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException {
        return super.exceptionRealHandler(request,response,exception);
    }

    /**
     * 获取每一页customerlist
     * @return
     */
    @RequestMapping(value = "/pagecustomerlist", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    @AccessToken
    @ResponseBody
    public BaseResponse fetchCustomerList(BasePageForm form) throws Exception {

        BaseResponse response = new BaseResponse();

        int pageNo = form.getPageNo();
        int pageSize = form.getPageSize();

        Map<String,Object> map = customerListService.fetchPageCustomer(pageNo,pageSize);
        response.setData(map);
        return response;
    }


    @RequestMapping(value = "/searchcustomerlist",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    @AccessToken
    @ResponseBody
    public BaseResponse searchCustomerList(CustomerForm form) throws Exception {

        BaseResponse response = new BaseResponse();

        String name = form.getName();
        String email = form.getEmail();
        String idCard = form.getIdCard();

        Map<String,Object> map = customerListService.searchCustomer(name,email,idCard);

        response.setData(map);
        return response;
    }

    @RequestMapping(value = "/addcustomers",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    @ResponseBody
    @AccessToken
    public BaseResponse addCustomerLists(CustomerForm reqJson) throws Exception {
        BaseResponse response = new BaseResponse();

        boolean isSuccess = customerListService.addCustomerMessage(reqJson);

        response.setErrCode(isSuccess?0:2);
        response.setErrMsg(isSuccess?"":"数据插进数据库失败！");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("isSuccess",isSuccess);
        response.setData(map);
        return response;
    }

    @RequestMapping(value = "/removecustomerlist",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    @ResponseBody
    @AccessToken
    @Transactional(rollbackFor=Exception.class)
    public BaseResponse removeCustomerList(@Param("id")int id) throws Exception {
        BaseResponse response = new BaseResponse();

        CustomerList message = customerListService.fetchFlightCustomerByKey(id);
        message.setStatus(false);

        List<FlightCustomer> customers = flightCustomerService.fetchFlightCustomerByFullName(message.getName());
        for (FlightCustomer customer:customers){
            customer.setStatus(false);
            flightCustomerService.updateFlightCustomer(customer);
        }


        boolean isSuccess= customerListService.updateCustomer(message);
        response.setErrCode(isSuccess?0:2);
        response.setErrMsg(isSuccess?"":"数据更新到数据库失败！");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("isSuccess",isSuccess);
        response.setData(map);
        return response;
    }


    @RequestMapping(value = "/updatecustomerlist",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    @ResponseBody
    @AccessToken
    public BaseResponse updateCustomerList(CustomerForm form) throws Exception {
        BaseResponse response = new BaseResponse();

        boolean isSuccess= customerListService.updateCustomer(form);
        response.setErrCode(isSuccess?0:2);
        response.setErrMsg(isSuccess?"":"数据更新到数据库失败！");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("isSuccess",isSuccess);
        response.setData(map);
        return response;
    }


    @RequestMapping(value = "file/download")
    @ResponseBody
    @AccessToken
    public BaseResponse fileDownload(){
        //获取网站部署路径(通过ServletContext对象)，用于确定下载文件位置，从而实现下载
        String path = "D:/test.xlsx";
        File file = new File(path);
        BaseResponse response = new BaseResponse();
        try {
            FileInputStream inputStream = new FileInputStream(file);

            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);

            response.setData(buffer);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
