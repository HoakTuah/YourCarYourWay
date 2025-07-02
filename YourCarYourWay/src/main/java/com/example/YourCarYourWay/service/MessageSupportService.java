package com.example.YourCarYourWay.service;

import com.example.YourCarYourWay.model.ChatMessage;
import com.example.YourCarYourWay.model.MessageSupport;
import com.example.YourCarYourWay.model.Utilisateur;
import com.example.YourCarYourWay.repository.MessageSupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for handling business logic related to support messages.
 */

@Service
public class MessageSupportService {
    private final MessageSupportRepository messageSupportRepository;
    private final UtilisateurService utilisateurService;

    /**
     * Injects the message support repository and user service.
     */

    @Autowired
    public MessageSupportService(MessageSupportRepository messageSupportRepository,
            UtilisateurService utilisateurService) {
        this.messageSupportRepository = messageSupportRepository;
        this.utilisateurService = utilisateurService;
    }

    /**
     * Saves a MessageSupport entity to the database.
     */

    public MessageSupport save(MessageSupport message) {
        return messageSupportRepository.save(message);
    }

    /**
     * Retrieves all support messages from the database.
     */

    public List<MessageSupport> findAll() {
        return messageSupportRepository.findAll();
    }

    /**
     * Processes a ChatMessage: assigns user ID if missing, sets sender name, sets
     * send date,
     * maps to MessageSupport entity, saves it, and returns the enriched
     * ChatMessage.
     */

    public ChatMessage processAndSaveMessage(ChatMessage message) {
        // Assign default user ID for test purposes
        if (message.getUtilisateurId() == null) {
            if ("SUPPORT_TO_USER".equalsIgnoreCase(message.getType())) {
                message.setUtilisateurId(2L);
            } else if ("USER_TO_SUPPORT".equalsIgnoreCase(message.getType())) {
                message.setUtilisateurId(1L);
            } else {
                message.setUtilisateurId(0L);
            }
        }

        // Retrieve user and set sender name
        Utilisateur utilisateur = utilisateurService.findById(message.getUtilisateurId());
        if (utilisateur != null) {
            message.setSender(utilisateur.getNom() + " " + utilisateur.getPrenom());
        } else {
            message.setSender("Inconnu");
        }
        // Set send date if missing
        if (message.getDateEnvoi() == null) {
            message.setDateEnvoi(LocalDateTime.now());
        }

        // Map ChatMessage to MessageSupport entity
        MessageSupport entity = new MessageSupport();
        entity.setUtilisateurId(message.getUtilisateurId());
        entity.setType(message.getType());
        entity.setContenu(message.getContenu());
        entity.setStatut(message.getStatut());
        entity.setDateEnvoi(message.getDateEnvoi());
        // Save in database
        MessageSupport saved = messageSupportRepository.save(entity);
        // Update id and send date in the ChatMessage to return
        message.setId(saved.getId());
        message.setDateEnvoi(saved.getDateEnvoi());
        return message;
    }
}
