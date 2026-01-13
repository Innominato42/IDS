package com.example.demo.Model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;


    @ManyToOne
    @JoinColumn(name = "hackathon_id")
    private Hackathon hackathon;
}