package com.example.demo.entity;

import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Users 
{
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID userId;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	private boolean verified;
	private String verificationToken;
	
	public Users(UUID userId, String email, String password, boolean verified, String verificationToken) 
	{
		super();
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.verified = verified;
		this.verificationToken = verificationToken;
	}

	public Users() 
	{
		super();
	}

	public UUID getUserId() 
	{
		return userId;
	}

	public void setUserId(UUID userId) 
	{
		this.userId = userId;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}

	public boolean isVerified() 
	{
		return verified;
	}

	public void setVerified(boolean verified)
	{
		this.verified = verified;
	}

	public String getVerificationToken() 
	{
		return verificationToken;
	}

	public void setVerificationToken(String verificationToken)
	{
		this.verificationToken = verificationToken;
	}
}
