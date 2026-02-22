package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repositorys.User_repo;

@Service
public class Service_file {
	@Autowired
	User_repo sr;

	public void registration(User s) {
		sr.save(s);
	}

	public User signup(String email, String Password) {
		User us = sr.findByEmail(email).orElseThrow(() -> new RuntimeException("Email not found"));
		if (!us.getPassword().equals(Password)) {
			throw new RuntimeException("Password not found");
		}
		return us;
	}
}
