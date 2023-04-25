package com.example.dolearn.repository;

import com.example.dolearn.config.TestConfig;
import com.example.dolearn.domain.User;
import com.example.dolearn.dto.UserDto;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(TestConfig.class)
@TestPropertySource("classpath:application-test.properties")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private Long id;
    private String name;
    private String email;
    private String password;
    private  String info;
    private  Integer point;
    private  String instagram;
    private  String facebook;
    private  String blog;
    private String youtube;
    private String imgPath;
    private String imgUrl;
    private String refreshToken;
    private String accessToken;

    Date joinDate;

    UserDto userDto;

    @BeforeEach
    void setup(){
        id = 1L;
        name = "가입자명";
        email = "abcd@daum.net";
        password = "abcdpassord";
        info = "안녕하세요";
        point = 50;
        instagram = "insta__";
        facebook = "";
        blog = "https://blog";
        youtube = "https://youtube";
        imgPath = "C://dolearn";
        imgUrl = "https://localhost:8080/resources/profile-img/1234543121";
        refreshToken = null;
        accessToken = null;
        joinDate = new Date();
        userDto = UserDto.builder().name(name).email(email).password(password).info(info).
                instagram(instagram).facebook(facebook).blog(blog).youtube(youtube).
                imgPath(imgPath).imgUrl(imgUrl).build();
    }

    @Nested
    class Save {
        @Test
        @DisplayName("사용자 추가 성공")
        public void success() {
            User user = userRepository.save(userDto.toEntity());

            assertThat(user.getName()).isEqualTo(name);
            assertThat(user.getPassword()).isEqualTo(password);
        }

        @Test
        @DisplayName("사용자 추가 실패 - 이메일 중복")
        public void fail() {
            userRepository.save(userDto.toEntity());
            Exception exception = assertThrows(DataIntegrityViolationException.class, ()->{
                userRepository.save(userDto.toEntity());
            });

            assertTrue(exception instanceof DataIntegrityViolationException);
            assertTrue(exception.getCause() instanceof ConstraintViolationException);
        }
    }

    @Nested
    class FindOneByEmail {
        @Test
        @DisplayName("이메일로 사용자 찾기 성공")
        public void success() {
            UserDto userDto = UserDto.builder().name(name).email(email).password(password).info(info).
                    instagram(instagram).facebook(facebook).blog(blog).youtube(youtube).
                    imgPath(imgPath).imgUrl(imgUrl).build();

            userRepository.save(userDto.toEntity());
            User user = userRepository.findOneByEmail(email).get();

            assertThat(user.getName()).isEqualTo(name);
        }

        @Test
        @DisplayName("이메일로 사용자 찾기 실패 - 해당 이메일 가진 사용자 없음")
        public void fail() {
            Exception exception = assertThrows(NoSuchElementException.class, ()->{
                userRepository.findOneByEmail(email).get();
            });

            assertTrue(exception instanceof NoSuchElementException);
            assertNull(exception.getCause());
        }
    }

    @Nested
    class FindOneById {
        @Test
        @DisplayName("ID로 사용자 찾기 성공")
        public void success() {
            User newUser = userRepository.save(userDto.toEntity());
            User user = userRepository.findOneById(newUser.getId()).get();

            assertThat(user.getName()).isEqualTo(name);
        }

        @Test
        @DisplayName("이메일로 사용자 찾기 실패 - 해당 이메일 가진 사용자 없음")
        public void fail() {
            Exception exception = assertThrows(NoSuchElementException.class, ()->{
                userRepository.findOneById(id).get();
            });

            assertTrue(exception instanceof NoSuchElementException);
            assertNull(exception.getCause());
        }
    }

    @Nested
    class delete {
        @Test
        @DisplayName("사용자 삭제 성공")
        public void success() {
            userRepository.save(userDto.toEntity());
            userRepository.delete(userDto.toEntity());
        }

        @Test
        @DisplayName("사용자 삭제 실패 - 존재하지 않는 사용자")
        public void fail() {
            Exception exception = assertThrows(NoSuchElementException.class, ()->{
                userRepository.findOneById(id).get();
            });

            assertTrue(exception instanceof NoSuchElementException);
            assertNull(exception.getCause());
        }
    }
}
