package com.upiiz.pyments.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upiiz.pyments.dto.PymentDTO;
import com.upiiz.pyments.entities.Pyments;
import com.upiiz.pyments.repositories.PymentsRepository;

@Service
public class PymentsServices {

    @Autowired
    PymentsRepository pymentsRepository;

    public PymentDTO createPyment(PymentDTO pymentDTO) {
        Pyments pyments = convertToEntity(pymentDTO);
        Pyments savedPyment = pymentsRepository.save(pyments);
        return convertToDTO(savedPyment);
    }

    public List<PymentDTO> getAllPyments() {
        List<Pyments> pyments = (List<Pyments>) pymentsRepository.findAll();
        System.out.println("Logs encontrados: " + pyments.size());
        return pyments.stream().map(this::convertToDTO).toList();
    }

    public PymentDTO getPymentById(Long id) {
        Optional<Pyments> pyments = pymentsRepository.findById(id);
        if (pyments.isPresent()) {
            return convertToDTO(pyments.get());
        } else {
            return null;
        }
    }

    public PymentDTO updatePyment(PymentDTO pymentDTO) {
        Pyments pyments = convertToEntity(pymentDTO);
        Pyments updatedPyment = pymentsRepository.save(pyments);
        return convertToDTO(updatedPyment);
    }

    public void deletePyment(Long id) {
        pymentsRepository.deleteById(id);
    }

    // Método para convertir un DTO a una entidad
    private Pyments convertToEntity(PymentDTO pymentDTO) {
        Pyments pyments = new Pyments();
        pyments.setId(pymentDTO.getId());
        pyments.setPaymenDate(pymentDTO.getPaymentDate());
        pyments.setAmount(pymentDTO.getAmount());
        pyments.setCostumerId(pymentDTO.getCostumerId());
        pyments.setId(pymentDTO.getCostumerId());
        return pyments;
    }

    // Método para convertir una entidad a un DTO
    private PymentDTO convertToDTO(Pyments pyments) {
        PymentDTO pymentDTO = new PymentDTO();
        pymentDTO.setId(pyments.getId());
        pymentDTO.setPaymentDate(pyments.getPaymenDate());
        pymentDTO.setAmount(pyments.getAmount());
        pymentDTO.setCostumerId(pyments.getCostumerId());
        pymentDTO.setCostumerId(pyments.getCostumerId());
        return pymentDTO;
    }

}
