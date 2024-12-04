package com.osj.dashboard;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.osj.dashboard.dto.CustomerDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.osj.dashboard.service.CustomerService;

import java.util.List;

@RestController
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;

    }

    @PostMapping("/customer/all")
    public List<CustomerDTO> getCustomerList() {
        List<CustomerDTO> customerList = customerService.selectCustomer();
        System.out.println("customer : " + customerList);
        return customerList;
    }

    @PostMapping("/customer/add")
    public int addCustomer(String id, String name, String information) {
        CustomerDTO newCustomer = new CustomerDTO(id, name, information);
        List<CustomerDTO> customerList = customerService.selectCustomer();
        try {
            for (CustomerDTO customer : customerList) {
                if (customer.getId().equals(id)) {
                    System.out.println("Error : duplicate id !!");
                    return 0;
                }
            }
            customerService.insertCustomer(newCustomer);
            return 1;

        } catch (Exception e) {
            return 0;
        }
    }
}
