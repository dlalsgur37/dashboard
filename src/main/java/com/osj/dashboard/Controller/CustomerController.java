package com.osj.dashboard.Controller;

import com.osj.dashboard.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.osj.dashboard.service.CustomerService;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CustomerController {
    private final CustomerService customerService;

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

        int resultCode;

        resultCode = customerService.insertCustomer(newCustomer);

        if (resultCode == HttpStatus.CONFLICT.value())
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        else if (resultCode == HttpStatus.INTERNAL_SERVER_ERROR.value())
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

        return resultCode;
    }

    @DeleteMapping("/customer/{id}")
    public int delCustomer(@PathVariable String id) {
        CustomerDTO targetCustomer = CustomerDTO.builder()
                                                .id(id).build();

        int resultCode;
        resultCode = customerService.deleteCustomer(targetCustomer);

        if (resultCode == 500)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

        return resultCode;
    }

    @PutMapping("/customer")
    public int updateCustomer(@RequestBody CustomerDTO targetCustomer) {

        int resultCode;
        resultCode = customerService.updateCustomer(targetCustomer);

        if (resultCode == 500)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        else if (resultCode == 409)
            throw new ResponseStatusException(HttpStatus.CONFLICT);

        return resultCode;
    }
}
