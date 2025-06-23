package com.example.demo.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entity.Users;
import com.example.demo.service.UserService;

@RestController
public class UserController 
{
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<String> userRegistration(@RequestParam String email, @RequestParam String password)
	{
		userService.registerUser(email, password);
		return ResponseEntity.ok("Email registered successfully check your email to verify yourself");
	}
	
	@GetMapping("/verify")
	public ResponseEntity<String> verifyUser(@RequestParam String token)
	{
		if(userService.verifyToken(token))
		{
			return ResponseEntity.ok("Email verified successfully you can now login");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token try again");
	}
	
	@GetMapping("/getUserByEmail/{email}")
	public Optional<Users> getUserByEmailId(@PathVariable String email)
	{
		return userService.getUserByEmail(email);
	}
}
