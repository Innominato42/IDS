package com.example.demo.Service;

import com.example.demo.Model.Invito;
import com.example.demo.Model.Team;
import com.example.demo.Model.Utente;
import com.example.demo.Repository.InvitoRepository;
import com.example.demo.Repository.TeamRepository;
import com.example.demo.Repository.UtenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvitoService {

    @Autowired
    private InvitoRepository invitoRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UtenteRepository utenteRepository;


    @Transactional
    public Invito creaInvito(Long teamId, Long utenteDaInvitareId)
    {
        Team team=teamRepository.findById(teamId).orElseThrow(()-> new IllegalArgumentException("Team non trovato"));

        Utente invitato = utenteRepository.findById(utenteDaInvitareId).orElseThrow(()-> new IllegalArgumentException("Utente non trovato"));

        if(team.getMembri().contains(invitato))
        {
            throw new IllegalArgumentException("L'utente fa gia parte del team");
        }

        Invito invito=new Invito();
        invito.setTeam(team);
        invito.setUtenteInvitato(invitato);
        invito.setStato("PENDING");
        invito.setDataInvito(LocalDateTime.now());

        return invitoRepository.save(invito);
    }

    @Transactional
    public Invito gestisciRisposta(Long invitoId, Long utenteId, boolean risposta)
    {
        Invito invito = invitoRepository.findById(invitoId).orElseThrow(()-> new IllegalArgumentException("Invito non trovato"));

        if(!invito.getUtenteInvitato().getId().equals(utenteId))
        {
            throw new SecurityException("L'invito non era per te");
        }
        if(invito.getStato().equals("ACCETTATO")||(invito.getStato().equals("RIFIUTATO")))
        {
            throw new IllegalStateException("Hai gia risposto a questo invito");
        }
        if(risposta)
        {
            Team team = invito.getTeam();
            Utente utente = invito.getUtenteInvitato();
            if(team.getMembri().size() >= 5)
            {
                invito.setStato("FALLITO_TEAM_PIENO");
                invitoRepository.save(invito);
                throw  new IllegalStateException("Il team si è riempito prima che potessi accettare l' invito!!! ");
            }
            team.getMembri().add(utente);
            utente.setTeam(team);
            teamRepository.save(team);
            utenteRepository.save(utente);
            invito.setStato("ACCETTATO");
        }   else {
            invito.setStato("RIFIUTATO");
        }
        return invitoRepository.save(invito);

    }

    public List<Invito> getAllInvitiInAttesa(Long utenteId)
    {
        return invitoRepository.findByUtenteInvitatoIdAndStato(utenteId, "PENDING");
    }
}
