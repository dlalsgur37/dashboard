package com.osj.dashboard.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceDTO {

    private String id;
    private String requestUser;
    private String description;
    private String solve;
    private String requestDate;
    private String owner;
    private String customerId;
    private String customerName;
    private String domainId;
    private String domainName;
    private String title;
    private String type;
}


