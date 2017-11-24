package com.airport.ticket.bms.dao;

import com.airport.ticket.bms.entity.AdminUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminUserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);

    AdminUser fetchAdminUserByUsername(@Param("username") String username);

    List<AdminUser> searchAdminUser(@Param("name") String name,@Param("word_id")String wordId,@Param("level")String level);

    int totalUser();

    List<AdminUser> fetchPageAdminUser(@Param("startNo") int startNo, @Param("pageSize") int pageSize);
}