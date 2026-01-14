package com.example.demo.Model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @ToString.Exclude
    private Team team;
}
