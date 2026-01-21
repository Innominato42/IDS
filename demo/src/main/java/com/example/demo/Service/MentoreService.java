package com.example.demo.Service;

import com.example.demo.DTO.RichiestaSupportoDTO;
import com.example.demo.Model.Hackathon;
import com.example.demo.Model.RichiestaSupporto;
import com.example.demo.Model.Team;
import com.example.demo.Model.Utente;
import com.example.demo.Repository.RichiestaSupportoRepository;
import com.example.demo.Repository.TeamRepository;
import com.example.demo.Repository.UtenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MentoreService {

    @Autowired
    private RichiestaSupportoRepository richiestaSupportoRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UtenteRepository utenteRepository;


    @Transactional
    public RichiestaSupporto creaRichiesta(RichiestaSupportoDTO richiesta)
    {
        Team team= teamRepository.findById(richiesta.getTeamId()).orElseThrow(()-> new RuntimeException("Team non trovato"));

        Utente mentore = utenteRepository.findById(richiesta.getMentoreId()).orElseThrow(()-> new RuntimeException("Utente non trovato"));

        if(!"MENTORE".equalsIgnoreCase(mentore.getRuolo().toString()))
        {
            throw new IllegalArgumentException("L'utente selezionato non è un mentore");
        }

        Hackathon hackathon = team.getHackathon();
        if(!"IN_CORSO".equals(hackathon.getStatoString()))
        {
            throw new IllegalStateException("Si puo chiedere aiuto solo quando l' hackathon è nella fase IN CORSO");
        }

        RichiestaSupporto richiestaSupporto= new RichiestaSupporto();
        richiestaSupporto.setTeam(team);
        richiestaSupporto.setMentore(mentore);
        richiestaSupporto.setMessaggio(richiesta.getMessaggio());
        richiestaSupporto.setDataRichiesta(LocalDateTime.now());
        richiestaSupporto.setStato("PENDING");

        return richiestaSupportoRepository.save(richiestaSupporto);
    }

    public RichiestaSupporto rispondiRichiesta(Long richiestaId,String risposta,String nuovoStato)
    {
        RichiestaSupporto richiestaSupporto= richiestaSupportoRepository.findById(richiestaId).orElseThrow(()->new RuntimeException("Richiesta di supporto non trovata"));
        richiestaSupporto.setRispostaMentore(risposta);
        richiestaSupporto.setStato(nuovoStato);
        return richiestaSupportoRepository.save(richiestaSupporto);
    }

    public List<RichiestaSupporto> getRichiestaPerMentore(Long mentoreId)
    {
        return richiestaSupportoRepository.findByMentoreId(mentoreId);
    }

    public List<RichiestaSupporto> getRichiesteMentore(Long mentoreId)
    {
        return richiestaSupportoRepository.findByMentoreId(mentoreId);
    }
    public List<RichiestaSupporto> getRichiesteDelTeam(Long teamId){
        return richiestaSupportoRepository.findByTeamId(teamId);
    }

    public List<RichiestaSupporto> getAllRichiesteHackathon(Long hackathonId){
        return richiestaSupportoRepository.findByTeam_Hackathon_id(hackathonId);
    }
}
