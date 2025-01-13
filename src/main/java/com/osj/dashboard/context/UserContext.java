package com.osj.dashboard.context;

import com.osj.dashboard.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class UserContext extends User {
    private final String username;
    private final String nickname;
    @Setter
    private String department;

    public UserContext(UserDTO user, List<GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.department = "";
    }
}
