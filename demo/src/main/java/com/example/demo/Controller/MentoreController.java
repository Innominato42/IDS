package com.example.demo.Controller;

import com.example.demo.DTO.RichiestaSupportoDTO;
import com.example.demo.Model.RichiestaSupporto;
import com.example.demo.Service.MentoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mentore")
public class MentoreController {

    @Autowired
    private MentoreService mentoreService;


    @PostMapping("/chiediSupporto")
    public ResponseEntity<?> chiediSupporto(@RequestBody RichiestaSupportoDTO richiestaSupportoDTO)
    {
        try
        {
            RichiestaSupporto richiesta= mentoreService.creaRichiesta(richiestaSupportoDTO);
            return ResponseEntity.ok("Richiesta inviata al mentore");
        }catch (IllegalStateException e)
        {
            return ResponseEntity.badRequest().body("Errore fase" + e.getMessage());
        }
    }

    @PutMapping("/{id}/rispondiRichiestaSupporto")
    public ResponseEntity<?> rispondiRichiestaSupporto(@PathVariable Long id, @RequestParam String risposta, @RequestParam String stato)
    {
        RichiestaSupporto richiesta=mentoreService.rispondiRichiesta(id, risposta, stato);
        return ResponseEntity.ok(richiesta);
    }

    @GetMapping("/mentore/{id}")
    public ResponseEntity<List<RichiestaSupporto>> getRichiesteMentore(@PathVariable Long id) {
        return ResponseEntity.ok(mentoreService.getRichiestaPerMentore(id));
    }

    @GetMapping("/mentore/{mentoreId}")
    public ResponseEntity<List<RichiestaSupporto>> getRichiestePerMentore(@PathVariable Long mentoreId) {
        return ResponseEntity.ok(mentoreService.getRichiesteMentore(mentoreId));
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<RichiestaSupporto>> getRichiestePerTeam(@PathVariable Long teamId) {
        return ResponseEntity.ok(mentoreService.getRichiesteDelTeam(teamId));
    }

    @GetMapping("/hackathon/{hackathonId}")
    public ResponseEntity<List<RichiestaSupporto>> getRichiestePerHackathon(@PathVariable Long hackathonId) {
        return ResponseEntity.ok(mentoreService.getAllRichiesteHackathon(hackathonId));
    }
}
