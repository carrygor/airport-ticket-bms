package com.airport.ticket.bms.controller;

import com.airport.ticket.bms.pointCut.AccessToken;
import com.airport.ticket.bms.service.AdminUserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Controller()
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @RequestMapping(value = "/login", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String login(@RequestBody String jsonStr){
        JSONObject object = JSONObject.fromObject(jsonStr);
        String username = object.getString("username");
        String password = object.getString("password");
        return adminUserService.login(username,password);
    }


    @AccessToken
    @RequestMapping(value = "/getU", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getUser(@RequestBody String jsonStr){
        JSONObject object = JSONObject.fromObject(jsonStr);
        String username = object.getString("username");
        String password = object.getString("password");
        String token = "";
        try {
            token = adminUserService.getToken(username,password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        JSONObject result = new JSONObject();
        result.put("token",token);
        return result.toString();
    }
}
