package com.airport.ticket.bms.service.Imp;

import com.airport.ticket.bms.ExceptionGMS.SystemException;
import com.airport.ticket.bms.dao.AdminUserDao;
import com.airport.ticket.bms.entity.AdminUser;
import com.airport.ticket.bms.service.AdminUserService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminUserServiceImp implements AdminUserService {

    @Autowired
    private AdminUserDao adminUserDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserService.class);

    public Map<String,Object> login(String username, String password) throws Exception{

        AdminUser adminUser = adminUserDao.fetchAdminUserByUsername(username);

        Map<String,Object> map  = new HashMap<String,Object>();

        if (adminUser != null && adminUser.getPassword().equals(password)){

            String token = "";
            try {
                token = this.getToken(username,password);
                map.put("token",token);
                map.put("msg","登陆成功！");
                return map;

            } catch (Exception e) {
                LOGGER.debug(e.getMessage());
                SystemException se = new SystemException();
                se.setErrCode(-1);
                se.setErrMsg("获取token失败！");
                throw se;
            }

        } else {
            map.put("msg","检验失败！");
        }
        return map;
    }


    public String getToken(String username, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(new Date());
        MessageDigest md5 = null;
        String token ;
        md5 = MessageDigest.getInstance("MD5");
        md5.update((username+password).getBytes("utf-8"));
        String encryptionKey = new BigInteger(1,md5.digest()).toString(16);
        String value = "service"+"AdminUserController"+this.getClass().getName()+ dateStr + encryptionKey;
        md5.update(value.getBytes("utf-8"));
        token = new BigInteger(1,md5.digest()).toString(16);
        return token;
    }
}
