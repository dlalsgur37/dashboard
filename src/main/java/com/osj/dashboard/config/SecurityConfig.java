package com.osj.dashboard.config;

import com.osj.dashboard.service.UserLoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    private final UserLoginService userLoginService;

    public SecurityConfig(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            response.sendRedirect("/index");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http	.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/register","/", "/login","/css/**", "/js/**").permitAll()
                        .requestMatchers("/index", "/loginForm").hasRole("user")
                        .anyRequest().authenticated())
				.formLogin(formLogin -> formLogin
                        .usernameParameter("userid")
                        .passwordParameter("password")
                        .loginProcessingUrl("/login")
                        .loginPage("/login")
						.defaultSuccessUrl("/index"))
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true))
                .rememberMe(rememberMe->rememberMe
                        .key("uniqueAndSecretKey")// 고유 키 설정
                        .tokenValiditySeconds(86400))// 1일 동안 유효
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .sessionFixation().none()
                        .maximumSessions(1) // 한 사용자당 최대 1개의 세션 허용
                        .maxSessionsPreventsLogin(false) // 새로운 로그인이 기존 세션을 만료
                );
        return http.build();
    }

}
