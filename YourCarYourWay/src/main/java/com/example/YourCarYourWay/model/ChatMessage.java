package com.example.YourCarYourWay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private Long id;
    private Long utilisateurId;
    private String sender; // nom de l'utilisateur pour l'affichage
    private String contenu;
    private String type;
    private String statut;
    private LocalDateTime dateEnvoi;
}