package com.osj.dashboard.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RequestController {

    @RequestMapping("/list/maintenance")
    public String maintenance_list() {  return "maintenance-list";  }

    @RequestMapping("/list/customer")
    public String customer_list() {  return "customer-list";  }

    @RequestMapping("/login")
    public String login() {
        return "authentication-login";
    }





}
