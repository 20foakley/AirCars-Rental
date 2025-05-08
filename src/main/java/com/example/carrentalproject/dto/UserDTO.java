package com.example.carrentalproject.dto;
import com.example.carrentalproject.model.Users;

public class UserDTO {
    // need to add other attributes here at some point
    private Long id;
    private String username;

    public UserDTO(Users user) {
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
