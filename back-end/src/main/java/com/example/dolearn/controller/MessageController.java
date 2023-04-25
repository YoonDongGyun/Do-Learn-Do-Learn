package com.example.dolearn.controller;

import com.example.dolearn.dto.MessageDto;
import com.example.dolearn.exception.error.ErrorCode;
import com.example.dolearn.response.ErrorResponse;
import com.example.dolearn.response.SuccessResponse;
import com.example.dolearn.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@Slf4j
public class MessageController {

    private MessageService messageService;
    private String success = "SUCCESS";
    private String fail = "FAIL";

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    //강의가 확정되면 수강 신청한 학생들에게 전송
    @PostMapping
    public ResponseEntity<?> createMessage(@RequestBody MessageDto messageDto) {

        log.info("create message 호출");
        log.info("content : {}",messageDto.getContent());
        log.info("bid : {}", messageDto.getBid());

        //메세지 생성하는 메소드
        try {
            return new ResponseEntity<>(new SuccessResponse(messageService.createMessage(messageDto)), HttpStatus.CREATED);
        } catch(Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.NO_MESSSAGE),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateCheck(@RequestBody MessageDto messageDto) {
        //확인표시로 업데이트
        log.info("update message 호출");
        log.info("rid : {}", messageDto.getRid());

        try {
            messageService.updateCheck(messageDto);
            return new ResponseEntity<>(success, HttpStatus.OK);

        } catch(Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.NO_MESSSAGE),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getMessageList(@PathVariable Long user_id) {

        log.info("getMessageList 호출");
        log.info("user id : {}",user_id);

        try {
            return new ResponseEntity<>(new SuccessResponse(messageService.getMessageList(user_id)), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.NO_MESSSAGE),
                    HttpStatus.NOT_FOUND);
        }
    }
    //아직 수신되지 않은 메시지 가져오기
    @GetMapping("/uncheck/user/{user_id}")
    public ResponseEntity<?> getUncheckMessageList(@PathVariable Long user_id) {

        log.info("getUncheckMessageList 호출");
        log.info("user id : {}",user_id);

        try {
            return new ResponseEntity<>(new SuccessResponse(messageService.getUnCheckMessageList(user_id)), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.NO_MESSSAGE),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{message_id}")
    public ResponseEntity<?> getMessageDetail(@PathVariable long message_id) {
        log.info("message detail 호출!");
        log.info("message id : {}", message_id);

        try {
            return new ResponseEntity<>(new SuccessResponse(messageService.getMessage(message_id)), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.NO_MESSSAGE),
                                        HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{message_id}")
    public ResponseEntity<?> deleteMessage(@PathVariable long message_id) {
        log.info("message 삭제 호출!");
        log.info(" message id : {}", message_id);

        try {
            messageService.deleteMessage(message_id);
            return new ResponseEntity<>(new SuccessResponse(success), HttpStatus.OK);

        } catch(Exception e) {
            return new ResponseEntity<>(new ErrorResponse(ErrorCode.NO_MESSSAGE),
                                        HttpStatus.NOT_FOUND);
        }
    }
}
