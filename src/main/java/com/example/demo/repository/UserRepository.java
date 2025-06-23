package com.example.demo.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID>
{
	Optional<Users> findUserByEmail(String email);
	Optional<Users> findVerifiedUserByVerificationToken(String token);
}
