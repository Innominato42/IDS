package com.example.demo.Repository;

import com.example.demo.Model.Sottomissione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SottommissioneRepository extends JpaRepository<Sottomissione, Long> {

    Optional<Sottomissione> findByTeamId(Long teamId);

    List<Sottomissione> findByHackathonId(Long hackathonId);
}
