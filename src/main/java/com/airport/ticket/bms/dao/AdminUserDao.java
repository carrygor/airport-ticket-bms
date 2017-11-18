package com.airport.ticket.bms.dao;

import com.airport.ticket.bms.entity.AdminUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);

    AdminUser fetchAdminUserByUsername(@Param("username") String username);
}