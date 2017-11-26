package com.airport.ticket.bms.service;

import com.airport.ticket.bms.entity.AdminUser;
import com.airport.ticket.bms.form.adminUser.AdminUserForm;
import net.sf.json.JSONArray;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface AdminUserService {
    Map<String,Object> login(String username, String password) throws Exception;

    Map<String,Object> fetchPageAdminUser(int pageNo, int pageSize) throws Exception;

    Map<String,Object> searchAdminUser(String name, String wordId, String level) throws Exception;

    boolean addAdminUser(AdminUserForm array) throws Exception;

    boolean updateAdminUser(AdminUser user) throws Exception;

    boolean updateAdminUser(AdminUserForm form) throws Exception;

    AdminUser fetchAdminUserByKey(int key) throws Exception;

    String getToken(String username, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException;

}
