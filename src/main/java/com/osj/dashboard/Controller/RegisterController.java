package com.osj.dashboard.Controller;

import com.osj.dashboard.dto.UserDTO;
import com.osj.dashboard.mapper.UserMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
public class RegisterController {
    @Autowired
    private UserMapper userMapper;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/register")
    public String indexPage() {
        return "authentication-register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("userid") String userid,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password,
                               @RequestParam("confirmPassword") String confirmPassword,
                               @RequestParam("email") String email,
                               @RequestParam("dep_id") String depId,
                               @RequestParam("phone_num") int phoneNum,
                               @RequestParam("internal_num") int internal_num,
                               HttpServletResponse response)throws IOException {
        // User 객체 생성 및 데이터 저장
        UserDTO user = UserDTO.builder()
                .userid(userid)
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .depId(depId)
                .regDate(LocalDateTime.now())
                .internalNum(internal_num)
                .phoneNum(phoneNum)
                .userRole("ROLE_USER")
                .build();

        userMapper.insertUser(user);
        return "redirect:/login"; // 회원가입 후 로그인 페이지로 리다이렉트
    }

}
