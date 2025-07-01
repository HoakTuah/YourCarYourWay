package com.example.YourCarYourWay.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "message_support")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "utilisateur_id")
    private Long utilisateurId;

    @Column(name = "type")
    private String type;

    @Column(name = "contenu")
    private String contenu;

    @Column(name = "statut")
    private String statut;

    @Column(name = "date_envoi")
    private LocalDateTime dateEnvoi;
}