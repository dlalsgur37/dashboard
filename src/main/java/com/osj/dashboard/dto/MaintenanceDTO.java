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
    private String owner;
    private  String customerName;

    public MaintenanceDTO(Map<String, String> maintenanceMap) {
        this.id = maintenanceMap.get("id");
        this.name = maintenanceMap.get("name");
        this.owner = maintenanceMap.get("owner");
        this.description = maintenanceMap.get("description");
        this.solve = maintenanceMap.get("solve");
        this.request_date = maintenanceMap.get("request_date");
        this.customerName = maintenanceMap.get("customerName");
    }
}

