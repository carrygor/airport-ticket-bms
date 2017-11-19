package com.airport.ticket.bms.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface AdminUserService {
    Map<String,Object> login(String username, String password) throws Exception;

    String getToken(String username, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException ;
}
