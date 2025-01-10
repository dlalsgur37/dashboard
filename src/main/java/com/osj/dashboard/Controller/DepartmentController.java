package com.osj.dashboard.Controller;

import com.osj.dashboard.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

@Controller
public class DepartmentController {

    @Autowired
    private final DepartmentService departmentService;


    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }



}
