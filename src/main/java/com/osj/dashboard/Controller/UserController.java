package com.osj.dashboard.Controller;

import com.osj.dashboard.dto.UserDTO;
import com.osj.dashboard.service.UserService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 폼
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserDTO());
        System.out.println("register");
        return "authentication-register";
    }

    // 회원가입 처리
    @PostMapping("/register/add")
    public String registerUser(@ModelAttribute("user") UserDTO userDTO) {
        System.out.println("USER >>> " + userDTO.getPassword());
        // user.getPassword()가 null인지 확인
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        userService.registerUser(userDTO);
        return "authentication-login";
    }


}
