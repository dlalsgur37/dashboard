package com.osj.dashboard.Controller;

import com.osj.dashboard.UserService;
import org.springframework.ui.Model;
import com.osj.dashboard.model.User;
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
        model.addAttribute("user", new User());
        System.out.println("register");
        return "authentication-register";
    }

    // 회원가입 처리
    @PostMapping("/register/add")
    public String registerUser(@ModelAttribute("user") User user) {
        System.out.println("USER >>> " + user.getPassword());
        // user.getPassword()가 null인지 확인
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        userService.registerUser(user);
        return "authentication-login";
    }


}
