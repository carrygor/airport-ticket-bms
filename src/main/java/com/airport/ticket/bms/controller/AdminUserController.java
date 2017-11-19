package com.airport.ticket.bms.controller;

import com.airport.ticket.bms.form.BaseLoginForm;
import com.airport.ticket.bms.form.BaseForm;
import com.airport.ticket.bms.model.BaseResponse;
import com.airport.ticket.bms.service.AdminUserService;
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

@RestController
public class AdminUserController extends SuperController{

    @Autowired
    private AdminUserService adminUserService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserController.class);

    @ExceptionHandler
    @Override
    public BaseResponse exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException {
        return super.exceptionRealHandler(request,response,exception);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    @ResponseBody
    public BaseResponse login(BaseLoginForm form){
        String username = form.getUsername();
        String password = form.getPassword();

//    public BaseResponse login(@RequestBody String form){
//        JSONObject json = JSONObject.fromObject(form);
//        String username = json.getString("username");
//        String password = json.getString("password");

        BaseResponse response = new BaseResponse();
            Map<String,Object> map = null;
            try {
                map = adminUserService.login(username,password);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!map.containsKey("token")){
                response.setErrCode(1);
            }
            LOGGER.debug(map.toString());
            response.setData(map);
            return response;
        }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String test(BaseForm form){
        BaseResponse response = new BaseResponse();

        response.setData("test success");


        return "test success";
    }

}
