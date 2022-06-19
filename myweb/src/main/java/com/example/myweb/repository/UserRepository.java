package com.example.myweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myweb.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
}
