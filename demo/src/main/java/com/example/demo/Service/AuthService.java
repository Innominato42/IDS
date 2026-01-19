package com.example.demo.Service;

import com.example.demo.DTO.LoginDTO;
import com.example.demo.DTO.RegistrazioneDTO;
import com.example.demo.Model.Ruolo;
import com.example.demo.Model.Utente;
import com.example.demo.Repository.UtenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UtenteRepository utenteRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public Utente registrazione(RegistrazioneDTO dto)
    {
        if(utenteRepository.findByUsername(dto.getUsername()).isPresent())
        {
            throw new RuntimeException("Username gia esistente");
        }
        Utente utente = new Utente();
        utente.setUsername(dto.getUsername());
        utente.setPassword(passwordEncoder.encode(dto.getPassword()));
        utente.setRuolo(Ruolo.valueOf(dto.getRuolo().toUpperCase()));
        return utenteRepository.save(utente);
    }

    public Utente login(LoginDTO dto)
    {
        Utente u = utenteRepository.findByUsername(dto.getUsername()).orElseThrow(() -> new RuntimeException("Utente non trovato"));
        if (!passwordEncoder.matches(dto.getPassword(), u.getPassword())) {
            throw new RuntimeException("Password errata!");
        }
        return u;
    }

}
