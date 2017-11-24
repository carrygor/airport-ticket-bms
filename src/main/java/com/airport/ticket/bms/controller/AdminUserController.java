package com.airport.ticket.bms.controller;

import com.airport.ticket.bms.Enum.KeyEnum;
import com.airport.ticket.bms.entity.AdminUser;
import com.airport.ticket.bms.form.BasePageForm;
import com.airport.ticket.bms.form.adminUser.AdminUserForm;
import com.airport.ticket.bms.form.message.BaseLoginForm;
import com.airport.ticket.bms.model.BaseResponse;
import com.airport.ticket.bms.pointCut.AccessToken;
import com.airport.ticket.bms.service.AdminUserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping(value = "/user")
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
    public BaseResponse login(BaseLoginForm form) throws Exception{
        String username = form.getUsername();
        String password = form.getPassword();

        BaseResponse response = new BaseResponse();
        Map<String,Object> map = adminUserService.login(username,password);

        if (!map.containsKey("token")){
            response.setErrCode(1);
        }
        LOGGER.debug(map.toString());
        response.setData(map);
        return response;
    }

    @RequestMapping(value = "/searchadminuser",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    @AccessToken
    @ResponseBody
    public BaseResponse searchAdminUser(@Param("username") String name,@Param("word_id")String word_id,@Param("level")String level)
            throws Exception {

        BaseResponse response = new BaseResponse();

        name = "%"+name+"%";
        word_id = "%"+word_id+"%";
        level = "%"+level+"%";
        Map<String,Object> map = adminUserService.searchAdminUser(name,word_id,level);

        response.setData(map);
        return response;
    }


    @RequestMapping(value = "/addadminuser",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    @ResponseBody
    @AccessToken
    public BaseResponse addAminUser(@Param("data") String form) throws Exception{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        AdminUser user = (AdminUser) request.getAttribute(KeyEnum.USER.getValue());

        Object object;
        if (!((object = isAccess(user)) instanceof Boolean)){
            return (BaseResponse) object;
        }

        BaseResponse response = new BaseResponse();

        JSONArray json = JSONArray.fromObject(form);
        boolean isSuccess = adminUserService.addAdminUser(json);

        response.setErrCode(isSuccess?0:2);
        response.setErrMsg(isSuccess?"":"数据插进数据库失败！");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("isSuccess",isSuccess);
        response.setData(map);
        return response;
    }

    @RequestMapping(value = "/removeadminuser",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    @ResponseBody
    @AccessToken
    public BaseResponse removeFlightMessages(@Param("id")int id) throws Exception {
        BaseResponse response = new BaseResponse();

        AdminUser user = adminUserService.fetchAdminUserByKey(id);
        user.setStatus(false);

        boolean isSuccess= adminUserService.updateAdminUser(user);
        response.setErrCode(isSuccess?0:2);
        response.setErrMsg(isSuccess?"":"数据更新到数据库失败！");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("isSuccess",isSuccess);
        response.setData(map);
        return response;
    }

    @RequestMapping(value = "/updateadminuser",method = RequestMethod.POST,produces = "text/json;charset=utf-8")
    @ResponseBody
    @AccessToken
    public BaseResponse updateFlightMessages(AdminUserForm form) throws Exception {
        BaseResponse response = new BaseResponse();

        boolean isSuccess= adminUserService.updateAdminUser(form);
        response.setErrCode(isSuccess?0:2);
        response.setErrMsg(isSuccess?"":"数据更新到数据库失败！");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("isSuccess",isSuccess);
        response.setData(map);
        return response;
    }

    @RequestMapping(value = "/pageadminuser", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
    @AccessToken
    @ResponseBody
    public BaseResponse fetchCustomerList(BasePageForm form) throws Exception {

        BaseResponse response = new BaseResponse();

        int pageNo = form.getPageNo();
        int pageSize = form.getPageSize();

        Map<String,Object> map = adminUserService.fetchPageAdminUser(pageNo,pageSize);
        response.setData(map);
        return response;
    }



    private Object isAccess(AdminUser user){
        if (user.getLevel()){
            return true;
        } else {
            BaseResponse response = new BaseResponse();
            response.setErrCode(-2001);
            response.setErrMsg("权限不够!");
            return response;
        }
    }

}
