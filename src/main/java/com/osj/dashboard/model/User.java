package com.osj.dashboard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userid;
    private String username;
    private String email;
    @Column(nullable = false)
    private String password;
    private String dep_id;

    @Column(name = "phone_num")
    private String phone_num;

    @Column(name = "internal_num")
    private String officephone;

    @Column(name = "reg_date")
    private LocalDate reg_date; // 회원가입 날짜



}


