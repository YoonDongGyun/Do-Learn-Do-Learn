package com.example.dolearn.service;

import com.example.dolearn.domain.Board;
import com.example.dolearn.domain.User;
import com.example.dolearn.domain.UserBoard;
import com.example.dolearn.domain.UserLecture;
import com.example.dolearn.dto.*;
import com.example.dolearn.exception.CustomException;
import com.example.dolearn.exception.error.ErrorCode;
import com.example.dolearn.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserBoardRepository userBoardRepository;

    @Autowired
    private UserLectureRepository userLectureRepository;

    @Autowired
    FixedLectureRepository fixedLectureRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto signup(UserDto reqUserDto){
        if (userRepository.findOneByEmail(reqUserDto.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.EMAIL_DUPLICATION);
        }
        reqUserDto.setPassword(passwordEncoder.encode(reqUserDto.getPassword()));
        return userRepository.save(reqUserDto.toEntity()).toDto();
    }

    public UserDto login(UserDto reqUserDto) {
        Optional<User> user = userRepository.findOneByEmail(reqUserDto.getEmail());
        if(!user.isPresent()){
            throw new CustomException(ErrorCode.NO_USER);
        }
        UserDto userDto = user.get().toDto();
        if (!passwordEncoder.matches(reqUserDto.getPassword(), userDto.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        return userDto;
    }

    public UserDto updateToken(UserDto reqUserDto, String refreshToken, String accessToken) {
        reqUserDto.setRefreshToken(refreshToken);
        reqUserDto.setAccessToken(accessToken);
        userRepository.save(reqUserDto.toEntity());
        return reqUserDto;
    }

    public UserDto logout(Long id) {
        Optional<User> user = userRepository.findOneById(id);
        if(!user.isPresent()){
            throw new CustomException(ErrorCode.NO_USER);
        }
        UserDto userDto = user.get().toDto();
        userDto.setRefreshToken(null);
        return userRepository.save(userDto.toEntity()).toDto();
    }

    public UserDto updateInfo(UserDto reqUserDto){
        if(reqUserDto.getId() == null || reqUserDto.getInfo() == null || reqUserDto.getBlog() == null || reqUserDto.getFacebook() == null || reqUserDto.getInstagram() == null || reqUserDto.getYoutube() == null){
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
        Optional<User> user = userRepository.findOneById(reqUserDto.getId());
        if(!user.isPresent()){
            throw new CustomException(ErrorCode.NO_USER);
        }
        UserDto userDto = user.get().toDto();
        userDto.setInfo(reqUserDto.getInfo());
        userDto.setBlog(reqUserDto.getBlog());
        userDto.setInstagram(reqUserDto.getInstagram());
        userDto.setFacebook(reqUserDto.getFacebook());
        userDto.setYoutube(reqUserDto.getYoutube());
        return userRepository.save(userDto.toEntity()).toDto();
    }

    public UserDto updateImgInfo(Long id, String imgPath, String imgUrl){
        if(id == null || imgPath == null || imgUrl == null){
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
        Optional<User> user = userRepository.findOneById(id);
        if(!user.isPresent()){
            throw new CustomException(ErrorCode.NO_USER);
        }
        UserDto userDto = user.get().toDto();
        userDto.setImgPath(imgPath);
        userDto.setImgUrl(imgUrl);
        return userRepository.save(userDto.toEntity()).toDto();
    }

    public UserDto getInfo(Long id){
        Optional<User> user = userRepository.findOneById(id);
        if(!user.isPresent()){
            throw new CustomException(ErrorCode.NO_USER);
        }
        return user.get().toDto();
    }

    public List<Long> getAllId(){
        List<Long> id = new ArrayList<>();
        for(UserIdMapping uim : userRepository.findAllBy(Sort.by(Sort.Direction.ASC, "id"))){
            id.add(uim.getId());
        }
        return id;
    }

    public SummaryUserDto getSummaryInfo(Long id){
        Optional<User> user = userRepository.findOneById(id);
        if(!user.isPresent()){
            throw new CustomException(ErrorCode.NO_USER);
        }
        UserDto detailUser = user.get().toDto();
        SummaryUserDto summUser = SummaryUserDto.builder()
                .name(detailUser.getName())
                .email(detailUser.getEmail())
                .info(detailUser.getInfo())
                .point(detailUser.getPoint())
                .youtube(detailUser.getYoutube())
                .instagram(detailUser.getInstagram())
                .facebook(detailUser.getFacebook())
                .blog(detailUser.getBlog())
                .imgUrl(detailUser.getImgUrl())
                .build();
        return summUser;
    }

    public void checkEmail(String email){
        if(userRepository.findOneByEmail(email).isPresent()) {
            throw new CustomException(ErrorCode.EMAIL_DUPLICATION);
        }
    }

    @Transactional
    public void delete(Long id) throws ParseException {
        Optional<User> user = userRepository.findOneById(id);
        if(!user.isPresent()){
            throw new CustomException(ErrorCode.NO_USER);
        }
        List<UserBoard> userBoards = userBoardRepository.findByUid(id);
        UserBoardDto userBoardDto;
        for(UserBoard userBoard : userBoards){
            userBoardDto = userBoard.toDto();
            userBoardDto.setUid(null);
            userBoardDto.setUser(null);
            userBoardRepository.save(userBoardDto.toEntity());
        }
        List<UserLecture> userLectures = userLectureRepository.findByUser(user.get());
        UserLectureDto userLectureDto;
        for(UserLecture userLecture : userLectures){
            userLectureDto = userLecture.toDto();
            userLectureDto.setUid(null);
            userLectureDto.setUser(null);
            userLectureRepository.save(userLectureDto.toEntity());
        }
        List<Board> boards = boardRepository.findByUid(id);
        BoardDto boardDto;
        for(Board board : boards){
            boardDto = board.toDto();
            boardDto.setUid(null);
            boardRepository.save(boardDto.toEntity());
        }
        userRepository.delete(user.get());
    }

    public UserDto updatePoint(Map<String, Object> params){
        if(params.get("id") == null || params.get("point") == null) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
        Long id = Long.parseLong(String.valueOf(params.get("id")));
        Integer point = (Integer) params.get("point");
        Optional<User> user = userRepository.findOneById(id);
        if(!user.isPresent()){
            throw new CustomException(ErrorCode.NO_USER);
        }
        UserDto userDto = user.get().toDto();
        userDto.setPoint(userDto.getPoint() + point);
        return userRepository.save(userDto.toEntity()).toDto();
    }

    public List<BoardDto> getRequestLecture(Long id) {
        if(id == null){
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new CustomException(ErrorCode.NO_USER);
        }
        List<Board> reqBoardEntity = boardRepository.findRequestLecture(id);
        List<BoardDto> reqBoardDto = new ArrayList<>();
        BoardDto boardDto;
        for(Board bEntity: reqBoardEntity){
            boardDto = bEntity.toDto();
            boardDto.setInstructors(userBoardRepository.findInstructors(bEntity.getId()).size());
            boardDto.setStudents(userBoardRepository.findStudents(bEntity.getId()).size());
            reqBoardDto.add(boardDto);
        }
        return reqBoardDto;
    }

    public List<BoardDto> getRequestLectureByHost(Long id) throws ParseException {
        if(id == null){
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new CustomException(ErrorCode.NO_USER);
        }
        List<Board> reqBoardEntity = boardRepository.findRequestLectureByHost(id);
        List<BoardDto> reqBoardDto = new ArrayList<>();
        BoardDto boardDto;
        for(Board bEntity: reqBoardEntity){
            boardDto = bEntity.toDto();
            boardDto.setInstructors(userBoardRepository.findInstructors(bEntity.getId()).size());
            boardDto.setStudents(userBoardRepository.findStudents(bEntity.getId()).size());
            reqBoardDto.add(boardDto);
        }
        return reqBoardDto;
    }

    public List<BoardDto> getRequestLectureByInst(Long id) throws ParseException {
        if(id == null){
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new CustomException(ErrorCode.NO_USER);
        }
        List<Board> reqBoardEntity = boardRepository.findRequestLectureByInst(id);
        List<BoardDto> reqBoardDto = new ArrayList<>();
        BoardDto boardDto;
        for(Board bEntity: reqBoardEntity){
            boardDto = bEntity.toDto();
            boardDto.setInstructors(userBoardRepository.findInstructors(bEntity.getId()).size());
            boardDto.setStudents(userBoardRepository.findStudents(bEntity.getId()).size());
            reqBoardDto.add(boardDto);
        }
        return reqBoardDto;
    }

    public List<BoardDto> getRequestLectureByStud(Long id) throws ParseException {
        if(id == null){
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new CustomException(ErrorCode.NO_USER);
        }
        List<Board> reqBoardEntity = boardRepository.findRequestLectureByStud(id);
        List<BoardDto> reqBoardDto = new ArrayList<>();
        BoardDto boardDto;
        for(Board bEntity: reqBoardEntity){
            boardDto = bEntity.toDto();
            boardDto.setInstructors(userBoardRepository.findInstructors(bEntity.getId()).size());
            boardDto.setStudents(userBoardRepository.findStudents(bEntity.getId()).size());
            reqBoardDto.add(boardDto);
        }
        return reqBoardDto;
    }

    public List<FixedLectureDto> getFixedLecture(Long id) throws ParseException {
        if(id == null){
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new CustomException(ErrorCode.NO_USER);
        }
        List<FixedLectureDto> reqBoard = fixedLectureRepository.findFixedLecture(id);
        return reqBoard;
    }

    public List<UserDto> getAllSortByPoint(){
        List<User> users = userRepository.findAll(Sort.by(Sort.DEFAULT_DIRECTION.DESC, "point", "joinDate"));
        List<UserDto> userDtos = new ArrayList<>();
        for(User user: users){
            userDtos.add(user.toDto());
        }
        return userDtos;
    }
}
