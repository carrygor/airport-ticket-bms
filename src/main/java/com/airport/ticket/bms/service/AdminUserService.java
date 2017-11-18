package com.airport.ticket.bms.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public interface AdminUserService {
    String login(String username,String password);

    String getToken(String username, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException ;
}
