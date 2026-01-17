package com.example.demo.Repository;

import com.example.demo.Model.Invito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  InvitoRepository extends JpaRepository<Invito, Long> {

    List<Invito> findByUtenteInvitatoIdAndStato(Long utenteId, String stato);
}
