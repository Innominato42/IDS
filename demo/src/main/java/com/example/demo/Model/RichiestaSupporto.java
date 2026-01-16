package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class RichiestaSupporto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String messaggio;
    private String rispostaMentore;
    private String stato;
    private LocalDateTime dataRichiesta;

    @ManyToOne
    @JoinColumn(name="team_id", nullable = false)
    private Team team;


    @ManyToOne
    @JoinColumn(name="mentore_id", nullable = false)
    private Utente mentore;
}
