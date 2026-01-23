package com.example.demo.Repository;

import com.example.demo.Model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente ,Long > {

    Optional<Utente> findByUsername(String username);
}
