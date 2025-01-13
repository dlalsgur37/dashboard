package com.osj.dashboard.Controller;

import com.osj.dashboard.context.UserContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    public AuthController() {
    }

    @GetMapping("/me")
    public UserContext getCurrentUser(Model model) {
        UserContext userContext = (UserContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("nickname", userContext.getNickname());
        return userContext;
    }
}
