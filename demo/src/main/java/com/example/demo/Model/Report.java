package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String motivo;

    private LocalDateTime dataSegnalazione;

    @ManyToOne
    @JoinColumn(name = "team_segnalato_id", nullable = false)
    private Team teamSegnalato;

    @ManyToOne
    @JoinColumn(name= "autore_id", nullable = false)
    private Utente autore;

    @ManyToOne
    @JoinColumn(name = "hackathon_id" , nullable = false)
    private Hackathon hackathon;
}
