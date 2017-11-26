package com.airport.ticket.bms.service.Imp;

import com.airport.ticket.bms.Enum.KeyEnum;
import com.airport.ticket.bms.ExceptionGMS.SystemException;
import com.airport.ticket.bms.dao.AdminUserDao;
import com.airport.ticket.bms.entity.AdminUser;
import com.airport.ticket.bms.form.adminUser.AdminUserForm;
import com.airport.ticket.bms.service.AdminUserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

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


    public Map<String,Object> fetchPageAdminUser(int pageNo, int pageSize) throws Exception {

        List<AdminUser> users = null;

        int totalPage = 0;
        try {
            totalPage = adminUserDao.totalUser();
            users = adminUserDao.fetchPageAdminUser((pageNo - 1) * pageSize,pageSize);
        } catch (Exception e){
            SystemException systemException = new SystemException();
            systemException.setErrCode(2);
            systemException.setErrMsg("数据库查询错误！");
            throw systemException;
        }

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("total",totalPage);
        map.put("list",users);
        return map;
    }

    public Map<String,Object> searchAdminUser(String name, String wordId, String level) throws Exception{

        List<AdminUser> messages = null;
        int totalPage = 0;

        try {
            messages = adminUserDao.searchAdminUser(name,name,level);
        } catch (Exception e){
            SystemException systemException = new SystemException();
            systemException.setErrCode(2);
            systemException.setErrMsg("数据库查询错误！");
            throw systemException;
        }

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("total",totalPage);
        map.put("list",messages);
        return map;
    }


    @Transactional(rollbackFor=Exception.class)
    public boolean addAdminUser(AdminUserForm array) throws Exception {

        int count = adminUserDao.totalUser();


        //加上对是否有该成员的判断
        //to do
        AdminUser user = adminUserDao.fetchAdminUserByUsername(array.getUsername());
        if (user != null){
            SystemException se = new SystemException();
            se.setErrCode(3);
            se.setErrMsg("已存在该成员！");
            throw  se;
        }

        AdminUser adminUser = new AdminUser();
        adminUser.setUsername(array.getUsername());
        adminUser.setRealname(array.getRealName());
        adminUser.setPassword(array.getPassword());
        adminUser.setWorkId(10000+count++);
        adminUser.setPhone(array.getPhone());
        adminUser.setEmail(array.getEmail());

        int isSuccess = adminUserDao.insert(adminUser);
        return isSuccess == 1 ? true :false;
    }

    public boolean updateAdminUser(AdminUser user) throws Exception{
        return adminUserDao.updateByPrimaryKeySelective(user) == 1 ? true:false;
    }

    public boolean updateAdminUser(AdminUserForm form) throws Exception{
        AdminUser user = new AdminUser();
        user.setId(form.getId());
        user.setEmail(form.getEmail());
        user.setPhone(form.getPhone());
        user.setWorkId(form.getWorkId());
        user.setPassword(form.getPassword());
        user.setRealname(form.getRealName());
        user.setUsername(form.getUsername());
        user.setLevel(form.isLevel());
        user.setStatus(form.isStatus());

        return adminUserDao.updateByPrimaryKeySelective(user) == 1 ? true:false;

    }

    public AdminUser fetchAdminUserByKey(int key) throws Exception{
        return adminUserDao.selectByPrimaryKey(key);
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
