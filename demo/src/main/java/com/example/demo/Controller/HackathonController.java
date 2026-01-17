package com.example.demo.Controller;

import com.example.demo.DTO.HackathonDTO;
import com.example.demo.DTO.TeamDTO;
import com.example.demo.DTO.VincitoreDTO;
import com.example.demo.Model.Hackathon;
import com.example.demo.Model.Team;
import com.example.demo.Service.HackathonService;
import com.example.demo.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hackathon")
public class HackathonController {
    @Autowired
    private HackathonService hackathonService;

    @Autowired
    private TeamService teamService;

    /*
        Crea un nuovo Hackathon
        Riceve un HackathonDTO e lo spacchetta fornendo i dati al service
     */
    @PostMapping
    public ResponseEntity<Hackathon> creaHackathon(@RequestBody HackathonDTO hackathonDTO) {
        Hackathon hackathon = hackathonService.creaHackathon(
                hackathonDTO.getNome(),
                hackathonDTO.getDescrizione(),
                hackathonDTO.getDataInizio(),
                hackathonDTO.getDataFine(),
                hackathonDTO.getDimensioneTeam(),
                hackathonDTO.getRegolamento(),
                hackathonDTO.getScadenzaIscrizioni(),
                hackathonDTO.getLuogo(),
                hackathonDTO.getPremio()
        );
        return ResponseEntity.ok(hackathon);
    }

    /*
        Iscrive un team all hackathon
        Gli errori vengono gestiti con un try-catch. Se il PatternState lancia un' eccezione viene gestita con un messaggio di errore, anziche un crash del server
     */
    @PostMapping("/{id}/iscrivi")
    public ResponseEntity<String> iscriviTeam(@PathVariable Long id, @RequestBody TeamDTO teamDTO)
    {
        try{
            /*
             * Salvo l id passato come parametro nel DTO per essere sicuro che il team si iscriva all' hackathon specificato
             */
            teamDTO.setHackathonId(id);

            Team teamSalvato = teamService.iscriviTeam(teamDTO);
            return ResponseEntity.ok(teamSalvato.getNome());
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/{id}/avvia")
    public ResponseEntity<String> avviaHackathon(@PathVariable Long id) {
        try {
            hackathonService.avviaHackathon(id);
            return ResponseEntity.ok("Hackathon avviato con successo! Le iscrizioni sono chiuse.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/stop")
    public ResponseEntity<String> stopHackathon(@PathVariable Long id) {
        try {
            hackathonService.stopHackathon(id);
            return ResponseEntity.ok("Hackathon terminato. Inizia la fase di valutazione.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/concludi")
    public ResponseEntity<String> concludiHackathon(@PathVariable Long id) {
        try {
            hackathonService.concludiHackathon(id); // Devi aggiungere questo metodo nel Service
            return ResponseEntity.ok("Hackathon concluso definitivamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/vincitore")
    public ResponseEntity<?> ottieniVincitore(@PathVariable Long id) {
        try {
            VincitoreDTO vincitore = hackathonService.getVincitore(id);
            return ResponseEntity.ok(vincitore);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Nessun vincitore proclamato: " + e.getMessage());
        }
    }


    @PutMapping("/{id}/aggiungi-mentore")
    public ResponseEntity<?> aggiungiMentore(@PathVariable Long hackathonId, @RequestParam Long mentore_id ){
        try
        {
            hackathonService.aggiungiMentore(hackathonId, mentore_id);
            return ResponseEntity.ok("Mentore aggiunto con successo");
        } catch (IllegalArgumentException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e)
        {
            return ResponseEntity.status(404).body(e.getMessage());
        }

    }

    @GetMapping("{id}/mentori")
    public ResponseEntity<?> getMentori(@PathVariable Long hackathonId) {
        return ResponseEntity.ok(hackathonService.getMentoriHackathon(hackathonId));
    }
}



















