package com.example.demo.Model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class Sottomissione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Hackathon a cui appartiene la sottomissione
    @ManyToOne
    @JoinColumn(name = "hackathon_id")
    private Hackathon hackathon;

    //team che ha creato la sottomissione
    @OneToOne
    @JoinColumn(name="team_id")
    private Team team;

    //link del progetto nella sottomissione
    private String link;
    private String description;

    //campi per la valutazione
    private int punteggio;
    @Column(length = 1000)
    private String giudizio;

    @ManyToOne
    @JoinColumn(name="giudice_id")
    private Utente giudice;

}