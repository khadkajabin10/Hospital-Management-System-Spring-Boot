package com.example.demo.Repository;

import com.example.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByusername(String username);//like receiveing userRepository.findByusername(ram) . now it goes to database and bring it
//SELECT * FROM user WHERE username = ?; this above do this
}