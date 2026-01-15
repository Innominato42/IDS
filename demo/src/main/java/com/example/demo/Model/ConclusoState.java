package com.example.demo.Model;

public class ConclusoState implements HackathonState{
    @Override
    public void iscriviTeam(Hackathon hackathon, Team team) {
        throw new IllegalStateException("Errore : non si puo' iscrivere durante questa fase");
    }

    @Override
    public void gestisciValutazione(Hackathon hackathon, Sottomissione sottomissione) {
        throw new IllegalStateException("Errore : non si puo' valutare durante questa fase");
    }


    @Override
    public void gestisciSottomissione(Sottomissione sottomissione, Hackathon hackathon) {
        throw new IllegalStateException("Errore : non si puo' valutare durante questa fase");
    }
    @Override
    public String getNomeStato() {
        return "CONCLUSO";
    }

    @Override
    public void avvia(Hackathon hackathon) {
        throw new IllegalStateException("L' hackathon è gia terminato");
    }

    @Override
    public void stop(Hackathon hackathon) {
        throw new IllegalStateException("L' hackathon è gia terminato");
    }

    @Override
    public void concluso(Hackathon hackathon) {
        throw new IllegalStateException("L' hackathon è gia terminato");
    }
}
