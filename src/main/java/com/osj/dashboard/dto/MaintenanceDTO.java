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
public class MaintenanceDTO {

    private String id;
    private String name;
    private String description;
    private String solve;
    private String request_date;

    public MaintenanceDTO(Map<String, String> customerMap) {
        this.id = customerMap.get("id");
        this.name = customerMap.get("name");
        this.description = customerMap.get("description");
        this.solve = customerMap.get("solve");
        this.request_date = customerMap.get("request_date");
    }
}

