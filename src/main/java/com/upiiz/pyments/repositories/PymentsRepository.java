package com.upiiz.pyments.repositories;

// import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.upiiz.pyments.entities.Pyments;

@Repository
public interface PymentsRepository extends CrudRepository<Pyments, Long>{

}
