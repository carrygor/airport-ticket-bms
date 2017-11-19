package com.airport.ticket.bms.controller;

import com.airport.ticket.bms.form.BaseForm;
import com.airport.ticket.bms.model.BaseResponse;
import com.airport.ticket.bms.service.AdminUserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AdminUserController extends SuperController{

    @Autowired
    private AdminUserService adminUserService;

    @ExceptionHandler
    @Override
    public BaseResponse exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException {
        return super.exceptionRealHandler(request,response,exception);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResponse login(@RequestBody String jsonStr){
        BaseResponse response = new BaseResponse();
        JSONObject object = JSONObject.fromObject(jsonStr);
        String username = object.getString("username");
        String password = object.getString("password");

        response.setData(adminUserService.login(username,password));
        return response;
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String test(BaseForm form){
        BaseResponse response = new BaseResponse();

        response.setData("test success");


        return "test success";
    }

}
