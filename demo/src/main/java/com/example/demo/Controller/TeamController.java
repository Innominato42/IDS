package com.example.demo.Controller;

import com.example.demo.DTO.TeamDTO;
import com.example.demo.Model.Team;
import com.example.demo.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;


    @PostMapping("/crea")
    public ResponseEntity<?> creaTeam(@RequestBody TeamDTO teamDTO) {

        try
        {
            Team nuovoTeam =teamService.iscriviTeam(teamDTO);
            return ResponseEntity.ok().body(nuovoTeam);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/aggiungi")
    public ResponseEntity<?> aggiungiMembro(@PathVariable Long id,@RequestParam Long userId ) {
        try
        {
            teamService.aggiungiMembro(id, userId);
            return ResponseEntity.ok("Utente aggiunto al team");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTeam(@PathVariable Long id){
        try{
            Team team = teamService.findById(id);
            return ResponseEntity.ok().body(team);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/hackathon/{hackathonId}")
    public ResponseEntity<List<Team>> getTeamsByHackathon(@PathVariable Long hackathonId) {
        List<Team> teams = teamService.trovaTeamPerHackathon(hackathonId);
        return ResponseEntity.ok(teams);
    }
}
