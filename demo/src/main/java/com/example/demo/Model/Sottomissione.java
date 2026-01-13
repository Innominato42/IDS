package com.example.demo.Model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Sottomissione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int punteggio;
    private String giudizio;

    @ManyToOne
    @JoinColumn(name = "hackathon_id")
    private Hackathon hackathon;
}