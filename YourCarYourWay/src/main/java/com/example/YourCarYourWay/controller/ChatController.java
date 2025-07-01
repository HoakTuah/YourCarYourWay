package com.example.YourCarYourWay.controller;

import com.example.YourCarYourWay.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage message) {
        // Ici vous pourriez ajouter la logique pour sauvegarder en base
        // et récupérer le nom de l'utilisateur depuis la base de données
        return message;
    }
}