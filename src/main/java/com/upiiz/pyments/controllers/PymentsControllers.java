package com.upiiz.pyments.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upiiz.pyments.entities.CustomResponse;
import com.upiiz.pyments.entities.Pyments;
import com.upiiz.pyments.services.PymentsServices;

import org.springframework.hateoas.Link;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api/v1/pyments")
@Tag(
        name = "pyments"
)
public class PymentsControllers {

    @Autowired
    private PymentsServices pymentsServices;

    @GetMapping
    public ResponseEntity<CustomResponse<List<Pyments>>> getPyments() {
        List<Pyments> pyments;
        Link selfLink = linkTo(methodOn(PymentsControllers.class).getPyments()).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            pyments = pymentsServices.getAllPyments();
            if (!pyments.isEmpty()) {
                return ResponseEntity.ok(new CustomResponse<>(1, "Pyments encontrados", pyments, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "No se encontraron pyments", pyments, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error interno del servidor", null, links));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<Pyments>> getPymentById(@PathVariable Long id) {
        Link selfLink = linkTo(methodOn(PymentsControllers.class).getPymentById(id)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            Optional<Pyments> pyment = pymentsServices.getPymentById(id);
            if (pyment.isPresent()) {
                return ResponseEntity.ok(new CustomResponse<>(1, "Pyment encontrado", pyment.get(), links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Pyment no encontrado", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error interno del servidor", null, links));
        }
    }

    @PostMapping
    public ResponseEntity<CustomResponse<Pyments>> createPyment(@RequestBody Pyments pyments) {
        Link selfLink = linkTo(methodOn(PymentsControllers.class).createPyment(pyments)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            Pyments nuevaCompetencia = pymentsServices.createPyment(pyments);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CustomResponse<>(1, "Pyment creado exitosamente", nuevaCompetencia, links));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al crear un pyment", null, links));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Pyments>> updatePyment(
            @PathVariable Long id, @RequestBody Pyments pyments) {
        Link selfLink = linkTo(methodOn(PymentsControllers.class).updatePyment(id, pyments)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            pyments.setId(id);
            if (pymentsServices.getPymentById(id).isPresent()) {
                Pyments pymentActualizado = pymentsServices.updatePyment(pyments);
                return ResponseEntity.ok(new CustomResponse<>(1, "Pyment actualizado exitosamente", pymentActualizado, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Pyment no encontrado", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al actualizar un pyment", null, links));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deletePyment(@PathVariable Long id) {
        Link selfLink = linkTo(methodOn(PymentsControllers.class).deletePyment(id)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            if (pymentsServices.getPymentById(id).isPresent()) {
                pymentsServices.deletePyment(id);
                return ResponseEntity.ok(new CustomResponse<>(1, "Pyment eliminado exitosamente", null, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "Pyment no encontrado", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al eliminar un pyment", null, links));
        }
    }

}
