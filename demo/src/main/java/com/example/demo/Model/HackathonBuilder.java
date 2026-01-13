package com.example.demo.Model;

import java.time.LocalDate;

public class HackathonBuilder {

    private Hackathon hackathon;

    private HackathonBuilder()
    {
        this.hackathon=new Hackathon();
        this.hackathon.cambiaStato(new InIscrizioneState());
    }

    public static HackathonBuilder newBuilder()
    {
        return  new HackathonBuilder();
    }

    public HackathonBuilder creaNome(String nome)
    {
        this.hackathon.setNome(nome);
        return this;
    }
    public HackathonBuilder creaDescrizione(String descrizione)
    {
        this.hackathon.setDescrizione(descrizione);
        return this;
    }
    public HackathonBuilder creaDataInizio(LocalDate dataInizio)
    {
        this.hackathon.setDataInizio(dataInizio);
        return this;
    }
    public HackathonBuilder creaDataFine(LocalDate dataFine)
    {
        this.hackathon.setDataFine(dataFine);
        return this;
    }
    public HackathonBuilder creaDimensioneTeam(int dimensione)
    {
        this.hackathon.setDimensioneTeam(dimensione);
        return this;
    }
    public HackathonBuilder creaPremio(double premio)
    {
        this.hackathon.setPremio(premio);
        return this;
    }
    public HackathonBuilder creaLuogo(String luogo)
    {
        this.hackathon.setLuogo(luogo);
        return this;
    }


    public HackathonBuilder creaScadenzaIscrizioni(LocalDate scadenzaIscrizioni) {
        this.hackathon.setScadenzaIscrizioni(scadenzaIscrizioni);
        return this;
    }

    public HackathonBuilder creaRegolamento (String regolamento) {
        this.hackathon.setRegolamento(regolamento);
        return this;
    }
    public Hackathon build()
    {
        if (this.hackathon.getNome() == null || this.hackathon.getNome().trim().isEmpty()) {
            throw new IllegalStateException("ERRORE: L'Hackathon deve avere un nome!");
        }
        if (this.hackathon.getDataInizio() != null && this.hackathon.getDataFine() != null) {
            if (this.hackathon.getDataFine().isBefore(this.hackathon.getDataInizio())) {
                throw new IllegalStateException("ERRORE: La data di fine non può essere precedente alla data di inizio!");
            }
        }

        if (this.hackathon.getScadenzaIscrizioni() != null && this.hackathon.getDataInizio() != null) {
            if (this.hackathon.getScadenzaIscrizioni().isAfter(this.hackathon.getDataInizio())) {
                throw new IllegalStateException("ERRORE: Le iscrizioni devono chiudere prima dell'inizio dell'evento!");
            }
        }
        return  this.hackathon;
    }
}
