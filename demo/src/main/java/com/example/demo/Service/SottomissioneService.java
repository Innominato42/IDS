package com.example.demo.Service;

import com.example.demo.Model.Hackathon;
import com.example.demo.Model.Sottomissione;
import com.example.demo.Model.Team;
import com.example.demo.Repository.HackathonRepository;
import com.example.demo.Repository.SottomissioneRepository;
import com.example.demo.Repository.TeamRepository;
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

    @Transactional
    public Sottomissione creaSottomissione(Long hackathonId, Long teamId, String link, String descrizione) {

        Hackathon hackathon = hackathonRepository.findById(hackathonId).orElseThrow(() -> new RuntimeException("Hackathon non trovato"));

        Team team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Team non trovato"));
        //Controllo che il team sia davvero iscritto all' hackathon
        if(team.getHackathon()==null || !team.getHackathon().getId().equals(hackathonId)) {
            throw new IllegalArgumentException("Errore : Il team non e' iscritto al hackathon");
        }

        Sottomissione nuovaSottomissione =new Sottomissione();
        nuovaSottomissione.setLink(link);
        nuovaSottomissione.setDescription(descrizione);

        nuovaSottomissione.setTeam(team);
        nuovaSottomissione.setHackathon(hackathon);

        return sottomissioneRepository.save(nuovaSottomissione);

    }
}
