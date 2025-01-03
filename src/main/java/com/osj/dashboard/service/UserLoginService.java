package com.osj.dashboard.service;

import com.osj.dashboard.dto.CustomerDTO;
import com.osj.dashboard.dto.UserDTO;
import com.osj.dashboard.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {

    private final UserMapper userMapper;

    public UserDTO findByUserId(String userId){
        return userMapper.findByUserId(userId);
    }
    @Autowired
    public UserLoginService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


}
