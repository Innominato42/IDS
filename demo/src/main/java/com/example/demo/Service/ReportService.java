package com.example.demo.Service;

import com.example.demo.DTO.ReportDTO;
import com.example.demo.DTO.ReportResponseDTO;
import com.example.demo.Model.Hackathon;
import com.example.demo.Model.Report;
import com.example.demo.Model.Team;
import com.example.demo.Model.Utente;
import com.example.demo.Repository.ReportRepository;
import com.example.demo.Repository.TeamRepository;
import com.example.demo.Repository.UtenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UtenteRepository utenteRepository;


    @Transactional
    public Report creaReport(ReportDTO reportDTO) {
        Team teamSegnalato = teamRepository.findById(reportDTO.getTeamSegnalatoId()).orElseThrow(() -> new RuntimeException("Team non trovato"));

        Utente autore = utenteRepository.findById(reportDTO.getAutoreId()).orElseThrow(() -> new RuntimeException("Autore non trovato"));


        Hackathon hackathon = teamSegnalato.getHackathon();

        if (!"MENTORE".equalsIgnoreCase(autore.getRuolo().toString())) {
            throw new SecurityException("Solo i mentori possono creare segnalazioni ");
        }

        Report report = new Report();
        report.setMotivo(reportDTO.getMotivo());
        report.setDataSegnalazione(LocalDateTime.now());
        report.setAutore(autore);
        report.setHackathon(hackathon);
        report.setTeamSegnalato(teamSegnalato);

        return reportRepository.save(report);
    }

    public List<ReportResponseDTO> getSegnalazioniHackathon(Long hackathonId) {
        List<Report> reports = reportRepository.findByHackathonId(hackathonId);
        return reports.stream().map(report -> {
            ReportResponseDTO dto = new ReportResponseDTO();
            dto.setId(report.getId());
            dto.setMotivo(report.getMotivo());
            dto.setDataSegnalazione(report.getDataSegnalazione());

            dto.setNomeAutore(report.getAutore().getUsername());
            dto.setNomeTeamSegnalato(report.getTeamSegnalato().getNome());
            return dto;
        }).collect(Collectors.toList());
    }
}
