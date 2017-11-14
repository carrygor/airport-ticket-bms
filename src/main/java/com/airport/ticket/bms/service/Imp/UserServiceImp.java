package com.airport.ticket.bms.service.Imp;

import com.airport.ticket.bms.dao.UserMapper;
import com.airport.ticket.bms.entity.User;
import com.airport.ticket.bms.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class UserServiceImp implements UserService {

    @Resource
    private UserMapper userMapper;

    public User selectByPrimaryKey(int i) {
        return userMapper.selectByPrimaryKey(i);
    }
}
