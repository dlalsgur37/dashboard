package com.osj.dashboard.service;

import com.osj.dashboard.context.UserContext;
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

import java.util.ArrayList;
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
        UserDTO user = userMapper.findByUserId(userId);
        if (user == null) {
            throw new UsernameNotFoundException("User not found" + userId);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        UserContext userContext = new UserContext(user, authorities);

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
