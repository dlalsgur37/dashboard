package com.osj.dashboard.mapper;

import com.osj.dashboard.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDTO, String> {
}
