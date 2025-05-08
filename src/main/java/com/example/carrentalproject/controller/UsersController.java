package com.example.carrentalproject.controller;

import com.example.carrentalproject.model.Users;
import com.example.carrentalproject.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping
    public List<Users> getAllUsers() {
        return usersRepository.findAll();




    }
}
