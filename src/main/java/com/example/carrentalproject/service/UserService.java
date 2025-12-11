package com.example.carrentalproject.service;
import com.example.carrentalproject.dto.UserDTO;
import com.example.carrentalproject.dto.RegisterRequest;
import com.example.carrentalproject.exception.UserNotFoundException;
import com.example.carrentalproject.model.User;
import com.example.carrentalproject.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserService {

    // Learning experience: I had PasswordEncoder listed as BCryptPasswordEncoder
    // But this violates loose coupling and testability

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));;
        return new UserDTO(user);
    }

    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));;
        return new UserDTO(user);
    }

    public UserDTO getUserByEmail(String email) {
        User user =  userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
        return new UserDTO(user);

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User registerUser(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken.");
        }
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered.");
        }
        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());
        User user = new User(registerRequest.getUsername(), hashedPassword, registerRequest.getEmail());
        //System.out.println("successfully registered with username, email, plain pw, hash pw: " + username + ", " + email + ", " + password + ", " + hashedPassword);
        return userRepository.save(user);
    }


}
