package com.example.demo.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrenotaCallDTO {
    private Long teamId;
    private Long mentoreId;
    private LocalDateTime oraInizio;

}
