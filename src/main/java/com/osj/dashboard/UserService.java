package com.osj.dashboard;

import com.osj.dashboard.model.User;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.osj.dashboard.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {

        try{
            // 비밀번호가 null일 경우 예외 처리
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                throw new IllegalArgumentException("Password cannot be null or empty");
            }

            // 비밀번호 암호화 처리 (예: BCrypt)
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            user.setId(3214L);
            user.setReg_date(LocalDate.now());
            System.out.println("user >>>>>>>> " + user.getId());
            return userRepository.save(user); // 저장된 사용자 반환

        }catch (Exception e){
            logger.error("사용자 저장 중 오류 발생", e);
            throw e;
        }

    }
}