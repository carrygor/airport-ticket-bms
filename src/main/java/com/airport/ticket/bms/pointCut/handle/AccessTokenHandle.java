package com.airport.ticket.bms.pointCut.handle;


import com.airport.ticket.bms.ExceptionGMS.TokenException;
import com.airport.ticket.bms.controller.SuperController;
import com.airport.ticket.bms.dao.AdminUserDao;
import com.airport.ticket.bms.entity.AdminUser;
import com.airport.ticket.bms.model.BaseResponse;
import com.airport.ticket.bms.service.AdminUserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Aspect
@Component
@Repository
public class AccessTokenHandle extends SuperController{

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserService.class);

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private AdminUserDao adminUserDao;

    @Pointcut("execution(public * com.airport.ticket.bms..*.*(..))")
    public void myMethod(){};


    @Around("@annotation(com.airport.ticket.bms.pointCut.AccessToken)")
    public Object doAccessCheck(ProceedingJoinPoint pjp) throws Throwable{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String username = request.getParameter("username");
        String reqToken = request.getParameter("token");

        AdminUser adminUser = adminUserDao.fetchAdminUserByUsername(username);

        if (adminUser == null){
            TokenException tokenException = new TokenException();
            tokenException.setErrCode(-1);
            tokenException.setErrMsg("找不到该用户！");
            throw tokenException;
        }
        request.setAttribute("user", adminUser);

        String token = adminUserService.getToken(username,adminUser.getPassword());

        if (!token.equals(reqToken)){
            TokenException tokenException = new TokenException();
            tokenException.setErrCode(-1);
            tokenException.setErrMsg("无效的token！");
            throw tokenException;
        }

        Object object = pjp.proceed(); //执行连接点方法

        return object;
    }

    @ExceptionHandler(Exception.class)
    @Override
    public BaseResponse exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException {
        return super.exceptionRealHandler(request,response,exception);
    }
}
