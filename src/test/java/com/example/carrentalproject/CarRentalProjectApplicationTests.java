package com.example.carrentalproject;

import com.example.carrentalproject.model.Users;
import com.example.carrentalproject.repository.UsersRepository;
import com.example.carrentalproject.service.UsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CarRentalProjectApplicationTests {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UsersRepository usersRepository;

	@Test
	void testRegisterUser() {
		String username = "john3";
		String password = "password123";
		String email = "bob@bob.com";

		UsersService usersService = new UsersService(passwordEncoder, usersRepository);
		usersService.registerUser(username, password, email);
	}
}
