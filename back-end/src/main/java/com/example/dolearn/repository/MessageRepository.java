package com.example.dolearn.repository;

import com.example.dolearn.domain.Message;
import com.example.dolearn.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long>,UserMessageRepository {

}
