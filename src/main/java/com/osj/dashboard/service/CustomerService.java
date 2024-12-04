package com.osj.dashboard.service;

import com.osj.dashboard.dto.CustomerDTO;
import com.osj.dashboard.mapper.CustomerMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private CustomerMapper customerMapper;

    public CustomerService(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    public List<CustomerDTO> selectCustomer() {
        return customerMapper.selectCustomer();
    }

    public void insertCustomer(CustomerDTO newCustomer) {
        customerMapper.insertCustomer(newCustomer.getId(), newCustomer.getName(), newCustomer.getInformation());
    }
}
