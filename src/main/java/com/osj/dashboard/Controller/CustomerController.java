package com.osj.dashboard.Controller;

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
    public int addCustomer(String name, String information) {
        CustomerDTO newCustomer = new CustomerDTO("", name, information);
        List<CustomerDTO> customerList = customerService.selectCustomer();
        try {
            for (CustomerDTO customer : customerList) {
                if (customer.getName().equals(name)) {
                    System.out.println("Error : duplicate name !!");
                    return 0;
                }
            }

            String lastId = customerList.get(customerList.size()-1).getId();
            String lastIdx = lastId.split("C")[1];
            int idx = Integer.parseInt(lastIdx) + 1;
            newCustomer.setId(String.format("C%03d", idx));

            customerService.insertCustomer(newCustomer);
            return 1;

        } catch (Exception e) {
            return 0;
        }
    }
}
