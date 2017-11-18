package com.airport.ticket.bms.controller;


import com.airport.ticket.bms.entity.AdminUser;
import com.airport.ticket.bms.model.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public abstract class SuperController {


    private static final Logger LOGGER = LoggerFactory.getLogger(SuperController.class);

    /**
     * 统一异常处理接口
     * @param request
     * @param response
     * @param exception
     */
    public abstract BaseResponse exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException;

    /**
     * 统一异常处理
     * @param request
     * @param response
     * @param exception
     */
    @ExceptionHandler(value = Throwable.class)
    public BaseResponse exceptionRealHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException {
        BaseResponse baseResponse = new BaseResponse();
        //exception

        LOGGER.error("统一异常处理：", exception);
        response.setHeader("errmsg", baseResponse.getErrMsg());
        response.setIntHeader("errcode", baseResponse.getErrCode());
        return baseResponse;
    }



    protected AdminUser getAdminUser(HttpServletRequest request) {
        return (AdminUser) request.getAttribute("user");
    }

}
