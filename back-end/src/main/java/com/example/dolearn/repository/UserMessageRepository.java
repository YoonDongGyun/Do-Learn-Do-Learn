package com.example.dolearn.repository;


import com.example.dolearn.domain.Message;

import java.util.List;

public interface UserMessageRepository {

    List<Message> findMessageByUserId(Long userId);
}
