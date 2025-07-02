package com.example.YourCarYourWay.service;

import com.example.YourCarYourWay.model.Utilisateur;
import com.example.YourCarYourWay.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for handling business logic related to users.
 */
@Service
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;

    /**
     * Injects the user repository.
     */
    @Autowired
    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    /**
     * Finds a user by their ID.
     */

    public Utilisateur findById(Long id) {
        return utilisateurRepository.findById(id).orElse(null);
    }
}