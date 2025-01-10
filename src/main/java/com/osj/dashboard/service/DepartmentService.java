package com.osj.dashboard.service;

import com.osj.dashboard.dto.DepartmentDTO;
import com.osj.dashboard.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentMapper departmentMapper;

    @Autowired
    public DepartmentService(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    public List<DepartmentDTO> selectDepartment() {
        return departmentMapper.selectDepartment();
    }


}
