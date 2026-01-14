package com.example.demo.Controller;


import com.example.demo.DTO.SottomissioneDTO;
import com.example.demo.Model.Sottomissione;
import com.example.demo.Service.HackathonService;
import com.example.demo.Service.SottomissioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hackathon")
public class SottomissioneController {

    @Autowired
    private SottomissioneService sottomissioneService;


    public ResponseEntity<?> createSottomissione(@RequestBody SottomissioneDTO sottomissioneDTO) {
        try{
            Sottomissione sottomissione = sottomissioneService.creaSottomissione(
                    sottomissioneDTO.getIdHackathon(),
                    sottomissioneDTO.getIdTeam(),
                    sottomissioneDTO.getLink(),
                    sottomissioneDTO.getDescrizione()
            );
            return  ResponseEntity.ok(sottomissione);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }



}
