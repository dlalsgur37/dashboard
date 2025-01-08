package com.osj.dashboard.mapper;

import com.osj.dashboard.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserDTO findByUserId(String username);

    void insertUser(UserDTO user);


}
