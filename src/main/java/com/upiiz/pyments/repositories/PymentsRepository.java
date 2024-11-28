package com.upiiz.pyments.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upiiz.pyments.entities.Pyments;

public interface PymentsRepository extends JpaRepository<Pyments, Long>{

}
