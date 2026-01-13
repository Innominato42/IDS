package com.example.demo.Model;

public class InIscrizioneState implements  HackathonState{
    @Override
    public void iscriviTeam(Hackathon hackathon, Team team) {
        hackathon.getTeamIscritti().add(team);
        team.setHackathon(hackathon);

    }

    @Override
    public void gestisciValutazione(Hackathon hackathon, Sottomissione sottomissione) {
        throw new IllegalStateException("Errore : non si puo' valutare durante questa fase");
    }

    @Override
    public void gestisciRegistrazione(Team team) {

    }

    @Override
    public void gestisciSottomissione(Sottomissione sottomissione,Hackathon hackathon) {
        throw new IllegalStateException("Errore : non si puo' valutare durante questa fase");
    }
    @Override
    public String getNomeStato() {
        return "IN_ISCRIZIONE";
    }
}
