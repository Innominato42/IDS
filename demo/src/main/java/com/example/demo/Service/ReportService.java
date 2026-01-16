package com.example.demo.Service;

import com.example.demo.DTO.ReportDTO;
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

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UtenteRepository utenteRepository;


    @Transactional
    public Report creaReport(ReportDTO reportDTO)
    {
        Team teamSegnalato = teamRepository.findById(reportDTO.getTeamSegnalatoId()).orElseThrow(()-> new RuntimeException("Team non trovato"));

        Utente autore= utenteRepository.findById(reportDTO.getAutoreId()).orElseThrow(()-> new RuntimeException("Autore non trovato"));

        Hackathon hackathon = teamSegnalato.getHackathon();

        if (!"MENTORE".equalsIgnoreCase(autore.getRuolo().toString())) {
            throw new SecurityException("Solo i mentori possono creare segnalazioni ");
        }

        Report report = new Report();
        report.setMotivo(reportDTO.getMotivo());
        report.setDataSegnalazione(report.getDataSegnalazione());
        report.setAutore(report.getAutore());
        report.setHackathon(hackathon);
        report.setTeamSegnalato(teamSegnalato);

        return reportRepository.save(report);
    }

    public List<Report> getSegnalazioniHackathon(Long hackathonId) {
        return reportRepository.findByHackathonId(hackathonId);
    }
}
