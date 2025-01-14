package com.osj.dashboard.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private String id;
    private String name;
    private String information;
}
