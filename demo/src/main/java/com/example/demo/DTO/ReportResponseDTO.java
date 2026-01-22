package com.example.demo.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportResponseDTO {

    private Long id;
    private String motivo;
    private LocalDateTime dataSegnalazione;
    private String nomeAutore;
    private String nomeTeamSegnalato;
}
