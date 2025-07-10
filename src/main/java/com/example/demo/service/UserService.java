package com.example.demo.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;

@Service
public class UserService 
{	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	public void registerUser(String email, String password)
	{
		String hassedPassword = passwordEncoder.encode(password);
		String token = UUID.randomUUID().toString();
		
		Users user = new Users();
		user.setEmail(email);
		user.setPassword(hassedPassword);
		user.setVerificationToken(token);
		user.setVerified(false);
		
		userRepo.save(user);
		
		emailService.sendVerificationMail(email, token);
		
	}
	
	public boolean verifyToken(String token)
	{
		Optional<Users> userOpt = userRepo.findVerifiedUserByVerificationToken(token);
		
		if(userOpt.isPresent())
		{
			Users user = userOpt.get();
			if(user.isVerified())
			{
				return false;
			}
			user.setVerified(true);
			user.setVerificationToken(null);
			userRepo.save(user);
			return true;
		}
		return false;
	}
	
	public Optional<Users> getUserByEmail(String email)
	{
		return userRepo.findUserByEmail(email);
	}
}
