package com.example.dolearn.service;

import com.example.dolearn.domain.*;
import com.example.dolearn.dto.BoardDto;
import com.example.dolearn.exception.CustomException;
import com.example.dolearn.exception.error.ErrorCode;
import com.example.dolearn.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserBoardRepository userBoardRepository;

    @Transactional
    public BoardDto insert(BoardDto boardDto) throws Exception {
        boardDto.setTimes(); //date format에 맞춰 data 수정
        Board board =boardDto.toEntity();

        BoardDto result = boardRepository.save(board).toDto(); //글 등록

        Optional<User> userData = userRepository.findOneById(board.getUid()); //작성자에 대한 데이터 받음

        if(userData.isEmpty()) throw new CustomException(ErrorCode.NO_USER); //작성자 데이터가 없는 경우 에러 발생

        User user = userData.get(); //엔티티에 user data 받음

        UserBoard userBoard = UserBoard.builder()
                .uid(board.getUid()).bid(board.getId()).user(user).board(board).userType("학생").build(); //UserBoard 엔티티에 저장

        userBoardRepository.save(userBoard);// member_board table에 작성자 저장

        log.info("저장 요청: {}",board);
        log.info("작성자 정보 저장: {}",userBoard);

        return result;
    }

    @Transactional
    public List<BoardDto> selectAll() throws Exception{
        List<Board> boardList = boardRepository.findAll(); //글 리스트 받아오기
        List<BoardDto> boardDtoList = new ArrayList<>(); //Dto list 생성

        for(int i=boardList.size()-1;i>=0;i--){ //id 높은 순으로 리스트 구성 (최근에 올린 글 순으로 정렬하기 위해)
            if(boardList.get(i).getIsFixed()==1) continue; //이미 확정된 강의의 경우 리스트에서 제외
            BoardDto boardDto = boardList.get(i).toDto();//dto로 변환

            if(boardDto.checkDeadline()>0){ //모집기간이 지난 경우
                deleteBoard(boardList.get(i).getId()); //board table에서 삭제
            }
            boardDtoList.add(boardDto);//변환된 dto 리스트에 추가
        }

        return boardDtoList;
    }

    @Transactional
    public BoardDto selectDetail(Long id){
        Optional<Board> board = boardRepository.findById(id);
        if(board.isEmpty()) throw new CustomException(ErrorCode.NO_BOARD); //글이 없는 경우 오류 발생

        BoardDto result = board.get().toDto(); //받아온 상세정보 dto로 변환

        return result;
    }

    @Transactional
    public int deleteBoard(Long id){
//        if(boardRepository.findById(id).isEmpty()) return 0;
        userBoardRepository.deleteByBid(id);
        return boardRepository.deleteBoard(id);
    }

    public List<BoardDto> searchResult(List<Board> bListByTitle, List<Board> bListByContent, List<Board> bListBySummary){
        List<BoardDto> result = new ArrayList<>();

        Set<Board> set = new LinkedHashSet<>(bListByTitle); //검색결과 중복 방지를 위해 set 선언 후 각 리스트 set에 추가
        set.addAll(bListByContent);
        set.addAll(bListBySummary);

        List<Board> bListResult = new ArrayList<>(set); //list로 변환

        for(int i=bListResult.size()-1;i>=0;i--){ //id 높은 순으로 정렬 (최근에 쓴 글 순으로 정렬)
            if(bListResult.get(i).getIsFixed()==1) continue; //확정된 강의 제외
            result.add(bListResult.get(i).toDto());
        }

        return result;
    }

    @Transactional
    public List<Board> searchBoardTitle(String keyword){
        return boardRepository.findByTitleContaining(keyword);
    }

    @Transactional
    public List<Board> searchBoardContent(String keyword){
        return boardRepository.findByContentContaining(keyword);
    }

    @Transactional
    public List<Board> searchBoardSummary(String keyword){
        return boardRepository.findBySummaryContaining(keyword);
    }

}