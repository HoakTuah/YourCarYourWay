package com.example.YourCarYourWay.controller;

import com.example.YourCarYourWay.model.ChatMessage;
import com.example.YourCarYourWay.model.MessageSupport;
import com.example.YourCarYourWay.model.Utilisateur;
import com.example.YourCarYourWay.service.MessageSupportService;
import com.example.YourCarYourWay.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ChatController {
    private final MessageSupportService messageSupportService;
    private final UtilisateurService utilisateurService;

    @Autowired
    public ChatController(MessageSupportService messageSupportService, UtilisateurService utilisateurService) {
        this.messageSupportService = messageSupportService;
        this.utilisateurService = utilisateurService;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage message) {

        // Test purpose default value for Client and Support
        if (message.getUtilisateurId() == null) {
            if ("SUPPORT_TO_USER".equalsIgnoreCase(message.getType())) {
                message.setUtilisateurId(2L);
            } else if ("USER_TO_SUPPORT".equalsIgnoreCase(message.getType())) {
                message.setUtilisateurId(1L);
            } else {
                message.setUtilisateurId(0L);
            }
        }

        Utilisateur utilisateur = utilisateurService.findById(message.getUtilisateurId());
        if (utilisateur != null) {
            message.setSender(utilisateur.getNom() + " " + utilisateur.getPrenom());
        } else {
            message.setSender("Inconnu");
        }
        if (message.getDateEnvoi() == null) {
            message.setDateEnvoi(LocalDateTime.now());
        }

        // Mapper ChatMessage vers MessageSupport
        MessageSupport entity = new MessageSupport();
        entity.setUtilisateurId(message.getUtilisateurId());
        entity.setType(message.getType());
        entity.setContenu(message.getContenu());
        entity.setStatut(message.getStatut());
        entity.setDateEnvoi(message.getDateEnvoi());
        // Save in database
        MessageSupport saved = messageSupportService.save(entity);
        // Update id and dateEnvoi in the ChatMessage to return
        message.setId(saved.getId());
        message.setDateEnvoi(saved.getDateEnvoi());
        return message;
    }

    // --- Ajout d'une API REST pour l'historique ---
    @RestController
    @RequestMapping("/api/chat")
    public static class ChatHistoryRestController {
        private final MessageSupportService messageSupportService;

        @Autowired
        public ChatHistoryRestController(MessageSupportService messageSupportService) {
            this.messageSupportService = messageSupportService;
        }

        @PostMapping("/send")
        public ChatMessage sendMessageRest(@RequestBody ChatMessage message) {
            // Mapper ChatMessage vers MessageSupport
            MessageSupport entity = new MessageSupport();
            entity.setUtilisateurId(message.getUtilisateurId());
            entity.setType(message.getType());
            entity.setContenu(message.getContenu());
            entity.setStatut(message.getStatut());
            entity.setDateEnvoi(message.getDateEnvoi());

            MessageSupport saved = messageSupportService.save(entity);

            message.setId(saved.getId());
            message.setDateEnvoi(saved.getDateEnvoi());
            return message;
        }
    }
}