package com.example.demo.integrationTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest 
{
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserRepository repo;
	
	@MockBean
	private UserService service;
	
	@BeforeEach
	public void setUp()
	{
		repo.deleteAll();
	}
	
	@Test
	public void UserRegistrationEndPointTest() throws Exception
	{
		mockMvc.perform(post("/register").param("email", "testEmail@mail.com").param("password", "Password123"))
		.andExpect(status().isOk())
		.andExpect(content().string("Email registered successfully check your email to verify yourself"));
	}
	
	@Test
	public void VerifingUserTest() throws Exception
	{
		mockMvc.perform(get("/verify").param("token", "uuid-abcd-efgh-ijkl"))
		.andExpect(status().isBadRequest())
		.andExpect(content().string("Invalid or expired token try again"));
	}
	
	@Test
	public void GetTheRegisteredUsers() throws Exception
	{
		UUID userId = UUID.randomUUID();
		Users mockUser = new Users();
		
		mockUser.setEmail("testMail@mail.com");
		mockUser.setUserId(userId);
		mockUser.setPassword("Password123");
		mockUser.setVerified(true);
		mockUser.setVerificationToken("verify-123");
		
		Mockito.when(service.getUserByEmail("testMail@mail.com")).thenReturn(Optional.of(mockUser));
		
		mockMvc.perform(get("/getUserByEmail/testMail@mail.com"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.userId").value(userId.toString()))
		.andExpect(jsonPath("$.email").value("testMail@mail.com"))
		.andExpect(jsonPath("$.verified").value(true))
		.andExpect(jsonPath("$.verificationToken").value("verify-123"));
	}
}
