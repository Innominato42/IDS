package com.example.demo.Model;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Hackathon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descrizione;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private int dimensioneTeam;
    private double premio;
    private String luogo;
    private String regolamento;
    private LocalDate scadenzaIscrizioni;

    //stato da salvare nel db
    private String statoString;

    //Team iscritti all hackathon
    @OneToMany(mappedBy = "hackathon", cascade = CascadeType.ALL)
    private List<Team> teamIscritti = new ArrayList<>();

    @OneToMany(mappedBy = "hackathon", cascade = CascadeType.ALL)
    private List<Sottomissione> sottomissioni =new ArrayList<>();

    @Transient
    private HackathonState statoCorrente;

    @ManyToOne
    @JoinColumn(name="giudice_id")
    private Utente giudice;

    @OneToOne
    @JoinColumn(name= "vincitore_id")
    private Team vincitore;

    private void inizializzaStato()
    {
        if("IN_ISCRIZIONE".equals(statoString))
        {
            this.statoCorrente = new InIscrizioneState();
        } else if("IN_CORSO".equals(statoString))
        {
            this.statoCorrente = new InCorsoState();
        } else if ("IN_VALUTAZIONE".equals(statoString))
        {
            this.statoCorrente = new InIscrizioneState();
        } else if("CONCLUSO".equals(statoString))
        {
            this.statoCorrente = new ConclusoState();
        }
        else {
            this.statoCorrente= new InIscrizioneState();
            this.statoString= "IN_ISCRIZIONE";
        }
    }
    public void cambiaStato(HackathonState nuovoStato)
    {
        this.statoCorrente= nuovoStato;
        this.statoString = nuovoStato.getNomeStato();
    }
    public void iscriviTeam(Team team)
    {
        if(statoCorrente==null)
        {
            inizializzaStato();
        }
        this.statoCorrente.iscriviTeam(this,team);
    }

    public void stop()
    {
        this.statoCorrente.stop(this);
    }

    public void gestisciSottomissione(Sottomissione sottomissione)
    {
        if(statoCorrente==null)
        {
            inizializzaStato();
        }
        this.statoCorrente.gestisciSottomissione(sottomissione,this);
    }
    public void gestisciValutazione(Sottomissione sottomissione)
    {
        if(statoCorrente==null)
        {
            inizializzaStato();
        }
        this.statoCorrente.gestisciValutazione(this, sottomissione);
    }

    public void inviaSottomissione(Sottomissione sottomissione)
    {
        this.statoCorrente.gestisciSottomissione(sottomissione,this);
    }



}
