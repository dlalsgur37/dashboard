package com.osj.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class DepartmentDTO {

    private String id;
    private String dep_name;

    public DepartmentDTO(Map<String, String> customerMap) {
        this.id = customerMap.get("id");
        this.dep_name = customerMap.get("dep_name");
    }
}
