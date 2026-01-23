package com.example.demo.Service;

import com.example.demo.DTO.TeamDTO;
import com.example.demo.Model.Hackathon;
import com.example.demo.Model.Team;
import com.example.demo.Model.Utente;
import com.example.demo.Repository.HackathonRepository;
import com.example.demo.Repository.TeamRepository;
import com.example.demo.Repository.UtenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private HackathonRepository hackathonRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Transactional
    public Team iscriviTeam(TeamDTO  teamDTO) {
        Hackathon hackathon = hackathonRepository.findById(teamDTO.getHackathonId()).orElseThrow(() -> new RuntimeException("Hackathon non trovato"));

        Utente creatore= utenteRepository.findById(teamDTO.getCreatoreId()).orElseThrow(() -> new RuntimeException("Utente non trovato"));

        if(creatore.getTeam()!=null)
        {
            throw new IllegalArgumentException("Errore l'utente " +creatore.getUsername()+"fa gia parte di un team");
        }
        if (teamRepository.existsByNomeAndHackathonId(teamDTO.getNome(), teamDTO.getHackathonId())) {
            throw new IllegalArgumentException("ERRORE: Esiste già un team chiamato '" + teamDTO.getNome() + "' in questo Hackathon.");
        }

        Team nuovoTeam = new Team();
        nuovoTeam.setNome(teamDTO.getNome());
        nuovoTeam.setCreatore(creatore);
        nuovoTeam.addMembro(creatore);
        hackathon.iscriviTeam(nuovoTeam);
        Team teamSalvato =teamRepository.save(nuovoTeam);
        utenteRepository.save(creatore);

        return teamSalvato;
    }

    @Transactional
    public void aggiungiMembro(Long teamId, Long utenteId)
    {

        Team team = teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Team non trovato"));

        Utente utente= utenteRepository.findById(utenteId).orElseThrow(() -> new RuntimeException("utente non trovato"));

        if(utente.getTeam() !=null)
        {
            throw new IllegalArgumentException("L'utente è gia iscritto ad un team");
        }

        int maxMembri = team.getHackathon().getDimensioneTeam();
        if(team.getMembri().size() >= maxMembri)
        {
            throw new IllegalStateException("Il team è gia al completo!");
        }

        team.addMembro(utente);

        utenteRepository.save(utente);


    }

    public List<Team> trovaTeamPerHackathon(Long hackathonId) {
        return teamRepository.findByHackathonId(hackathonId);
    }


    public Team findById(Long teamId) {
        return teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Team non trovato"));
    }
}
