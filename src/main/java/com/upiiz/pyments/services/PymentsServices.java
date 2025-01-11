package com.upiiz.pyments.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
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
        System.out.println("Pyments encontrados: " + pyments.size());
        return pyments.stream().map(this::convertToDTO).toList();
    }

    public PymentDTO getPymentById(Long id) {
        Optional<Pyments> pyments = pymentsRepository.findById(id);
        return pyments.map(this::convertToDTO).orElse(null);
    }

    public PymentDTO updatePyment(Long id, PymentDTO pymentDTO) {
        Optional<Pyments> existingPyments = pymentsRepository.findById(id);
        if(existingPyments.isPresent()) {
            Pyments pyments = existingPyments.get();
            pyments.setCostumerId(pymentDTO.getCostumerId());

            Pyments updatedPyments = pymentsRepository.save(pyments);
            return convertToDTO(updatedPyments);
        }
        return null;
    }

    public void deletePyment(Long id) {
        pymentsRepository.deleteById(id);
    }

    // Método para convertir un DTO a una entidad
    private Pyments convertToEntity(PymentDTO pymentDTO) {
        return Pyments.builder()
                .id(pymentDTO.getId())
                .paymenDate(pymentDTO.getPaymentDate())
                .amount(pymentDTO.getAmount())
                .costumerId(pymentDTO.getCostumerId())
                .build();
    }

    // Método para convertir una entidad a un DTO
    private PymentDTO convertToDTO(Pyments pyments) {
        PymentDTO pymentDTO = new PymentDTO();
        pymentDTO.setId(pyments.getId());
        pymentDTO.setPaymentDate(pyments.getPaymenDate());
        pymentDTO.setAmount(pyments.getAmount());
        pymentDTO.setCostumerId(pyments.getCostumerId());
        return pymentDTO;
    }

}
