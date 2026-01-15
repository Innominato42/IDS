package com.example.demo.Model;

import java.util.ArrayList;

public class InCorsoState implements HackathonState{
    @Override
    public void iscriviTeam(Hackathon hackathon, Team team) {
        throw new IllegalStateException("Errore : non si puo' iscrivere durante questa fase");
    }

    @Override
    public void gestisciValutazione(Hackathon hackathon, Sottomissione sottomissione) {
        throw new IllegalStateException("Errore : non si puo' valutare durante questa fase");
    }


    @Override
    public void gestisciSottomissione(Sottomissione sottomissione,Hackathon hackathon) {
        sottomissione.setHackathon(hackathon);
        if(hackathon.getSottomissioni()==null)
        {
            hackathon.setSottomissioni(new ArrayList<>());
        }
        hackathon.getSottomissioni().add(sottomissione);
    }
    @Override
    public String getNomeStato() {
        return "IN_CORSO";
    }

    @Override
    public void avvia(Hackathon hackathon) {
        throw new IllegalStateException("L hackathon è gia in corso");
    }

    @Override
    public void stop(Hackathon hackathon) {
        hackathon.setStatoCorrente(new InValutazioneState());
        System.out.println("Fase di valutazione iniziata");
    }

    @Override
    public void concluso(Hackathon hackathon) {
        throw new IllegalStateException("Non si puo chiudere l hackathon prima della fase di valutazione");
    }
}
