package com.osj.dashboard.mapper;

import com.osj.dashboard.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface UserMapper {
    UserDTO findByUserId(String username);

    HashMap<String, Object> findByUserId(String username, String department);

    void insertUser(UserDTO user);


}
