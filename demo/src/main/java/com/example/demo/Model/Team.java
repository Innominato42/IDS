package com.example.demo.Model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToOne
    @JoinColumn(name = "creatore_id")
    private Utente creatore;

    @OneToMany(mappedBy = "team", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    private List<Utente> membri = new ArrayList<>();

    @OneToOne(mappedBy = "team")
    private Sottomissione sottomissione;

    @ManyToOne
    @JoinColumn(name = "hackathon_id")
    private Hackathon hackathon;


    public void addMembro(Utente utente) {
        membri.add(utente);
        utente.setTeam(this);
    }
}