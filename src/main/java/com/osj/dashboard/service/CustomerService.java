package com.osj.dashboard.service;

import com.osj.dashboard.dto.CustomerDTO;
import com.osj.dashboard.mapper.CustomerMapper;
import org.commonmark.node.Document;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerMapper customerMapper;

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
                //markdown 이미지 처리 필요 시
                /*Parser parser = Parser.builder().build();
                Node document = new Document();
                document.appendChild(parser.parse("<div class=\"markdown-body\">" + customer.getInformation() + "</div>"));
                customer.setInformation(HtmlRenderer.builder().build().render(document));*/
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
    * resultCode = 201 : success
    * resultCode = 500 : fail - unknown error
    * resultCode = 409 : fail - duplicate name
    * */
    public int insertCustomer(CustomerDTO newCustomer) {
        List<CustomerDTO> customerList = selectCustomer();
        try {
            CustomerDTO alreadyCustomer = customerMapper.selectCustomerWithName(newCustomer.getName());

            if (alreadyCustomer != null && !alreadyCustomer.getId().equalsIgnoreCase(newCustomer.getId())) {
                newCustomer.setId("C001");
            } else {
                String lastId = customerList.get(customerList.size()-1).getId();
                String lastIdx = lastId.split("C")[1];
                int idx = Integer.parseInt(lastIdx) + 1;
                newCustomer.setId(String.format("C%03d", idx));
            }

            customerMapper.insertCustomer(newCustomer.getId(), newCustomer.getName(), newCustomer.getInformation());
            return HttpStatus.CREATED.value();
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

    }

    /*
     * Customer Delete from DB
     * @param customer : CustomerDTO
     * @return int - resultCode
     * resultCode = 200 : success
     * resultCode = 500 : fail - unknown error
     * */
    public int deleteCustomer(CustomerDTO customer) {
        String customerId = customer.getId();

        try {
            CustomerDTO realCustomer = customerMapper.selectCustomerWithId(customerId);

            if (realCustomer.getId().equalsIgnoreCase(customerId)) {
                customerMapper.deleteCustomer(customerId);
            }

            return HttpStatus.OK.value();
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

    }

    /*
     * Customer Update to DB
     * @param customer : CustomerDTO
     * @return int - resultCode
     * resultCode = 200 : success
     * resultCode = 409 : fail - duplicate name
     * resultCode = 500 : fail - unknown error
     * */
    public int updateCustomer(CustomerDTO customer) {
        try {
            CustomerDTO alreadyCustomer = customerMapper.selectCustomerWithName(customer.getName());

            if(alreadyCustomer != null && !alreadyCustomer.getId().equalsIgnoreCase(customer.getId()))
                return HttpStatus.CONFLICT.value();
            else
                customerMapper.updateCustomer(customer.getId(), customer.getName(), customer.getInformation());

            return HttpStatus.OK.value();
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

    }
}
