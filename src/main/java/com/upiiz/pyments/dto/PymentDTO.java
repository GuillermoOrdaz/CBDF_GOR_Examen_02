package com.upiiz.pyments.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class PymentDTO {
    private Long id;
    private LocalDate paymentDate;
    private BigDecimal amount;
    private Long costumerId;
}
