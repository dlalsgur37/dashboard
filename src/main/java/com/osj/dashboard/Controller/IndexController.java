package com.osj.dashboard.Controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@Controller
public class IndexController {
    @GetMapping("/index")
    public String index(Model model, HttpSession session){
        // SecurityContext에서 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자 정보 가져오기
        User user = (User) authentication.getPrincipal();

        // 모델에 사용자 정보 추가
        model.addAttribute("username", user.getUsername());
        return "index";

    }


    @ModelAttribute("username")
    public String username( ){
        // SecurityContext에서 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "";
        try{
            // 사용자 정보 가져오기
            User user = (User) authentication.getPrincipal();
            username = user.getUsername();
        }catch(Exception e){
            username="";
        }
        return  username;

    }

}
