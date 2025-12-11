package com.example.carrentalproject;

import com.example.carrentalproject.dto.RegisterRequest;
import com.example.carrentalproject.security.JWTUtil;
import com.example.carrentalproject.repository.UserRepository;
import com.example.carrentalproject.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CarRentalProjectApplicationTests {

	public final UserService userService;

	public CarRentalProjectApplicationTests(UserService userService) {
        this.userService = userService;
	}


	@Test
	void testRegisterUser() {
		String username = "john3";
		String password = "password123";
		String email = "bob@bob.com";
		RegisterRequest registerRequest = new RegisterRequest(username,password,email);
		userService.registerUser(registerRequest);
	}


}
