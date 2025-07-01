package com.example.YourCarYourWay.controller;

import com.example.YourCarYourWay.model.ChatMessage;
import com.example.YourCarYourWay.model.MessageSupport;
import com.example.YourCarYourWay.service.MessageSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ChatController {
    private final MessageSupportService messageSupportService;

    @Autowired
    public ChatController(MessageSupportService messageSupportService) {
        this.messageSupportService = messageSupportService;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage message) {
        // Mapper ChatMessage vers MessageSupport
        MessageSupport entity = new MessageSupport();
        entity.setUtilisateurId(message.getUtilisateurId());
        entity.setType(message.getType());
        entity.setContenu(message.getContenu());
        entity.setStatut(message.getStatut());
        entity.setDateEnvoi(message.getDateEnvoi());
        // Sauvegarder en base
        MessageSupport saved = messageSupportService.save(entity);
        // Mettre à jour l'id et la dateEnvoi dans le ChatMessage à retourner
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

        @GetMapping("/history")
        public List<ChatMessage> getHistory() {
            List<MessageSupport> messages = messageSupportService.findAll();
            return messages.stream().map(msg -> {
                ChatMessage chatMsg = new ChatMessage();
                chatMsg.setId(msg.getId());
                chatMsg.setUtilisateurId(msg.getUtilisateurId());
                chatMsg.setSender("");
                chatMsg.setContenu(msg.getContenu());
                chatMsg.setType(msg.getType());
                chatMsg.setStatut(msg.getStatut());
                chatMsg.setDateEnvoi(msg.getDateEnvoi());
                return chatMsg;
            }).collect(Collectors.toList());
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
            // Sauvegarder en base
            MessageSupport saved = messageSupportService.save(entity);
            // Mettre à jour l'id et la dateEnvoi dans le ChatMessage à retourner
            message.setId(saved.getId());
            message.setDateEnvoi(saved.getDateEnvoi());
            return message;
        }
    }
}