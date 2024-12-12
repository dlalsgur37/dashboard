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
public class CustomerDTO {

    private String id;
    private String name;
    private String information;

    public CustomerDTO(Map<String, String> customerMap) {
        this.id = customerMap.get("id");
        this.name = customerMap.get("name");
        this.information = customerMap.get("information");
    }
}
