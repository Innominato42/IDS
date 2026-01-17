package com.example.demo.Service;

import com.example.demo.DTO.VincitoreDTO;
import com.example.demo.Model.*;
import com.example.demo.Repository.HackathonRepository;
import com.example.demo.Repository.SottomissioneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class HackathonService {

    @Autowired
    private HackathonRepository hackathonRepository;

    @Autowired
    private PagamentoService pagamentoService;
    @Autowired
    private SottomissioneRepository sottomissioneRepository;

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

    @Transactional
    public void avviaHackathon(Long hackathonId) {
        Hackathon hackathon =hackathonRepository.findById(hackathonId).orElseThrow(() -> new RuntimeException("Hackathon not found"));
        hackathon.cambiaStato(new InCorsoState());
        hackathonRepository.save(hackathon);
    }

    @Transactional
    public void chiudiHackathonPerValutazione(Long hackathonId) {
        Hackathon hackathon =hackathonRepository.findById(hackathonId).orElseThrow(() -> new RuntimeException("Hackathon not found"));
        hackathon.cambiaStato(new InValutazioneState());
        hackathonRepository.save(hackathon);
    }

    @Transactional
    public void concludiHackathon(Long hackathonId) {
        Hackathon hackathon = hackathonRepository.findById(hackathonId)
                .orElseThrow(() -> new RuntimeException("Hackathon non trovato"));
        hackathon.cambiaStato(new ConclusoState());

        List<Sottomissione> sottomissioni= sottomissioneRepository.findByHackathonId(hackathonId);
        Sottomissione sottomissioneVincente = sottomissioni.stream()
                .filter(s -> s.getPunteggio() != null)
                .max(Comparator.comparing(Sottomissione::getPunteggio))
                .orElse(null);
        if (sottomissioneVincente != null) {
            Team teamVincente = sottomissioneVincente.getTeam();
            hackathon.setVincitore(teamVincente);
            if(hackathon.getPremio() > 0) {
                pagamentoService.effettuaPagamento(teamVincente.getNome(), hackathon.getPremio());
            }

        }
        hackathonRepository.save(hackathon);
    }

    public VincitoreDTO getVincitore(Long hackathonId)
    {
        Hackathon hackathon= hackathonRepository.findById(hackathonId).orElseThrow(() -> new RuntimeException("Hackathon non trovato"));
        if (hackathon.getVincitore() == null) {
            throw new RuntimeException("Il vincitore non è stato ancora proclamato (o nessun voto assegnato).");
        }

        Team t = hackathon.getVincitore();
        Sottomissione s = sottomissioneRepository.findByTeamId(t.getId()).orElse(new Sottomissione());
        VincitoreDTO dto = new VincitoreDTO();
        dto.setNomeHackathon(hackathon.getNome());
        dto.setPremio(hackathon.getPremio());
        dto.setNomeTeam(hackathon.getVincitore().getNome());
        dto.setPunteggio(s.getPunteggio());
        dto.setPremio(hackathon.getPremio());
        dto.setNomeCreatore(t.getCreatore().getUsername());

        return dto;
    }


    public void stopHackathon(Long hackathonId) {
        Hackathon hackathon = hackathonRepository.findById(hackathonId).orElseThrow(() -> new RuntimeException("Hackathon non trovato"));

        hackathon.stop();

        hackathonRepository.save(hackathon);
    }


    public List<Hackathon> getAllHackathon() {
        return hackathonRepository.findAll();
    }
}
