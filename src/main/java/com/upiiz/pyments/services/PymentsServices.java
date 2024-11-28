package com.upiiz.pyments.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upiiz.pyments.entities.Pyments;
import com.upiiz.pyments.repositories.PymentsRepository;

@Service
public class PymentsServices {

    @Autowired
    PymentsRepository pymentsRepository;

    //GET de todos los heroes
    public List<Pyments> getAllPyments(){
        return pymentsRepository.findAll();
    }

    //GET de un heroe
    public Optional<Pyments> getPymentById(Long id){
        return pymentsRepository.findById(id);
    }

    //POST de un heroe
    public Pyments createPyment(Pyments pyment){
        return pymentsRepository.save(pyment);
    }

    //PUT de un heroe
    public Pyments updatePyment(Pyments pyment){
        return pymentsRepository.save(pyment);
    }
    //DELETE de un heroe
    public void deletePyment(Long id){
        pymentsRepository.deleteById(id);
    }

}
