package com.upiiz.pyments.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upiiz.pyments.dto.PymentDTO;
import com.upiiz.pyments.services.PymentsServices;

import io.swagger.v3.oas.annotations.tags.Tag;



@RestController
@RequestMapping("/api/v1/pyments")
@Tag(
        name = "pyments"
)
public class PymentsControllers {

    @Autowired
    private PymentsServices pymentsServices;

    @GetMapping
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<List<PymentDTO>> getAllPyments() {
        return ResponseEntity.ok(pymentsServices.getAllPyments());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<PymentDTO> getPymentById(@PathVariable Long id) {
        return ResponseEntity.ok(pymentsServices.getPymentById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE')")
    public ResponseEntity<PymentDTO> createPyment(@RequestBody PymentDTO pymentDTO) {
        return ResponseEntity.ok(pymentsServices.createPyment(pymentDTO));
    }

    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE')")
    public ResponseEntity<PymentDTO> updatePyment(@RequestBody PymentDTO pymentDTO) {
        return ResponseEntity.ok(pymentsServices.updatePyment(pymentDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE')")
    public ResponseEntity<Void> deletePyment(@PathVariable Long id) {
        pymentsServices.deletePyment(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
