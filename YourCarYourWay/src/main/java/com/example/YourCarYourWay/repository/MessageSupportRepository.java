package com.example.YourCarYourWay.repository;

import com.example.YourCarYourWay.model.MessageSupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageSupportRepository extends JpaRepository<MessageSupport, Long> {
}