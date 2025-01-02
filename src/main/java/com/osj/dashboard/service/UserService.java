package com.osj.dashboard.service;

import com.osj.dashboard.dto.CustomerDTO;
import com.osj.dashboard.dto.UserDTO;
import com.osj.dashboard.mapper.UserMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
        this.passwordEncoder = new BCryptPasswordEncoder(); // 비밀번호 암호화
    }


    public void createUser(UserDTO user) {
        userMapper.insertUser(user);
    }


    public void registerUser(UserDTO user) {
        // 현재 시간 설정
        user.setRegDate(LocalDateTime.now());

        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 사용자 역할 기본값 설정
        if (user.getUserRole() == null) {
            user.setUserRole("ROLE_USER");
        }


        // DB에 사용자 정보 저장
        userMapper.insertUser(user);
    }

}
