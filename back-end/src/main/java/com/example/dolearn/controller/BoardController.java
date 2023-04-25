package com.example.dolearn.controller;

import com.example.dolearn.domain.Board;
import com.example.dolearn.domain.UserBoard;
import com.example.dolearn.dto.BoardDto;
import com.example.dolearn.dto.UserBoardDto;
import com.example.dolearn.exception.CustomException;
import com.example.dolearn.exception.error.ErrorCode;
import com.example.dolearn.response.ErrorResponse;
import com.example.dolearn.response.SuccessResponse;
import com.example.dolearn.service.BoardService;
import com.example.dolearn.service.UserBoardService;
import com.example.dolearn.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/board")
@Slf4j
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserBoardService userBoardService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody BoardDto boardDto){
        try {
            Board board = boardService.insert(boardDto).toEntity();
            log.info("글 등록: {}",boardDto);

            return new ResponseEntity<>(new SuccessResponse(board), HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> selectAll(){
        try {
            List<BoardDto> boardDtoList = boardService.selectAll();

            for(BoardDto boardDto:boardDtoList){
                boardDto.setCounts(userBoardService.getInstructors(boardDto.getId()).size(),userBoardService.getStudents(boardDto.getId()).size());
            }

            return new ResponseEntity<>(new SuccessResponse(boardDtoList),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetails(@PathVariable Long id){
        try{
            BoardDto boardDto = boardService.selectDetail(id);

            log.info("글 상세정보 조회: {}",boardDto);

            return new ResponseEntity<>(new SuccessResponse(boardDto),HttpStatus.OK);
        }catch (CustomException e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.NO_BOARD), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            int result = boardService.deleteBoard(id);

            log.info("삭제할 id: {}",id);

            return new ResponseEntity<>(new SuccessResponse("삭제가 완료되었습니다!!"),HttpStatus.OK);
        }catch (CustomException e){
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode()), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/instructor-list/{bid}")
    public ResponseEntity<?> getInstructors(@PathVariable Long bid){
        try{
            log.info("강사 목록 가져오기 요청: {}",bid);
            List<UserBoard> applicants= userBoardService.getInstructors(bid);
            log.info("목록: {}",applicants);

            if(applicants.isEmpty()) return new ResponseEntity<>(new SuccessResponse("신청한 강사가 없습니다"),HttpStatus.OK);
            return new ResponseEntity<>(new SuccessResponse(applicants),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/student-list/{bid}")
    public ResponseEntity<?> getStudents(@PathVariable Long bid){
        try{
            log.info("학생 목록 가져오기 요청: {}",bid);
            List<UserBoard> applicants = userBoardService.getStudents(bid);

            return new ResponseEntity<>(new SuccessResponse(applicants),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> search(@PathVariable String keyword){
        try{
            List<Board> bListByTitle = boardService.searchBoardTitle(keyword);
            List<Board> bListByContent = boardService.searchBoardContent(keyword);
            List<Board> bListBySummary = boardService.searchBoardSummary(keyword);

            List<BoardDto> result = boardService.searchResult(bListByTitle,bListByContent,bListBySummary);

            for(BoardDto boardDto: result){
                boardDto.setCounts(userBoardService.getInstructors(boardDto.getId()).size(),userBoardService.getStudents(boardDto.getId()).size());
            }

            return new ResponseEntity<>(new SuccessResponse(result),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/student")
    public ResponseEntity<?> applyClass(@RequestBody Map <String, Long> userApplyInfo){
        try{
            Long uid = userApplyInfo.get("uid");
            Long bid = userApplyInfo.get("bid");

            log.info("수강신청: {}, {}",uid,bid);

            UserBoard userBoard = UserBoard.builder().bid(bid).uid(uid).userType("학생").build();
            UserBoardDto result = userBoardService.applyClass(userBoard);

            if(result==null) throw new Exception();

            return new ResponseEntity<>(new SuccessResponse("강의 신청이 완료되었습니다!!"),HttpStatus.OK);
        }catch (CustomException e){
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode()), HttpStatus.CONFLICT);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/instructor")
    public ResponseEntity<?> applyInstructor(@RequestBody Map<String, Long> userApplyInfo){
        try{
            Long uid = userApplyInfo.get("uid");
            Long bid = userApplyInfo.get("bid");

            log.info("강의신청: {}, {}",uid,bid);

            UserBoard userBoard = UserBoard.builder().uid(uid).bid(bid).userType("강사").build();
            userBoardService.applyClass(userBoard);

            return new ResponseEntity<>(new SuccessResponse("강사 신청이 완료되었습니다!!"),HttpStatus.OK);
        }catch (CustomException e){
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode()), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("apply/{uid}/{bid}")
    public ResponseEntity<?> cancelApply(@PathVariable Long uid, @PathVariable Long bid){
        try{
            log.info("삭제요청: {}, {}",uid,bid);
            int result = userBoardService.cancelApply(uid,bid);

            return new ResponseEntity<>(new SuccessResponse("강의 신청 취소가 완료되었습니다!!"),HttpStatus.OK);
        }catch (CustomException e){
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode()), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
