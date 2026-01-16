package com.example.demo.Service;


import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    public void effettuaPagamento(String nomeTeam, double importo)
    {
        if(importo <=0) return;
        System.out.println("SISTEMA PAGAMENTI");
        System.out.println("Inviato bonifico di € "+importo +" al team " +nomeTeam);
        System.out.println("Transazione completata");
    }
}
