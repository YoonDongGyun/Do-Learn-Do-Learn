package com.example.dolearn.service;

import com.example.dolearn.domain.*;
import com.example.dolearn.dto.MessageDto;
import com.example.dolearn.exception.CustomException;
import com.example.dolearn.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class MessageServiceTest {

    MessageService messageService;

    @MockBean
    MessageRepository messageRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    UserLectureRepository userLectureRepository;

    @MockBean
    LectureRepository lectureRepository;

    @MockBean
    BoardRepository boardRepository;

    @BeforeEach
    void setUp() {

        messageService = new MessageService(messageRepository,userRepository,
                                            lectureRepository,userLectureRepository,boardRepository);
    }

    @DisplayName("메세지 생성 테스트")
    @Test
    public void MessageCreateTest() {

        //given
        Optional<Lecture> lecture = Optional.of(Lecture.builder().id(1L).build());
        MessageDto messageDto = MessageDto.builder().bid(1L).content("강의확정").build();
        Board board = Board.builder().id(1L).title("좋은 강의입니다.").build();

        User user1 = User.builder().id(1L).name("test1").build();

        List<UserLecture> result = new ArrayList<>();

        UserLecture userLecture1 = UserLecture.builder().id(1L).build();
        UserLecture userLecture2 = UserLecture.builder().user(user1).id(2L).build();

        result.add(userLecture1);
        result.add(userLecture2);

        //when
        when(boardRepository.findById(anyLong())).thenReturn(Optional.of(board));
        when(lectureRepository.findByBoardId(anyLong())).thenReturn(lecture.get());
        when(userLectureRepository.searchLecture(anyLong())).thenReturn(result);

        //then
        List<MessageDto> mList = messageService.createMessage(messageDto);
        assertThat(mList.size()).isEqualTo(1);
    }


    @DisplayName("메세지 확인 업데이트 테스트")
    @Test
    public void messageCheckUpdateTest() {
        //given

        MessageDto messageDto = MessageDto.builder()
                .id(1L)
                .rid(1L)
                .content("강의가 취소되었습니다.")
                .isChecked(0).build();

        Optional<Message> result = Optional.of(messageDto.toEntity());
        Message message = Message.builder().isChecked(1).content("test").build();

        when(messageRepository.findById(any())).thenReturn(result);

        when(messageRepository.save(any())).thenReturn(message);
        //when
        messageService.updateCheck(messageDto);

        //then
        assertThat(message.getIsChecked()).isEqualTo(1);
    }

    @DisplayName("메세지 가져오기 서비스")
    @Test
    public void getDetailMessageTest() {

        Message message = Message.builder().content("test").isChecked(1).build();
        Board board = Board.builder().id(1L).title("좋은 강의입니다.").build();
        User user = User.builder().name("test").build();

        message.setUser(user);
        message.setBoard(board);

        Optional<Message> result = Optional.of(message);

        when(messageRepository.findById(anyLong())).thenReturn(result);

        MessageDto messageDto = messageService.getMessage(1L);

        assertThat(messageDto).isNotNull();
        assertThat(messageDto.getIsChecked()).isEqualTo(1L);
    }

    @DisplayName("특정유저가 받은 메세지 가져오기")
    @Test
    public void getMessageList() {

        User user = User.builder().name("test").build();

        Message message1 = Message
                .builder()
                .content("test")
                .user(user)
                .isChecked(0)
                .build();

        Message message2 = Message
                .builder()
                .content("test")
                .user(user)
                .isChecked(0)
                .build();

        Board board = Board
                .builder()
                .title("좋은 강의입니다.")
                .build();

        message1.setUser(user);
        message1.setBoard(board);
        message2.setUser(user);
        message2.setBoard(board);

        when(userRepository.findOneById(anyLong())).thenReturn(Optional.of(user));

        List<MessageDto> result = messageService.getMessageList(1L);

        assertThat(result).isNotNull();
    }

    @DisplayName("특정 메세지 가져오기")
    @Test
    public void getMessageDetail() {

        User user = User.builder().id(1L).name("test").build();
        Board board = Board.builder().id(1L).title("좋은 강의입니다.").build();

        Optional<Message> result = Optional.of(Message.builder().content("test").isChecked(0).build());

        result.get().setUser(user);
        result.get().setBoard(board);

        when(messageRepository.findById(anyLong())).thenReturn(result);

        MessageDto messageDto = messageService.getMessage(1L);

        assertThat(messageDto.getContent()).isEqualTo(result.get().getContent());
    }

    @DisplayName("특정 메세지 삭제 테스트")
    @Test
    public void deleteMessage() throws Exception {

        Message message = Message
                .builder()
                .content("test content")
                .build();

        when(messageRepository.findById(anyLong())).thenReturn(Optional.of(message));

        messageService.deleteMessage(1L);
    }

    @DisplayName("특정 메세지 삭제 에러 발생 테스트")
    @Test
    public void deleteMessageNoMessage() {

        Exception exception = assertThrows(CustomException.class, () -> {
            messageService.deleteMessage(2L);
        });

        assertTrue(exception instanceof CustomException);
        assertEquals(exception.getMessage(),"없는 메세지 입니다.");
    }

    @DisplayName("읽지 않은 메세지 가져오기 테스트")
    @Test
    public void getUnCheckMessageList() {

        User user = User
                .builder()
                .email("cksgnlcjswoo@nVER.COM")
                .name("test1")
                .build();

        Board board = Board
                .builder()
                .title("tmp")
                .build();

        Message message1 = Message
                .builder()
                .content("강의확정")
                .isChecked(1)
                .build();

        Message message2 = Message
                .builder()
                .content("강의확정")
                .isChecked(0)
                .build();

        message1.setUser(user); message1.setBoard(board);
        message2.setUser(user); message2.setBoard(board);

        when(userRepository.findOneById(anyLong())).thenReturn(Optional.of(user));

        List<MessageDto> result = messageService.getUnCheckMessageList(1L);

        assertThat(result.size()).isEqualTo(1);
    }
}
