package com.example.demo.Service;

import com.example.demo.DTO.SottomissioneDTO;
import com.example.demo.DTO.ValutazioneDTO;
import com.example.demo.Model.Hackathon;
import com.example.demo.Model.Sottomissione;
import com.example.demo.Model.Team;
import com.example.demo.Model.Utente;
import com.example.demo.Repository.HackathonRepository;
import com.example.demo.Repository.SottomissioneRepository;
import com.example.demo.Repository.TeamRepository;
import com.example.demo.Repository.UtenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SottomissioneService {

    @Autowired
    private HackathonRepository hackathonRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private SottomissioneRepository sottomissioneRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Transactional
    public Sottomissione creaSottomissione(SottomissioneDTO sottomissioneDTO, Long utenteId) {

        Team team= teamRepository.findById(sottomissioneDTO.getIdTeam()).orElseThrow(()-> new RuntimeException("Team non trovato"));

        boolean isMembro = team.getMembri().stream()
                .anyMatch(membro -> membro.getId().equals(utenteId));

        if (!isMembro) {
            throw new RuntimeException("ERRORE: Devi far parte del team per poter inviare il progetto!");
        }
        Hackathon hackathon=team.getHackathon();
        if(hackathon==null)
        {
            throw new RuntimeException("Il team non è iscritto a nessun hackathon");
        }

        /*if(hackathon.getStatoCorrente().toString().equalsIgnoreCase("IN_CORSO"))
        {
            throw new RuntimeException("Non puoi più aggiornare o creare sottomissioni ");
        }*/

        Optional<Sottomissione> sottomissioneEsistente = sottomissioneRepository.findByTeamId(team.getId());
        Sottomissione sottomissione;

        //Controllo se il team ha gia fatto una sottomissione
        if(sottomissioneEsistente.isPresent()){
            sottomissione=sottomissioneEsistente.get();
            sottomissione.setLink(sottomissioneDTO.getLink());
            sottomissione.setDescription(sottomissioneDTO.getDescrizione());
        }
        //se il team non ha mai fatto una sottomissione la creo
        else {
            sottomissione = new Sottomissione();
            sottomissione.setLink(sottomissioneDTO.getLink());
            sottomissione.setDescription(sottomissioneDTO.getDescrizione());
            sottomissione.setTeam(team);
            sottomissione.setHackathon(hackathon);
        }

        hackathon.gestisciSottomissione(sottomissione);

        return sottomissioneRepository.save(sottomissione);

    }

    @Transactional
    public Sottomissione valutaSottomissione(ValutazioneDTO valutazione) {

        Sottomissione sottomissione = sottomissioneRepository.findById(valutazione.getSottomissioneId()).orElseThrow(()->new RuntimeException("Sottomissione non trovata"));
        Hackathon hackathon=sottomissione.getHackathon();
        Utente giudice = hackathon.getGiudice();

        if(giudice==null)
        {
            throw new IllegalStateException("Questo hackathon non ha ancora un giudice");
        }
        if(!"GIUDICE".equals(giudice.getRuolo().toString()))
        {
            throw new IllegalArgumentException("L'utente non è un giudice");
        }
        hackathon.gestisciValutazione(sottomissione);
        sottomissione.setPunteggio(valutazione.getPunteggio());
        sottomissione.setGiudice(giudice);
        sottomissione.setGiudizio(valutazione.getGiudizio());

        return sottomissioneRepository.save(sottomissione);
    }

    public Sottomissione getSottomissionePerTeam(Long teamId) {
        return sottomissioneRepository.findByTeamId(teamId)
                .orElseThrow(() -> new RuntimeException("Nessuna sottomissione trovata per questo team"));
    }
}
