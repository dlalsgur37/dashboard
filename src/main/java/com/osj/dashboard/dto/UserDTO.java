package com.osj.dashboard.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDTO {
    private Integer id;                // PK
    private String nickname;           // 사용자 이름
    private String username;             // 사용자 ID
    private String password;           // 비밀번호
    private LocalDateTime regDate;     // 등록일
    private Integer phoneNum;          // 전화번호
    private Integer internalNum;       // 내부 번호
    private String email;              // 이메일
    private String depId;              // 부서 ID
    private String userRole;          // 사용자 권한
}


