package com.osj.dashboard.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerDTO {

    private String id;
    private String name;
    private String information;

    public CustomerDTO(String id, String name, String information) {
        this.id = id;
        this.name = name;
        this.information = information;
    }
}
