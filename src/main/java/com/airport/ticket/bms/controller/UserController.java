package com.airport.ticket.bms.controller;


import com.airport.ticket.bms.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/getUser",method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getUser(){
        System.out.println(userService.selectByPrimaryKey(1));
        return "hello world";
    }
}
