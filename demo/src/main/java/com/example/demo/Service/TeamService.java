package com.example.demo.Service;

import com.example.demo.DTO.TeamDTO;
import com.example.demo.Model.Hackathon;
import com.example.demo.Model.Team;
import com.example.demo.Repository.HackathonRepository;
import com.example.demo.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private HackathonRepository hackathonRepository;


    public Team iscriviTeam(Long hackathonId, TeamDTO  teamDTO) {
        Hackathon hackathon = hackathonRepository.findById(hackathonId).orElseThrow(() -> new RuntimeException("Hackathon non trovato"));

        if(teamRepository.existsByNomeAndHackathonId(teamDTO.getNome(), hackathonId)){
            throw new IllegalArgumentException("Esiste gia un team con questo nome");
        }

        Team nuovoTeam = new Team();
        nuovoTeam.setNome(teamDTO.getNome());

        hackathon.iscriviTeam(nuovoTeam);


        return teamRepository.save(nuovoTeam);
    }

    public void aggiungiMembro(Long teamId, Long utenteId)
    {

    }

    public List<Team> findTeamPerHackathon(Long hackathonId) {
        return teamRepository.findByHackathonId(hackathonId);
    }

    public Team findById(Long teamId) {
        return teamRepository.findById(teamId).orElseThrow(() -> new RuntimeException("Team non trovato"));
    }
}
