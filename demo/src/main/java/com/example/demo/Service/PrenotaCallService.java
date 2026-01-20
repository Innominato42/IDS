package com.example.demo.Service;

import com.example.demo.DTO.PrenotaCallDTO;
import com.example.demo.Model.PrenotaCall;
import com.example.demo.Model.Ruolo;
import com.example.demo.Model.Team;
import com.example.demo.Model.Utente;
import com.example.demo.Repository.PrenotaCallRepository;
import com.example.demo.Repository.TeamRepository;
import com.example.demo.Repository.UtenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrenotaCallService {

    @Autowired
    private PrenotaCallRepository prenotaCallRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UtenteRepository utenteRepository;


    @Transactional
    public PrenotaCall prenotaCall(PrenotaCallDTO prenotaCallDTO) {

        Team team = teamRepository.findById(prenotaCallDTO.getTeamId()).orElseThrow(() -> new RuntimeException("Team non trovato"));

        Utente mentore = utenteRepository.findById(prenotaCallDTO.getMentoreId()).orElseThrow(() -> new RuntimeException("Utente non trovato"));

        if(mentore.getRuolo() != Ruolo.MENTORE){
            throw new IllegalArgumentException("Puoi prenotare call solo con un mentore");
        }

        if(!"IN_CORSO".equals(team.getHackathon().getStatoString())){
            throw new IllegalStateException("Le call possono essere prenotate solo quando l hackathon è in corso");
        }

        PrenotaCall call= new PrenotaCall();
        call.setMentore(mentore);
        call.setTeam(team);
        call.setOraInizio(prenotaCallDTO.getOraInizio());
        call.setStato("PENDING");
        return prenotaCallRepository.save(call);
    }

    @Transactional
    public PrenotaCall rispondiCall(Long callId, boolean risposta,String link)
    {
        PrenotaCall call= prenotaCallRepository.findById(callId).orElseThrow(() -> new RuntimeException("Call non trovato"));
        if(risposta){
            call.setStato("ACCETTATA");
            call.setLinkMeeting(link);
        }else {
            call.setStato("RIFIUTATA");
        }
        return prenotaCallRepository.save(call);
    }


}
