package com.example.demo.serviceTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.example.demo.service.EmailService;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest 
{
	@Mock
	private JavaMailSender mailSender;
	
	@InjectMocks
	private EmailService emailService;
	
	@Test
	void sendVerificationMailTest()
	{
		String email = "exampleMail@gmail.com";
		String token = "abcd1234";
		
		emailService.sendVerificationMail(email, token);
		
		ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
		verify(mailSender).send(captor.capture());
		
		SimpleMailMessage sentMessage = captor.getValue();
		assertEquals(email, sentMessage.getTo()[0]);
		assertEquals("Verification Email", sentMessage.getSubject());
		assertTrue(sentMessage.getText().contains(token));
		assertTrue(sentMessage.getText().contains("http://localhost:8080/verify?token="+token));		
	}
}
