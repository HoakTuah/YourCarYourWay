package com.example.YourCarYourWay.service;

import com.example.YourCarYourWay.model.MessageSupport;
import com.example.YourCarYourWay.repository.MessageSupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageSupportService {
    private final MessageSupportRepository messageSupportRepository;

    @Autowired
    public MessageSupportService(MessageSupportRepository messageSupportRepository) {
        this.messageSupportRepository = messageSupportRepository;
    }

    public MessageSupport save(MessageSupport message) {
        return messageSupportRepository.save(message);
    }

    public List<MessageSupport> findAll() {
        return messageSupportRepository.findAll();
    }

    // You can add more methods as needed (e.g., findByUtilisateurId)
}
