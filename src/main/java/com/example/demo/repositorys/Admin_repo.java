package com.example.demo.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Admin;
@Repository
public interface Admin_repo extends JpaRepository<Admin,Long> {
	Admin findByEmail(String email);
}
