package com.example.myweb.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.myweb.model.User;

public interface UserService {
	//전체 가져오기
	List<User> getAllUser();
	
	//저장하기
	User saveUser(User user);
	
	//수정하기
	User updateUser(User user);
	
	//삭제하기
	void deleteUser(Long no);
	
	//한줄만 가져오기
	User getUserById(Long no);
	
	Page<User> findPaginated(int pageno, int pageSize);
} 