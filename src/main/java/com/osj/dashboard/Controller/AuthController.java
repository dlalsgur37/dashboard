package com.osj.dashboard.Controller;

import com.osj.dashboard.context.UserContext;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/me")
    public UserContext getCurrentUser(Authentication authentication) {
        return (UserContext) authentication.getPrincipal();
    }
}
