package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService 
{
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendVerificationMail(String email, String token)
	{
		String subject = "Verification Email";
		String content = "This is an verification email. verify yourself for the further process please click the following link to verify your mail:"
							+ " http://localhost:8080/verify?token="+token;
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(email);
		message.setSubject(subject);
		message.setText(content);
		
		mailSender.send(message);
	}
}
