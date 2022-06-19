package com.example.myweb.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.myweb.model.User;
import com.example.myweb.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(Long no) {
		userRepository.deleteById(no);
	}

	@Override
	public User getUserById(Long no) {
		return userRepository.findById(no).get();
	}

	@Override
	public Page<User> findPaginated(int pageno, int pageSize) {
		Pageable pageable = PageRequest.of(pageno-1, pageSize);
		return userRepository.findAll(pageable);
	}
}
