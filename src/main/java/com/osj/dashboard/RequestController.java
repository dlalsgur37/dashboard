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
}
