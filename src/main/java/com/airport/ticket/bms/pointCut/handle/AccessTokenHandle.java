package com.airport.ticket.bms.pointCut.handle;


import com.airport.ticket.bms.ExceptionGMS.TokenException;
import com.airport.ticket.bms.dao.AdminUserDao;
import com.airport.ticket.bms.entity.AdminUser;
import com.airport.ticket.bms.service.AdminUserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Repository
public class AccessTokenHandle {

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
        String username = request.getHeader("username");
        String reqToken = request.getHeader("token");

        AdminUser adminUser = adminUserDao.fetchAdminUserByUsername(username);

        if (adminUser == null){
            TokenException tokenException = new TokenException();
            tokenException.setResultCode(-1);
            tokenException.setErrorMsg("找不到该用户！");
            throw tokenException;
        }

        String token = adminUserService.getToken(username,adminUser.getPassword());

        if (!token.equals(reqToken)){
            TokenException tokenException = new TokenException();
            tokenException.setResultCode(-1);
            tokenException.setErrorMsg("无效的token！");
            throw tokenException;
        }

        Object object = pjp.proceed(); //执行连接点方法

        return object;
    }
}
