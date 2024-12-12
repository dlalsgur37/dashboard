package com.osj.dashboard.service;

import com.osj.dashboard.dto.CustomerDTO;
import com.osj.dashboard.mapper.CustomerMapper;
import org.commonmark.node.Document;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private CustomerMapper customerMapper;

    @Autowired
    public CustomerService(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    public List<CustomerDTO> selectCustomer() {
        return customerMapper.selectCustomer();
    }

    public List<CustomerDTO> selectCustomer(boolean markdown) {
        List<CustomerDTO> customerList = selectCustomer();

        if (markdown) {
            for (CustomerDTO customer : customerList) {
                Parser parser = Parser.builder().build();
                Node document = new Document();
                document.appendChild(parser.parse("<div class=\"markdown-body\">" + customer.getInformation() + "</div>"));
                customer.setInformation(HtmlRenderer.builder().build().render(document));
            }
        } else {
            customerList = selectCustomer();
        }
        return customerList;
    }

    /*
    * Customer Insert to DB
    * @param newCustomer : CustomerDTO
    * @return int - resultCode
    * resultCode = 0 : success
    * resultCode = 1 : fail - unknown error
    * resultCode = 2 : fail - duplicate name
    * */
    public int insertCustomer(CustomerDTO newCustomer) {
        List<CustomerDTO> customerList = selectCustomer();
        String newName = newCustomer.getName();
        try {
            for (CustomerDTO customer : customerList) {
                if (customer.getName().equals(newName)) {
                    System.out.println("Error : duplicate name !!");
                    return 2;
                }
            }

            if (customerList.isEmpty()) {
                newCustomer.setId("C001");
            } else {
                String lastId = customerList.get(customerList.size()-1).getId();
                String lastIdx = lastId.split("C")[1];
                int idx = Integer.parseInt(lastIdx) + 1;
                newCustomer.setId(String.format("C%03d", idx));
            }

            customerMapper.insertCustomer(newCustomer.getId(), newCustomer.getName(), newCustomer.getInformation());
            return 0;
        } catch (Exception e) {
            return 1;
        }

    }

    /*
     * Customer Delete from DB
     * @param customer : CustomerDTO
     * @return int - resultCode
     * resultCode = 0 : success
     * resultCode = 1 : fail - unknown error
     * */
    public int deleteCustomer(CustomerDTO customer) {
        String customerId = customer.getId();
        int resultCode = -1;

        try {
            CustomerDTO realCustomer = customerMapper.selectCustomer(customerId);

            if (realCustomer.getId().equalsIgnoreCase(customerId)) {
                customerMapper.deleteCustomer(customerId);
                resultCode = 0;
            }

            return resultCode;
        } catch (Exception e) {
            resultCode = 1;
            return resultCode;
        }

    }
}
