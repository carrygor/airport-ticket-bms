package com.airport.ticket.bms.service.Imp;

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

@Service
public class AdminUserServiceImp implements AdminUserService {

    @Autowired
    private AdminUserDao adminUserDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserService.class);

    public String login(String username, String password) {

        AdminUser adminUser = adminUserDao.fetchAdminUserByUsername(username);

        JSONObject result = new JSONObject();
        if (adminUser != null && adminUser.getPassword().equals(password)){

            MessageDigest md5 = null;
            String token ;
            try {
                token = this.getToken(username,password);
                result.put("resultCode","1");
                result.put("msg","检验成功！");
                result.put("token",token);
                return result.toString();

            } catch (NoSuchAlgorithmException e) {
                LOGGER.debug(e.getMessage());
                e.printStackTrace();
                result.put("msg","致命错误！");
                result.put("resultCode","-1");
                return result.toString();
            } catch (UnsupportedEncodingException e) {
                LOGGER.debug(e.getMessage());
                e.printStackTrace();
                result.put("resultCode","-1");
                result.put("msg","致命错误！");
                return result.toString();
            }

        } else {
            result.put("resultCode","0");
            result.put("msg","检验失败！");
        }
        return result.toString();
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
