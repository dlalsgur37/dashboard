package com.osj.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RequestController {
    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/list/maintance")
    public String maintance_list() {  return "maintance_list";  }

    @RequestMapping("/list/customer")
    public String customer_list() {  return "customer_list";  }
}
