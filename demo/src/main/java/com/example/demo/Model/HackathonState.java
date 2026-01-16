package com.example.demo.Model;

public interface HackathonState {

    void iscriviTeam(Hackathon hackathon, Team team);
    void gestisciValutazione(Hackathon hackathon, Sottomissione sottomissione);
    void gestisciSottomissione(Sottomissione sottomissione, Hackathon hackathon);
    String getNomeStato();
    void avvia(Hackathon hackathon); //da In iscrizione a in corso
    void stop(Hackathon hackathon); //da in corso ad in valutazione
    void concluso(Hackathon hackathon); //da in valutazione a concluso
}
