package com.osj.dashboard.mapper;

import com.osj.dashboard.dto.DepartmentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    List<DepartmentDTO> selectDepartment();
}
