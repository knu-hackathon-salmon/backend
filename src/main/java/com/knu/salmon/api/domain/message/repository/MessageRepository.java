package com.knu.salmon.api.domain.message.repository;


import com.knu.salmon.api.domain.chat.entity.Chat;
import com.knu.salmon.api.domain.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByChat(Chat chat);
}
