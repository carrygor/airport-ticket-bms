package com.airport.ticket.bms.pointCut.handle;


import com.airport.ticket.bms.ExceptionGMS.TokenException;
import com.airport.ticket.bms.dao.AdminUserDao;
import com.airport.ticket.bms.entity.AdminUser;
import com.airport.ticket.bms.pointCut.CheckToken;
import com.airport.ticket.bms.service.AdminUserService;
import org.apache.shiro.aop.MethodInterceptor;
import org.apache.shiro.aop.MethodInvocation;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Component
@Aspect
public class CheckTokenHandle implements MethodInterceptor {


    @Autowired
    private AdminUserDao adminUserDao;

    @Autowired
    private AdminUserService adminUserService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckTokenHandle.class);

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        Method method = methodInvocation.getMethod();
        Object[] arguments = methodInvocation.getArguments();

        boolean valiation = method.isAnnotationPresent(CheckToken.class);
        if (!valiation){
            return methodInvocation.proceed();
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String username = request.getParameter("username");
        String reqToken = request.getParameter("token");

        AdminUser user = adminUserDao.fetchAdminUserByUsername(username);

        String token = "";

        if (user != null){
            token = adminUserService.getToken(user.getUsername(),user.getPassword());
        }

        if ("".equals(token) || !token.equals(reqToken)){
            TokenException tokenException = new TokenException();
            tokenException.setErrMsg("token不存在或者不可用！");
            tokenException.setErrCode(-1);
            throw tokenException;
        }

        return methodInvocation.proceed();
    }
}
