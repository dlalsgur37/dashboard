package com.osj.dashboard.dto;


import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;                // PK
    private String nickname;           // 사용자 이름
    private String username;             // 사용자 ID
    private String password;           // 비밀번호
    private LocalDateTime regDate;     // 등록일
    private Integer phoneNum;          // 전화번호
    private Integer internalNum;       // 내부 번호
    private String email;              // 이메일
    private String userRole;          // 사용자 권한

    private DepartmentDTO departmentDTO;             // 부서
}


