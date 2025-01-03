package com.osj.dashboard.mapper;

import com.osj.dashboard.dto.CustomerDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomerMapper {
    List<CustomerDTO> selectCustomer();

    CustomerDTO selectCustomer(String id);

    void insertCustomer(String id, String name, String information);

    void deleteCustomer(String id);

    void updateCustomer(String id, String name, String information);
}
