package com.example.YourCarYourWay.controller;

import com.example.YourCarYourWay.model.ChatMessage;
import com.example.YourCarYourWay.service.MessageSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * WebSocket controller for handling chat messages between users and support.
 */

@Controller
public class ChatController {
    private final MessageSupportService messageSupportService;

    /**
     * Injects the message support service.
     */
    @Autowired
    public ChatController(MessageSupportService messageSupportService) {
        this.messageSupportService = messageSupportService;
    }

    /**
     * Handles incoming chat messages from the WebSocket endpoint
     * '/chat.sendMessage'.
     * Processes and saves the message, then broadcasts it to all subscribers of
     * '/topic/public'.
     */
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage message) {
        return messageSupportService.processAndSaveMessage(message);
    }
}