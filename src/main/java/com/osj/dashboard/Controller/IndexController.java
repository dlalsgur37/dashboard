package com.osj.dashboard.Controller;

import com.osj.dashboard.context.UserContext;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/index")
    public String index(HttpSession session, Model model){
        // SecurityContext에서 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자 정보 가져오기
        UserContext userContext = (UserContext) authentication.getPrincipal();

        session.setAttribute("username", userContext.getUsername());
        session.setAttribute("userNickname", userContext.getNickname());
        // 모델에 사용자 정보 추가
        model.addAttribute("username", userContext.getNickname());

        return "index";

    }

    @GetMapping("/")
    public String home() {
        return "redirect:/index";
    }
}
