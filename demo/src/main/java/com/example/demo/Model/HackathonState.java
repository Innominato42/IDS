package com.example.demo.Model;

public interface HackathonState {

    void iscriviTeam(Hackathon hackathon, Team team);
    void gestisciValutazione(Hackathon hackathon, Sottomissione sottomissione);
    void gestisciRegistrazione(Team team);
    void gestisciSottomissione(Sottomissione sottomissione, Hackathon hackathon);
    String getNomeStato();
}
