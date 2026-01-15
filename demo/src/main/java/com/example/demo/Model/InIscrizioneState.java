package com.example.demo.Model;

public class InIscrizioneState implements  HackathonState{
    @Override
    public void iscriviTeam(Hackathon hackathon, Team team) {
        hackathon.getTeamIscritti().add(team);
        team.setHackathon(hackathon);
        System.out.println("Nuovo team iscritto: " + team.getNome());
    }

    @Override
    public void gestisciValutazione(Hackathon hackathon, Sottomissione sottomissione) {
        throw new IllegalStateException("Errore : non si puo' valutare durante questa fase");
    }


    @Override
    public void gestisciSottomissione(Sottomissione sottomissione,Hackathon hackathon) {
        throw new IllegalStateException("Errore : non si possono fare sottomissioni durante questa fase");
    }
    @Override
    public String getNomeStato() {
        return "IN_ISCRIZIONE";
    }

    @Override
    public void avvia(Hackathon hackathon) {
        hackathon.setStatoCorrente(new InCorsoState());
    }

    @Override
    public void stop(Hackathon hackathon) {
        throw new IllegalStateException("Errore ");

    }

    @Override
    public void concluso(Hackathon hackathon) {
        throw new IllegalStateException("Errore: l'hackathon e' ancora in corso");

    }


}
