package com.airport.ticket.bms.dao;

import com.airport.ticket.bms.entity.CustomerList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerListDao {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerList record);

    int insertSelective(CustomerList record);

    CustomerList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerList record);

    int updateByPrimaryKey(CustomerList record);

    List<CustomerList> fetchCustomerByUsername(String name);

    CustomerList fetchCustomerByIdCard(String idCard);

    List<CustomerList> searchCustomer(@Param("name") String name, @Param("id_card") String id_card, @Param("email") String email);

    int totalCustomer();

    List<CustomerList> fetchPageCustomer(@Param("startNo") int startNo, @Param("pageSize") int pageSize);

    List<CustomerList> fetchAllCustomers() ;
}