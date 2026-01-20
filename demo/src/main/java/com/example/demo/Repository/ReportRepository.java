package com.example.demo.Repository;

import com.example.demo.Model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report,Long> {

    List<Report> findByHackathonId(Long hackathonId);
}
