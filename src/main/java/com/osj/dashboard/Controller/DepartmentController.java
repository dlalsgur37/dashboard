package com.osj.dashboard.Controller;

import com.osj.dashboard.dto.DepartmentDTO;
import com.osj.dashboard.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DepartmentController {

    @Autowired
    private final DepartmentService departmentService;


    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/Dempartment")
    public List<DepartmentDTO> getDpartment(){
        return departmentService.selectDepartment();
    }


}
