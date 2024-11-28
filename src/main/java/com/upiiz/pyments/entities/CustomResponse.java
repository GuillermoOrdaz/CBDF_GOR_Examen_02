package com.upiiz.pyments.entities;

import java.util.List;

import org.springframework.hateoas.Link;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponse<T> {
    private int estado;
    private String msg;
    private T data;
    private List<Link> links;
}
