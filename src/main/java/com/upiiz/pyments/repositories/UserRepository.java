package com.upiiz.pyments.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upiiz.pyments.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
