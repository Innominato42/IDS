package com.example.demo.Controller;


import com.example.demo.DTO.ReportDTO;
import com.example.demo.DTO.ReportResponseDTO;
import com.example.demo.Model.Report;
import com.example.demo.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("invia")
    public ResponseEntity<?> segnalaTeam(@RequestBody ReportDTO reportDTO) {
        try {
            Report reportSalvato = reportService.creaReport(reportDTO);
            return ResponseEntity.ok("Report inviato con successo ");
        } catch (SecurityException e) {
            return ResponseEntity.status((HttpStatus.FORBIDDEN)).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/hackathon/{hackathonId}")
    public ResponseEntity<List<ReportResponseDTO>>  getReportPerHackathon(@PathVariable Long hackathonId)
    {
        List<ReportResponseDTO> reports = reportService.getSegnalazioniHackathon(hackathonId); // <--- E qui

        if (reports.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(reports);
    }
}
