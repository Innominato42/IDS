package com.example.demo.Controller;

import com.example.demo.DTO.PrenotaCallDTO;
import com.example.demo.Model.PrenotaCall;
import com.example.demo.Service.PrenotaCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calls")
public class PrenotaCallController {

    @Autowired
    private PrenotaCallService prenotaCallService;

    @PostMapping("/prenota")
    public ResponseEntity<?> prenota(@RequestBody PrenotaCallDTO prenotaCallDTO) {
        return ResponseEntity.ok(prenotaCallService.prenotaCall(prenotaCallDTO));
    }

    @PutMapping("/{id}/rispondi")
    public ResponseEntity<?> rispondi(
            @PathVariable Long id,
            @RequestParam boolean risposta,
            @RequestParam(required = false) String link) {

        return ResponseEntity.ok(prenotaCallService.rispondiCall(id, risposta, link));
    }
}
