package com.osj.dashboard.Controller;

import com.osj.dashboard.dto.UserDTO;
import com.osj.dashboard.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;
import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/login")
    public String loginPage(Authentication authentication)  {
        // 이미 로그인된 경우 /index로 리다이렉트
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/index";
        }
        return "authentication-login";
    }

    @PostMapping("/login")
    public int Login(@RequestParam("userid") String userid,
                        @RequestParam("password") String password,
                        HttpServletResponse response)throws IOException{
        UserDTO loginUser = new UserDTO();
        loginUser.setUserid(userid);
        loginUser.setPassword(loginUser.getPassword());
        int result = userDetailsService.checkUser(loginUser);

        if(result == HttpStatus.BAD_REQUEST.value()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return result;


    }




}