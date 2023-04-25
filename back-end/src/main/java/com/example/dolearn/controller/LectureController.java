package com.example.dolearn.controller;

import com.example.dolearn.dto.LectureDto;
import com.example.dolearn.dto.UserLectureDto;
import com.example.dolearn.exception.CustomException;
import com.example.dolearn.exception.error.ErrorCode;
import com.example.dolearn.response.ErrorResponse;
import com.example.dolearn.response.SuccessResponse;
import com.example.dolearn.service.LectureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/lecture")
@RestController
public class LectureController {

    private final LectureService lectureService;

    @GetMapping("/{lecture_id}")
    public ResponseEntity<?> getLectureDetail(@PathVariable Long lecture_id) {

        log.info("lecture detail 호출!");
        log.info("lecture_id : {}", lecture_id);

        try {
            return new ResponseEntity<>(new SuccessResponse(lectureService.getDetail(lecture_id)), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.NO_MESSSAGE),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateLecture(@RequestBody LectureDto lectureDto){
        try{
            log.info("강의 업데이트 요청: {}",lectureDto);

            LectureDto result = lectureService.updateLecture(lectureDto);
            return new ResponseEntity<>(new SuccessResponse(result),HttpStatus.OK);
        }catch (CustomException e){
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode()), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/instructor/{lid}")
    public ResponseEntity<?> getInstructor(@PathVariable Long lid){
        log.info("강사 찾기 요청: {}",lid);

        try{
            return new ResponseEntity<>(new SuccessResponse(lectureService.getInstructor(lid)), HttpStatus.OK);
        }catch (CustomException e){
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode()), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/fix")
    public ResponseEntity<?> updateFixed(@RequestBody Map<String,Long> idMap){
        try{
            log.info("업데이트 요청: {} {}",idMap.get("bid"),idMap.get("Luid"));
            LectureDto updateBoard = lectureService.updateFix(idMap.get("bid"),idMap.get("Luid"));
            log.info("강의 업데이트 완료: {}",updateBoard);

            return new ResponseEntity<>(new SuccessResponse(updateBoard), HttpStatus.OK);
        }catch (CustomException e){
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode()), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list/{lid}")
    public ResponseEntity<?> getList(@PathVariable Long lid){
        try{
            return new ResponseEntity<>(new SuccessResponse(lectureService.getList(lid)), HttpStatus.OK);
        }catch (CustomException e){
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode()), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/member-update")
    public ResponseEntity<?> updateMember(@RequestBody Map<String,Long> info){
        try{
            UserLectureDto userLectureDto = lectureService.updateLectureMember(info.get("lid"),info.get("uid"));

            return new ResponseEntity<>(new SuccessResponse(userLectureDto),HttpStatus.OK);
        }catch (CustomException e){
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode()), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/apply")
    public ResponseEntity<?> cancelApply(@RequestBody Map<String,Long> info){
        try{
            int result = lectureService.cancelApply(info.get("lid"),info.get("uid"));

            return new ResponseEntity<>(new SuccessResponse("삭제가 완료되었습니다"),HttpStatus.OK);
        }catch (CustomException e){
            return new ResponseEntity<>(new ErrorResponse(e.getErrorCode()), HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
