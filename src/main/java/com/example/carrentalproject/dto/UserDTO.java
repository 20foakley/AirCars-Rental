package com.example.carrentalproject.dto;
import com.example.carrentalproject.model.User;

public class UserDTO {
    // need to add other attributes here at some point (role?)
    // this class provides a more secure and efficient way to send user info without including a bunch of unnecessary fields!
    private Long id;
    private String username;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
