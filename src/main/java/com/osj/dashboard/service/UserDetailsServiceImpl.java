package com.osj.dashboard.service;

import com.osj.dashboard.context.UserContext;
import com.osj.dashboard.dto.DepartmentDTO;
import com.osj.dashboard.dto.UserDTO;
import com.osj.dashboard.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService  {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    public UserDetailsServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserDTO findByUserId(String userId){
        return userMapper.findByUserId(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        HashMap<String, Object> userInfoMap = userMapper.findByUserId(userId, "true");

        UserDTO user = new UserDTO().getUserSchema(userInfoMap);

        if (user == null) {
            throw new UsernameNotFoundException("User not found" + userId);
        }

        DepartmentDTO departmentDTO = new DepartmentDTO().getDepartmentSchema(userInfoMap);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getUserRole()));

        UserContext userContext = new UserContext(user, authorities);
        userContext.setDepartment(departmentDTO.getDep_name());

        return userContext;
    }

    public int checkUser(UserDTO loginUser) {

        try {
            UserDTO user = findByUserId(loginUser.getUsername());
            String encodedPwd = passwordEncoder.encode(loginUser.getPassword());

            if (user == null) {
                return HttpStatus.BAD_REQUEST.value();
            }
            if (encodedPwd != user.getPassword()) {
                return HttpStatus.BAD_REQUEST.value();
            }
        }catch (Exception e){
            e.printStackTrace();
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

        return HttpStatus.OK.value();
    }

}
