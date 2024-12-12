package com.osj.dashboard.Controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.osj.dashboard.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import com.osj.dashboard.service.CustomerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CustomerController {
    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getCustomerList() {
        return customerService.selectCustomer(true);
    }

    @PostMapping("/customer")
    public int addCustomer(String name, String information) {
        CustomerDTO newCustomer = CustomerDTO.builder()
                                            .id("")
                                            .name(name)
                                            .information(information).build();
        int resultCode = 1;
        try {
            resultCode = customerService.insertCustomer(newCustomer);
            return resultCode;

        } catch (Exception e) {
            return resultCode;
        }
    }

    @DeleteMapping("/customer/{id}")
    public int delCustomer(@PathVariable String id) {
        CustomerDTO targetCustomer = CustomerDTO.builder()
                                                .id(id).build();
        int resultCode = 1;
        try {
            resultCode = customerService.deleteCustomer(targetCustomer);
            return resultCode;

        } catch (Exception e) {
            return resultCode;
        }
    }
}
