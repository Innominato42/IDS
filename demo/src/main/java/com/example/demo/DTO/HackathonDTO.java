package com.example.demo.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HackathonDTO {

    private String nome;
    private String descrizione;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private LocalDate scadenzaIscrizioni;
    private int dimensioneTeam;
    private double premio;
    private String luogo;
    private String regolamento;

}
