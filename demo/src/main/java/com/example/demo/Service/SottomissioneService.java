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
    public Sottomissione creaSottomissione(SottomissioneDTO sottomissione, Long utenteId) {

        Team team= teamRepository.findById(sottomissione.getIdTeam()).orElseThrow(()-> new RuntimeException("Team non trovato"));

        Hackathon hackathon=team.getHackathon();

        Sottomissione s = sottomissioneRepository.findByTeamId(team.getId()).orElse(new Sottomissione());

        if(s.getId()==null )
        {
            s.setTeam(team);
            s.setHackathon(hackathon);
        }
        s.setLink(sottomissione.getLink());
        s.setDescription(s.getDescription());
        hackathon.inviaSottomissione(s);

        return sottomissioneRepository.save(s);

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
