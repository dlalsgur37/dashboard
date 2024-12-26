package com.osj.dashboard.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RequestController {
    @RequestMapping("/")
    public String home() {
        return "authentication-login";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/list/maintance")
    public String maintance_list() {  return "maintance-list";  }

    @RequestMapping("/list/customer")
    public String customer_list() {  return "customer-list";  }

    @RequestMapping("/login")
    public String login() {
        return "authentication-login";
    }





}
