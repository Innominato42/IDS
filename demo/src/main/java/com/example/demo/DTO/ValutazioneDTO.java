package com.example.demo.DTO;

import lombok.Data;

@Data
public class ValutazioneDTO {

    private Long sottomissioneId;
    private Long giudiceId;
    private int punteggio;
    private String giudizio;

}
