package com.upiiz.pyments.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

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
import com.upiiz.pyments.entities.User;
import com.upiiz.pyments.services.UserServices;

import org.springframework.hateoas.Link;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/users")
@Tag(
    name = "users"
)
public class UserControllers {
    @Autowired
    private UserServices userServices;

    @GetMapping
    public ResponseEntity<CustomResponse<List<User>>> getUsers(){
        List<User> users;
        Link selfLink = (Link) linkTo(methodOn(UserControllers.class).getUsers()).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            users = userServices.getAllUsers();
            if (!users.isEmpty()) {
                return ResponseEntity.ok(new CustomResponse<>(1, "Users encontrados", users, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "No se encontraron users", users, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error interno del servidor", null, links));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<User>> getUserById(@PathVariable Long id) {
        Link selfLink = linkTo(methodOn(UserControllers.class).getUserById(id)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            User user = userServices.getUserById(id);
            if (user != null) {
                return ResponseEntity.ok(new CustomResponse<>(1, "User encontrado", user, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "User no encontrado", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error interno del servidor", null, links));
        }
    }

    @PostMapping
    public ResponseEntity<CustomResponse<User>> createUser(@RequestBody User user) {
        Link selfLink = linkTo(methodOn(UserControllers.class).createUser(user)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            User nuevaCompetencia = userServices.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CustomResponse<>(1, "USer creado exitosamente", nuevaCompetencia, links));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al crear un user", null, links));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<User>> updateUser(
            @PathVariable Long id, @RequestBody User user) {
        Link selfLink = linkTo(methodOn(UserControllers.class).updateUser(id, user)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            user.setId(id);
            if (userServices.getUserById(id) != null) {
                User userActualizado = userServices.updateUser(user);
                return ResponseEntity.ok(new CustomResponse<>(1, "User actualizado exitosamente", userActualizado, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "User no encontrado", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al actualizar un user", null, links));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteUser(@PathVariable Long id) {
        Link selfLink = linkTo(methodOn(UserControllers.class).deleteUser(id)).withSelfRel();
        List<Link> links = List.of(selfLink);

        try {
            if (userServices.getUserById(id) != null) {
                userServices.deleteUser(id);
                return ResponseEntity.ok(new CustomResponse<>(1, "User eliminado exitosamente", null, links));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new CustomResponse<>(0, "User no encontrado", null, links));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomResponse<>(0, "Error al eliminar un user", null, links));
        }
    }

}
