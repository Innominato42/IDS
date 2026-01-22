package com.example.demo.Controller;


import com.example.demo.DTO.SottomissioneDTO;
import com.example.demo.DTO.ValutazioneDTO;
import com.example.demo.Model.Sottomissione;
import com.example.demo.Service.HackathonService;
import com.example.demo.Service.SottomissioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sottomissione")
public class SottomissioneController {

    @Autowired
    private SottomissioneService sottomissioneService;


    @PostMapping("/invia")
    public ResponseEntity<?> inviaSottomissione(@RequestBody SottomissioneDTO sottomissioneDTO, @RequestParam Long userId) {
        try
        {
            Sottomissione s = sottomissioneService.creaSottomissione(sottomissioneDTO, userId);
            return ResponseEntity.ok("Sottomissione inviata");
        } catch (IllegalStateException e)
        {
            return ResponseEntity.badRequest().body(" non puoi creare sottomissioni in questa fase" +e.getMessage());
        } catch (RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/valuta")
    public ResponseEntity<?> valutaSottomissione(@RequestBody ValutazioneDTO valutazioneDTO) {
        try
        {
            Sottomissione valutata = sottomissioneService.valutaSottomissione(valutazioneDTO);
            return ResponseEntity.ok("Valutazione inviata");
        } catch (SecurityException e )
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        catch (IllegalStateException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @GetMapping("/team/{teamId}")
    public ResponseEntity<?> getSottomissioneTeam(@PathVariable Long teamId) {
        try
        {
            Sottomissione sottomissione= sottomissioneService.getSottomissionePerTeam(teamId);
            return ResponseEntity.ok(sottomissione);
        } catch (RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




}
