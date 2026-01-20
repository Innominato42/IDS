package com.example.demo.Repository;

import com.example.demo.Model.Invito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  InvitoRepository extends JpaRepository<Invito, Long> {

    List<Invito> findByUtenteInvitatoIdAndStato(Long utenteId, String stato);
}
