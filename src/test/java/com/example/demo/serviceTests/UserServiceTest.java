package com.example.demo.serviceTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EmailService;
import com.example.demo.service.UserService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest 
{
	@InjectMocks
	private UserService userService;
	
	@Mock
	private EmailService emailService;
	
	@Mock
	private JavaMailSender mailSender;
	
	@Mock
	private UserRepository repo;
	
	@Mock
	private BCryptPasswordEncoder passwordencoder;
	
	@Test
	void passwordEncryptionTest()
	{
		String email = "exampleMail@gmail.com";
		String rawPassword = "Password@123";
		
		userService.registerUser(email, rawPassword);
		
		ArgumentCaptor<Users> usersCaptor = ArgumentCaptor.forClass(Users.class);
		
		verify(repo).save(usersCaptor.capture());
		Users savedUser = usersCaptor.getValue();
		
		assertNotEquals(rawPassword, savedUser.getPassword());	
	}
	
	@Test
	void registerUserTest()
	{
		String email = "exampleMail@gmail.com";
		String rawPassword = "password@123";
		String VerificationToken = "abc123";
		
		userService.registerUser(email, rawPassword);
		
		emailService.sendVerificationMail(email, VerificationToken);
		
		ArgumentCaptor<Users> userCaptor = ArgumentCaptor.forClass(Users.class);
		
		verify(repo).save(userCaptor.capture());
		Users registeredUser = userCaptor.getValue();
		
		assertEquals(email, registeredUser.getEmail());
		assertNotEquals(rawPassword, registeredUser.getPassword());
		assertNotNull(VerificationToken, registeredUser.getVerificationToken());
		assertFalse(registeredUser.isVerified());
	}
	
	@Test
	void verifingUserWithTokenTest()
	{
		String token = "abc123";
		
		Users mockUser = new Users();
		mockUser.setVerified(false);
		mockUser.setVerificationToken(token);
		
		when(repo.findVerifiedUserByVerificationToken(token)).thenReturn(Optional.of(mockUser));
		
		boolean result = userService.verifyToken(token);
		
		assertTrue(result);
		assertTrue(mockUser.isVerified());
		assertNull(mockUser.getVerificationToken());
		verify(repo).save(mockUser);
	}
	
	@Test
	void shouldReturnInvalidTokenIfAlreadyVerifiedTest()
	{
		String token = "invalid-token";
		
		Users mockUser = new Users();
		mockUser.setVerified(true);
		
		when(repo.findVerifiedUserByVerificationToken(token)).thenReturn(Optional.of(mockUser));
		
		boolean result = userService.verifyToken(token);
		
		assertFalse(result);
		verify(repo, never()).save(mockUser);
	}
	
	@Test
	void getTheRegisteredUsers()
	{
		String email = "exampleMail@gmail.com";
		Users mockUser = new Users();
		mockUser.setEmail(email);
		
		when(userService.getUserByEmail(email)).thenReturn(Optional.of(mockUser));
		
		Optional<Users> result = userService.getUserByEmail(email);
		
		assertTrue(result.isPresent());
		assertEquals(email, result.get().getEmail());
		verify(repo).findUserByEmail(email);
	}
}
