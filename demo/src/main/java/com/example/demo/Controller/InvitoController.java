package com.example.demo.Controller;


import com.example.demo.Model.Invito;
import com.example.demo.Repository.InvitoRepository;
import com.example.demo.Service.InvitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inviti")
public class InvitoController {

    @Autowired
    private InvitoService invitoService;

    @PostMapping("invia")
    public ResponseEntity<?> inviaInvito( @RequestParam Long teamId, @RequestParam Long userId) {

        Invito invito = invitoService.creaInvito(teamId, userId);
        return new ResponseEntity<>("Invito inviato con successo", HttpStatus.OK);
    }

    @PutMapping("/{id}/rispondi")
    public ResponseEntity<?> rispondiInvito( @PathVariable Long id,@RequestParam Long utenteId,@RequestParam boolean risposta ) {
        Invito aggiornato = invitoService.gestisciRisposta(id, utenteId, risposta);
        String esito = risposta ? "ACCETTATO (Benvenuto nel team!)" : "RIFIUTATO :(";
        return ResponseEntity.ok("Invito " + esito);
    }


    @GetMapping("/attesa/{utenteId}")
    public ResponseEntity<List<Invito>> getAllInvitiInAttesa(@PathVariable Long utenteId) {
        return ResponseEntity.ok(invitoService.getAllInvitiInAttesa(utenteId));
    }



}
