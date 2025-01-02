package com.osj.dashboard.mapper;

import com.osj.dashboard.dto.UserDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    UserDTO findByUserId(String userId);

    void insertUser(UserDTO user);


}
