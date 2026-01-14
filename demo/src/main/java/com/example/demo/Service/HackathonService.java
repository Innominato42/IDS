package com.example.demo.Service;

import com.example.demo.Model.*;
import com.example.demo.Repository.HackathonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HackathonService {

    @Autowired
    private HackathonRepository hackathonRepository;
    /*
    Creo un nuovo Hackathon usando il design pattern Builder
    Con questo metodo riceviamo dati grezzi e li trasformiamo in un oggetto Hackathon completo
     */
    public Hackathon creaHackathon(String nome, String descrizione, LocalDate dataInizio, LocalDate dataFine, int dimensioneTeam, String regolamento, LocalDate scandenza, String luogo, double premio) {
        Hackathon nuovoHackathon =  HackathonBuilder.newBuilder()
                .creaNome(nome)
                .creaDescrizione(descrizione)
                .creaDataInizio(dataInizio)
                .creaDataFine(dataFine)
                .creaDimensioneTeam(dimensioneTeam)
                .creaRegolamento(regolamento)
                .creaLuogo(luogo)
                .creaPremio(premio)
                .creaScadenzaIscrizioni(scandenza)
                .build();
        return hackathonRepository.save(nuovoHackathon);
    }


    @Transactional
    public void iscriviTeam(Long hackathonId, Team team) {
        Hackathon hackathon = hackathonRepository.findById(hackathonId).orElseThrow(() -> new RuntimeException("Hackathon not found"));

        hackathon.iscriviTeam(team);

        hackathonRepository.save(hackathon);

    }

    public void avviaHackathon(Long hackathonId) {
        Hackathon hackathon =hackathonRepository.findById(hackathonId).orElseThrow(() -> new RuntimeException("Hackathon not found"));
        hackathon.cambiaStato(new InCorsoState());
        hackathonRepository.save(hackathon);
    }

    public void chiudiHackathonPerValutazione(Long hackathonId) {
        Hackathon hackathon =hackathonRepository.findById(hackathonId).orElseThrow(() -> new RuntimeException("Hackathon not found"));
        hackathon.cambiaStato(new InValutazioneState());
        hackathonRepository.save(hackathon);
    }

    public void concludiHackathon(Long hackathonId) {
        Hackathon hackathon =hackathonRepository.findById(hackathonId).orElseThrow(() -> new RuntimeException("Hackathon not found"));
        hackathon.cambiaStato(new ConclusoState());
        hackathonRepository.save(hackathon);
    }

    public List<Hackathon> getAllHackathon() {
        return hackathonRepository.findAll();
    }
}
