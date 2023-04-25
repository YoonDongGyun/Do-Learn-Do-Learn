package com.example.dolearn.repository;

import com.example.dolearn.config.TestConfig;
import com.example.dolearn.domain.Message;
import com.example.dolearn.domain.User;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(TestConfig.class)
@TestPropertySource("classpath:application-test.properties")
public class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("message repository 저장 테스트")
    @Test
    public void messageRepositorySaveTest() {

        Message message = Message
                .builder()
                .id(1L)
                .content("test")
                .isChecked(0)
                .build();

        Message result = messageRepository.save(message);

        assertThat(result.getContent()).isEqualTo(message.getContent());
        assertThat(result.getId()).isEqualTo(result.getId());
        assertThat(result.getIsChecked()).isEqualTo(result.getIsChecked());
    }

    @DisplayName("messageRepository findById 테스트")
    @Test
    public void MessageRepositoryFindByIdTest() {
        Message message = Message
                .builder()
                .id(1L)
                .content("test")
                .isChecked(0)
                .build();

        messageRepository.save(message);

        Message result = messageRepository.findById(1L).get();

        assertThat(result.getContent()).isEqualTo(message.getContent());
        assertThat(result.getIsChecked()).isEqualTo(result.getIsChecked());
    }

    @DisplayName("messageRepository findMessageByUserId 테스트")
    @Test
    public void MessageRepositoryFindMessageByUserId() {

        User user = User
                .builder()
                .email("cksgnlcjswo@naver.com")
                .password("1234")
                .name("test user")
                .build();

        User newUser = userRepository.save(user);

        Message message1 = Message
                .builder()
                .content("test")
                .user(newUser)
                .isChecked(0)
                .build();

        Message message2 = Message
                .builder()
                .content("test 2")
                .user(newUser)
                .isChecked(0)
                .build();

        Message message3 = Message
                .builder()
                .content("test 3")
                .user(newUser)
                .isChecked(0)
                .build();

        messageRepository.save(message1);
        messageRepository.save(message2);
        messageRepository.save(message3);

        List<Message> result = messageRepository.findMessageByUserId(1L);

        assertThat(result).isNotNull();
    }
}
