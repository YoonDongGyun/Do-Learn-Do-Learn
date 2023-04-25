package com.example.dolearn.service;

import com.example.dolearn.domain.*;
import com.example.dolearn.dto.*;
import com.example.dolearn.exception.CustomException;
import com.example.dolearn.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String encodedPassword;
    private String imgPath;
    private String imgUrl;
    private String info;
    private String blog;
    private String facebook;
    private String instagram;
    private Integer point;
    private String youtube;

    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    BoardRepository boardRepository;
    @Mock
    FixedLectureRepository fixedLectureRepository;
    @Mock
    UserBoardRepository userBoardRepository;
    @Mock
    UserLectureRepository userLectureRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup(){
        id = 1L;
        name = "민싸피";
        email = "ssafy@naver.com";
        password = "abcd!1234";
        encodedPassword = "$2a$10$Ul5NqDvSKVE/pbZhr5TlduldDXNk8ZztWAg8gWGxjB0zuK9tn9QHy";
        imgPath = "새로운 이미지 path";
        imgUrl = "새로운 이미지 url";
        info = "안녕하세요";
        blog = "새로운 블로그 링크";
        facebook = "새로운 페이스북 링크";
        instagram = "새로운 인스타 링크";
        youtube = "새로운 유튜브 링크";
        point = 100;
    }

    @Test
    @Transactional
    void 회원가입성공() {
        UserDto userDto = UserDto.builder().email(email).password(password).build();

        when(userRepository.save(any(User.class))).thenReturn(userDto.toEntity());

        UserDto result = userService.signup(userDto);

        assertThat(result.getEmail()).isEqualTo(email);
    }

    @Test
    @Transactional
    void 회원가입실패_중복이메일() {
        UserDto userDto = UserDto.builder().email(email).password(password).build();

        when(userRepository.findOneByEmail(email)).thenReturn(Optional.ofNullable(userDto.toEntity()));

        Exception exception = assertThrows(CustomException.class, ()->{
            UserDto result = userService.signup(userDto);
        });

        assertTrue(exception instanceof CustomException);
        assertEquals("이미 존재하는 이메일입니다.", exception.getMessage());
    }

    @Test
    @Transactional
    void 로그인성공() {
        UserDto reqUserDto = UserDto.builder().email(email).password(password).build();
        UserDto loginuUserDto = UserDto.builder().name(name).email(email).password(encodedPassword).build();

        when(userRepository.findOneByEmail(email)).thenReturn(Optional.ofNullable(loginuUserDto.toEntity()));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

        UserDto userDto = userService.login(reqUserDto);

        assertEquals(name, userDto.getName());
    }

    @Test
    @Transactional
    void 로그인실패_없는사용자() {
        UserDto userDto = UserDto.builder().build();

        Exception exception = assertThrows(CustomException.class, ()->{
            UserDto result = userService.login(userDto);
        });

        assertTrue(exception instanceof CustomException);
        assertEquals("없는 사용자입니다.", exception.getMessage());
    }

    @Test
    @Transactional
    void 로그인실패_이상한비번() {
        password = "이상한 비번";

        UserDto reqUserDto = UserDto.builder().email(email).password(password).build();
        UserDto loginuUserDto = UserDto.builder().name(name).email(email).password(encodedPassword).build();

        when(userRepository.findOneByEmail(email)).thenReturn(Optional.ofNullable(loginuUserDto.toEntity()));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        Exception exception = assertThrows(CustomException.class, ()->{
            UserDto result = userService.login(reqUserDto);
        });

        assertTrue(exception instanceof CustomException);
        assertEquals("비밀번호가 옳지 않습니다.", exception.getMessage());
    }

    @Test
    @Transactional
    void 토큰재발급성공() {
        UserDto reqUserDto = UserDto.builder().name(name).email(email).password(password).build();

        when(userRepository.save(any(User.class))).thenReturn(reqUserDto.toEntity());

        UserDto userDto = userService.updateToken(reqUserDto, "refresh-token", "access-token");

        assertThat(userDto.getRefreshToken().equals("refresh-token"));
    }

    @Test
    @Transactional
    void 로그아웃성공() {
        UserDto reqUserDto = UserDto.builder().name(name).email(email).password(password).build();

        when(userRepository.save(any(User.class))).thenReturn(reqUserDto.toEntity());
        when(userRepository.findOneById(any(Long.class))).thenReturn(Optional.ofNullable(reqUserDto.toEntity()));

        UserDto userDto = userService.logout(id);

        assertNull(userDto.getRefreshToken());
    }

    @Test
    @Transactional
    void 로그아웃실패_없는사용자() {
        when(userRepository.findOneById(id)).thenReturn(Optional.ofNullable(null));

        Exception exception = assertThrows(CustomException.class, ()->{
            UserDto result = userService.logout(id);
        });

        assertTrue(exception instanceof CustomException);
        assertEquals(exception.getMessage(), "없는 사용자입니다.");
    }

    @Test
    @Transactional
    void 사용자정보수정성공() {
        UserDto reqUserDto = UserDto.builder().id(id).email(email)
                .imgPath(imgPath).imgUrl(imgUrl).info(info).instagram(instagram).blog(blog).facebook(facebook).youtube(youtube)
                .build();

        when(userRepository.findOneById(id)).thenReturn(Optional.ofNullable(reqUserDto.toEntity()));
        when(userRepository.save(any(User.class))).thenReturn(reqUserDto.toEntity());

        UserDto userDto = userService.updateInfo(reqUserDto);

        assertEquals(email, userDto.getEmail());
    }

    @Test
    @Transactional
    void 사용자정보수정실패_부족한정보() {
        UserDto reqUserDto = UserDto.builder().id(id).email(email)
                .blog(blog).build();

        Exception exception = assertThrows(CustomException.class, ()->{
            UserDto result = userService.updateInfo(reqUserDto);
        });

        assertTrue(exception instanceof CustomException);
        assertEquals("기입되지 않은 정보가 있습니다", exception.getMessage());
    }

    @Test
    @Transactional
    void 사용자정보수정실패_없는사용자() {
        UserDto reqUserDto = UserDto.builder().id(id).email(email)
                .imgPath(imgPath).imgUrl(imgUrl).info(info).instagram(instagram).blog(blog).facebook(facebook).youtube(youtube)
                .build();

        when(userRepository.findOneById(id)).thenReturn(Optional.ofNullable(null));

        Exception exception = assertThrows(CustomException.class, ()->{
            UserDto result = userService.updateInfo(reqUserDto);
        });

        assertTrue(exception instanceof CustomException);
        assertEquals("없는 사용자입니다.", exception.getMessage());
    }

    @Test
    @Transactional
    void 프로필사진업데이트성공(){
        id = 1L;
        imgPath = "imgpath";
        imgUrl = "imgurl";
        UserDto reqUserDto = UserDto.builder().id(id).imgPath(imgPath).imgUrl(imgUrl).build();

        when(userRepository.findOneById(id)).thenReturn(Optional.ofNullable(reqUserDto.toEntity()));
        when(userRepository.save(any(User.class))).thenReturn(reqUserDto.toEntity());

        UserDto userDto = userService.updateImgInfo(id, imgPath, imgUrl);

        assertEquals(imgPath, userDto.getImgPath());
    }

    @Test
    @Transactional
    void 프로필사진업데이트실패_부족한정보(){
        id = 1L;
        imgPath = "imgpath";
        imgUrl = "imgurl";

        Exception exception = assertThrows(CustomException.class, ()->{
            UserDto result = userService.updateImgInfo(null, imgPath, imgUrl);
        });
        assertTrue(exception instanceof CustomException);
        assertEquals("기입되지 않은 정보가 있습니다", exception.getMessage());

        exception = assertThrows(CustomException.class, ()->{
            UserDto result = userService.updateImgInfo(id, null, imgUrl);
        });
        assertTrue(exception instanceof CustomException);
        assertEquals("기입되지 않은 정보가 있습니다", exception.getMessage());

        exception = assertThrows(CustomException.class, ()->{
            UserDto result = userService.updateImgInfo(id, imgPath, null);
        });
        assertTrue(exception instanceof CustomException);
        assertEquals("기입되지 않은 정보가 있습니다", exception.getMessage());
    }

    @Test
    @Transactional
    void 프로필사진업데이트실패_없는사용자(){
        id = 1L;
        imgPath = "imgpath";
        imgUrl = "imgurl";

        when(userRepository.findOneById(id)).thenReturn(Optional.ofNullable(null));

        Exception exception = assertThrows(CustomException.class, ()->{
            UserDto result = userService.updateImgInfo(id, imgPath, imgUrl);
        });

        assertTrue(exception instanceof CustomException);
        assertEquals("없는 사용자입니다.", exception.getMessage());
    }

    @Test
    void 사용자정보조회성공() {
        UserDto reqUserDto = UserDto.builder().id(id).email(email).build();

        when(userRepository.findOneById(id)).thenReturn(Optional.ofNullable(reqUserDto.toEntity()));

        UserDto userDto = userService.getInfo(id);

        assertEquals(email, userDto.getEmail());
    }

    @Test
    void 사용자정보조회실패_없는사용자() {
        id = 150L;

        Exception exception = assertThrows(CustomException.class, ()->{
            UserDto result = userService.getInfo(id);
        });

        assertTrue(exception instanceof CustomException);
        assertEquals("없는 사용자입니다.", exception.getMessage());
    }

    @Test
    void 사용자요약정보조회(){
        UserDto reqUserDto = UserDto.builder().id(id).email(email).build();

        when(userRepository.findOneById(id)).thenReturn(Optional.ofNullable(reqUserDto.toEntity()));

        SummaryUserDto userDto = userService.getSummaryInfo(id);

        assertEquals(email, userDto.getEmail());
    }

    @Test
    void 사용자요약정보조회실패_없는사용자(){
        UserDto reqUserDto = UserDto.builder().id(id).email(email).build();

        when(userRepository.findOneById(id)).thenReturn((Optional.ofNullable(null)));

        Exception exception = assertThrows(CustomException.class, ()->{
            userService.getSummaryInfo(id);
        });

        assertTrue(exception instanceof CustomException);
        assertEquals("없는 사용자입니다.", exception.getMessage());
    }

    @Test
    void 중복되지않는이메일() {
        when(userRepository.findOneByEmail(email)).thenReturn(Optional.ofNullable(null));

        userService.checkEmail(email);
    }

    @Test
    void 중복된이메일() {
        UserDto reqUserDto = UserDto.builder().id(id).email(email).build();

        when(userRepository.findOneByEmail(email)).thenReturn(Optional.ofNullable(reqUserDto.toEntity()));

        Exception exception = assertThrows(CustomException.class, ()->{
            userService.checkEmail(email);
        });

        assertTrue(exception instanceof CustomException);
        assertEquals("이미 존재하는 이메일입니다.", exception.getMessage());
    }

    @Test
    @Transactional
    void 사용자삭제성공() throws ParseException {
        UserDto reqUserDto = UserDto.builder().id(id).build();
        Board board = BoardDto.builder()
                .id(1L).uid(2L).tid(1L).content("content").deadline("2023-01-18 14:31:59")
                .startTime("2023-01-18 14:31:59").endTime("2023-01-18 14:31:59")
                .isFixed(0).maxCnt(5).summary("summary").title("title").build().toEntity();
        Lecture lecture = Lecture.builder()
                .id(1L).board(board).memberCnt(0).build();
        UserBoard userBoard = UserBoardDto.builder()
                .id(1L).bid(board.getId()).uid(reqUserDto.getId()).board(board).user(reqUserDto.toEntity()).userType("강사").build().toEntity();
        UserLecture userLecture = UserLectureDto.builder()
                .id(1L).lid(1L).uid(1L).user(reqUserDto.toEntity()).lecture(lecture).memberType("학생").evaluateStatus(0).build().toEntity();


        List<UserBoard> userBoardList = new ArrayList<UserBoard>();
        userBoardList.add(userBoard);
        List<UserLecture> userLectureList = new ArrayList<UserLecture>();
        userLectureList.add(userLecture);
        List<Board> boardList = new ArrayList<Board>();
        boardList.add(board);

        when(userRepository.findOneById(id)).thenReturn(Optional.ofNullable(reqUserDto.toEntity()));
        when(userBoardRepository.findByUid(id)).thenReturn(userBoardList);
        when(userBoardRepository.save(any(UserBoard.class))).thenReturn(userBoard);
        when(userLectureRepository.findByUser(any(User.class))).thenReturn(userLectureList);
        when(userLectureRepository.save(any(UserLecture.class))).thenReturn(userLecture);
        when(boardRepository.findByUid(id)).thenReturn(boardList);
        when(boardRepository.save(any(Board.class))).thenReturn(board);

        userService.delete(id);
    }

    @Test
    @Transactional
    void 사용자삭제실패_없는사용자() {
        Exception exception = assertThrows(CustomException.class, ()->{
            userService.delete(id);
        });

        assertTrue(exception instanceof CustomException);
        assertEquals("없는 사용자입니다.", exception.getMessage());
    }

    @Test
    @Transactional
    void 포인트수정성공() {
        UserDto reqUserDto = UserDto.builder().id(id).email(email).point(point).build();
        UserDto resUserDto = UserDto.builder().id(id).email(email).point(point+50).build();

        when(userRepository.findOneById(id)).thenReturn(Optional.ofNullable(reqUserDto.toEntity()));
        when(userRepository.save(any(User.class))).thenReturn(resUserDto.toEntity());

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("point", point);
        UserDto userDto = userService.updatePoint(params);

        assertEquals(50, userDto.getPoint() - reqUserDto.getPoint());
    }

    @Test
    @Transactional
    void 포인트수정실패_부족한정보() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        Exception exception = assertThrows(CustomException.class, ()->{
            UserDto userDto = userService.updatePoint(params);
        });

        assertTrue(exception instanceof CustomException);
        assertEquals("기입되지 않은 정보가 있습니다", exception.getMessage());
    }

    @Test
    @Transactional
    void 포인트수정실패_없는사용자() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", 100L);
        params.put("point", point);

        Exception exception = assertThrows(CustomException.class, ()->{
            UserDto userDto = userService.updatePoint(params);
        });

        assertTrue(exception instanceof CustomException);
        assertEquals("없는 사용자입니다.", exception.getMessage());
    }

    @Test
    void 미확정강의조회성공() throws ParseException {
        id = 1L;
        UserDto resUserDto = UserDto.builder().id(id).build();
        List<Board> boards = new ArrayList<>();
        boards.add(BoardDto.builder()
                .id(1L).uid(1L).tid(1L).content("content").deadline("2023-01-18 14:31:59")
                .startTime("2023-01-18 14:31:59").endTime("2023-01-18 14:31:59")
                .isFixed(0).maxCnt(5).summary("summary").title("title").build().toEntity());

        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(resUserDto.toEntity()));
        when(boardRepository.findRequestLecture(id)).thenReturn(boards);

        userService.getRequestLecture(id);
    }

    @Test
    void 미확정강의조회실패_부족한정보() {
        Exception exception = assertThrows(CustomException.class, ()->{
            List<BoardDto> result = userService.getRequestLecture(null);
        });
        assertTrue(exception instanceof CustomException);
        assertEquals("기입되지 않은 정보가 있습니다", exception.getMessage());
    }

    @Test
    void 미확정강의조회실패_없는사용자() {
        id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        Exception exception = assertThrows(CustomException.class, ()->{
            List<BoardDto> result = userService.getRequestLecture(id);
        });

        assertTrue(exception instanceof CustomException);
        assertEquals("없는 사용자입니다.", exception.getMessage());
    }

    @Test
    void 미확정강의조회_방장_성공() throws ParseException {
        id = 1L;
        UserDto resUserDto = UserDto.builder().id(id).build();
        List<Board> boards = new ArrayList<>();
        boards.add(BoardDto.builder()
                .id(1L).uid(1L).tid(1L).content("content").deadline("2023-01-18 14:31:59")
                .startTime("2023-01-18 14:31:59").endTime("2023-01-18 14:31:59")
                .isFixed(0).maxCnt(5).summary("summary").title("title").build().toEntity());

        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(resUserDto.toEntity()));
        when(boardRepository.findRequestLectureByHost(id)).thenReturn(boards);

        userService.getRequestLectureByHost(id);
    }

    @Test
    void 미확정강의조회실패_방장_부족한정보() {
        Exception exception = assertThrows(CustomException.class, ()->{
            List<BoardDto> result = userService.getRequestLectureByHost(null);
        });
        assertTrue(exception instanceof CustomException);
        assertEquals("기입되지 않은 정보가 있습니다", exception.getMessage());
    }

    @Test
    void 미확정강의조회실패_방장_없는사용자() {
        id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        Exception exception = assertThrows(CustomException.class, ()->{
            List<BoardDto> result = userService.getRequestLectureByHost(id);
        });

        assertTrue(exception instanceof CustomException);
        assertEquals("없는 사용자입니다.", exception.getMessage());
    }

    @Test
    void 미확정강의조회_강사_성공() throws ParseException {
        id = 1L;
        UserDto resUserDto = UserDto.builder().id(id).build();
        List<Board> boards = new ArrayList<>();
        boards.add(BoardDto.builder()
                .id(1L).uid(1L).tid(1L).content("content").deadline("2023-01-18 14:31:59")
                .startTime("2023-01-18 14:31:59").endTime("2023-01-18 14:31:59")
                .isFixed(0).maxCnt(5).summary("summary").title("title").build().toEntity());

        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(resUserDto.toEntity()));
        when(boardRepository.findRequestLectureByInst(id)).thenReturn(boards);

        userService.getRequestLectureByInst(id);
    }

    @Test
    void 미확정강의조회실패_강사_부족한정보() {
        Exception exception = assertThrows(CustomException.class, ()->{
            List<BoardDto> result = userService.getRequestLectureByInst(null);
        });
        assertTrue(exception instanceof CustomException);
        assertEquals("기입되지 않은 정보가 있습니다", exception.getMessage());
    }

    @Test
    void 미확정강의조회실패_강사_없는사용자() {
        id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        Exception exception = assertThrows(CustomException.class, ()->{
            List<BoardDto> result = userService.getRequestLectureByInst(id);
        });

        assertTrue(exception instanceof CustomException);
        assertEquals("없는 사용자입니다.", exception.getMessage());
    }

    @Test
    void 미확정강의조회_학생_성공() throws ParseException {
        id = 1L;
        UserDto resUserDto = UserDto.builder().id(id).build();
        List<Board> boards = new ArrayList<>();
        boards.add(BoardDto.builder()
                .id(1L).uid(1L).tid(1L).content("content").deadline("2023-01-18 14:31:59")
                .startTime("2023-01-18 14:31:59").endTime("2023-01-18 14:31:59")
                .isFixed(0).maxCnt(5).summary("summary").title("title").build().toEntity());

        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(resUserDto.toEntity()));
        when(boardRepository.findRequestLectureByStud(id)).thenReturn(boards);

        userService.getRequestLectureByStud(id);
    }

    @Test
    void 미확정강의조회실패_학생_부족한정보() {
        Exception exception = assertThrows(CustomException.class, ()->{
            List<BoardDto> result = userService.getRequestLectureByStud(null);
        });
        assertTrue(exception instanceof CustomException);
        assertEquals("기입되지 않은 정보가 있습니다", exception.getMessage());
    }

    @Test
    void 미확정강의조회실패_학생_없는사용자() {
        id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        Exception exception = assertThrows(CustomException.class, ()->{
            List<BoardDto> result = userService.getRequestLectureByStud(id);
        });

        assertTrue(exception instanceof CustomException);
        assertEquals("없는 사용자입니다.", exception.getMessage());
    }

    @Test
    void 확정강의조회성공() throws ParseException {
        id = 1L;
        UserDto resUserDto = UserDto.builder().id(id).build();

        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(resUserDto.toEntity()));
        when(fixedLectureRepository.findFixedLecture(id)).thenReturn(new ArrayList<>());

        userService.getFixedLecture(id);
    }

    @Test
    void 확정강의조회실패_부족한정보() {
        Exception exception = assertThrows(CustomException.class, ()->{
            List<FixedLectureDto> result = userService.getFixedLecture(null);
        });
        assertTrue(exception instanceof CustomException);
        assertEquals("기입되지 않은 정보가 있습니다", exception.getMessage());
    }

    @Test
    void 확정강의조회실패_없는사용자() {
        id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        Exception exception = assertThrows(CustomException.class, ()->{
            List<FixedLectureDto> result = userService.getFixedLecture(id);
        });

        assertTrue(exception instanceof CustomException);
        assertEquals("없는 사용자입니다.", exception.getMessage());
    }
}