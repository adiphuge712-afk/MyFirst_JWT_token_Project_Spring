package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.entity.User;
import com.example.demo.principles.UserPrincilpals;
import com.example.demo.principles.adminprinciple;
import com.example.demo.repositorys.Admin_repo;
import com.example.demo.repositorys.User_repo;

@Service
public class Service_file implements UserDetailsService {
	@Autowired
	private User_repo sr;
	@Autowired
	private Admin_repo adminrepo;

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


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User us = sr.findByEmail(username).orElseThrow(() -> new RuntimeException("Email not found"));
			System.out.println("admin username is "+us.getEmail());
			if(us!=null) {
				return new UserPrincilpals(us);
			}
		} catch (RuntimeException e) {
			Admin admin=adminrepo.findByEmail(username);
			System.out.println("admin username is "+admin.getEmail());
			if(admin!=null) {
				return new adminprinciple(admin);
			}
		}
		
		 throw new UsernameNotFoundException("User or Admin with email " + username + " not found");
		
	
	}

	public List<User> viewDetails() {
		
		return sr.findAll();
	}

	public Admin adminsignup(String email, String password) {
		// TODO Auto-generated method stub
		 Admin admin=adminrepo.findByEmail(email);
		 if(!admin.getPassword().equals(password)) {
			 throw new RuntimeException("Password not found");
		 }
		 return admin;
	}

	public List<Admin> viewDetailsadmin() {
		// TODO Auto-generated method stub
		return adminrepo.findAll();
	}

}
