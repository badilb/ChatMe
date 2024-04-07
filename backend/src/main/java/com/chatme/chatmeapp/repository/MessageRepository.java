package com.chatme.chatmeapp.repository;

import com.chatme.chatmeapp.models.dto.MessageDTO;
import com.chatme.chatmeapp.models.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT new com.chatme.chatmeapp.models.dto.MessageDTO(m)" +
            "FROM com.chatme.chatmeapp.models.entity.Message m " +
            "WHERE m.id = :messageID")
    Optional<MessageDTO> findDTOById(Long messageID);
}
