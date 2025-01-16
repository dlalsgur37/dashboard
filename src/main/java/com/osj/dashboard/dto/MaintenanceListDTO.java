package com.osj.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceListDTO {
    public UserDTO userDTO;
    public MaintenanceDTO maintenanceDTO;
    public CustomerDTO customerDTO;
    public DepartmentDTO departmentDTO;
}
