package com.example.demo.Model;

public class InValutazioneState implements HackathonState{
    @Override
    public void iscriviTeam(Hackathon hackathon, Team team) {
        throw new IllegalStateException("Errore : non si puo' iscrivere durante questa fase");
    }

    @Override
    public void gestisciValutazione(Hackathon hackathon, Sottomissione sottomissione) {

    }

    @Override
    public void gestisciSottomissione(Sottomissione sottomissione,Hackathon hackathon) {
        throw new IllegalStateException("Errore : non si puo' valutare durante questa fase");
    }
    @Override
    public String getNomeStato() {
        return "IN_VALUTAZIONE";
    }

    @Override
    public void avvia(Hackathon hackathon) {
        throw new IllegalStateException("Errore : non si puo' avviare durante questa fase");
    }

    @Override
    public void stop(Hackathon hackathon) {
        throw new IllegalStateException("Errore : L'hackathon e' gia in attesa di valutazione");
    }

    @Override
    public void concluso(Hackathon hackathon) {
        hackathon.cambiaStato(new ConclusoState());
        System.out.println("Hackathon concluso. Il vincitore e' stato decretato.");
    }
}
