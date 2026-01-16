package com.example.demo.Repository;

import com.example.demo.Model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Long> {

    List<Report> findByHackathonId(Long hackathonId);
}
