package com.example.YourCarYourWay.repository;

import com.example.YourCarYourWay.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
}