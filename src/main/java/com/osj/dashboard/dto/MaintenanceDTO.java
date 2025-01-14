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
    private String request_user;
    private String description;
    private String solve;
    private String request_date;
    private String owner;
    private String customerId;
    private String customerName;
    private String domainId;
    private String domainName;
    private String title;
    private String type;

    public MaintenanceDTO(Map<String, String> maintenanceMap) {
        this.id = maintenanceMap.get("id");
        this.request_user = maintenanceMap.get("request_user");
        this.owner = maintenanceMap.get("owner");
        this.description = maintenanceMap.get("description");
        this.solve = maintenanceMap.get("solve");
        this.title = maintenanceMap.get("title");
        this.request_date = maintenanceMap.get("request_date");
        this.customerId = maintenanceMap.get("customerId");
        this.domainId = maintenanceMap.get("domainId");
        this.customerName = maintenanceMap.get("customerName");
        this.domainName = maintenanceMap.get("domainName");
        this.type = maintenanceMap.get("type");
    }
}


