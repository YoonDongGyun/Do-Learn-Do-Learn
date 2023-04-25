package com.example.dolearn.controller;

import com.example.dolearn.config.SecurityConfig;
import com.example.dolearn.dto.MessageDto;
import com.example.dolearn.exception.CustomException;
import com.example.dolearn.exception.error.ErrorCode;
import com.example.dolearn.jwt.JwtTokenProvider;
import com.example.dolearn.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MessageController.class)
@WithMockUser
public class MessageControllerTest {

    @MockBean
    private MessageService messageService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @DisplayName("메세지 생성 테스트")
    @Test
    public void messageCreateTest() throws Exception {

        MessageDto messageDto = MessageDto.builder()
                                .id(1L)
                                .rid(1L)
                                .content("강의 확정 완료")
                                .isChecked(1).build();

        MessageDto messageDto1 = MessageDto.builder()
                .rid(1L)
                .content("강의 확정 완료")
                .isChecked(1).build();

        MessageDto messageDto2 = MessageDto.builder()
                .rid(1L)
                .content("강의 확정 완료")
                .isChecked(1).build();

        MessageDto messageDto3 = MessageDto.builder()
                .rid(1L)
                .content("강의 확정 완료")
                .isChecked(1).build();

        List<MessageDto> result = new ArrayList<>();
        result.add(messageDto1);
        result.add(messageDto2);
        result.add(messageDto3);

        when(messageService.createMessage(any(MessageDto.class))).thenReturn(result);

        mockMvc.perform(post("/api/message").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(messageDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.response", hasSize(result.size())));

    }

    @DisplayName("메세지 생성시 에러 테스트")
    @Test
    public void messageCreateErrorTest() throws Exception {

        MessageDto messageDto = MessageDto.builder()
                .id(1L)
                .rid(1L)
                .content("강의 확정 완료")
                .isChecked(1).build();

        when(messageService.createMessage(any(MessageDto.class))).thenThrow(new CustomException(ErrorCode.NO_MESSSAGE));

        mockMvc.perform(post("/api/message")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
                .content(new ObjectMapper().writeValueAsString(messageDto)))
                .andExpect(status().isNotFound());
    }

    @DisplayName("메세지 확인상태 업데이트 테스트")
    @Test
    public void messageUpdateTest() throws Exception {
        MessageDto messageDto = MessageDto.builder()
                .id(1L)
                .rid(1L)
                .content("test")
                .isChecked(1).build();

        mockMvc.perform(put("/api/message").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(messageDto)))
                .andExpect(status().isOk());
    }

    @DisplayName("메세지 목록 가져오기 테스트")
    @Test
    public void messageListTest() throws Exception {

        List<MessageDto> messageDtoList = new ArrayList<>();

        MessageDto messageDto1 = new MessageDto().builder()
                                                .id(1L)
                                                .content("hello").build();

        MessageDto messageDto2 = new MessageDto().builder()
                .id(2L)
                .content("hello").build();

        MessageDto messageDto3 = new MessageDto().builder()
                .id(3L)
                .content("hello").build();

        messageDtoList.add(messageDto1);
        messageDtoList.add(messageDto2);
        messageDtoList.add(messageDto3);

        when(messageService.getMessageList(anyLong())).thenReturn(messageDtoList);

        mockMvc.perform(get("/api/message/{user_id}",1L))
                .andExpect(status().isOk());
    }

    @DisplayName("메세지 목록 가져오기 에러 테스트")
    @Test
    public void getMessageListErrorTest() throws Exception {

        when(messageService.getMessageList(anyLong())).thenThrow(new CustomException(ErrorCode.NO_MESSSAGE));

        mockMvc.perform(get("/api/message/user/1"))
                .andExpect(status().isNotFound());
    }

    @DisplayName("메세지 디테일 반환 테스트")
    @Test
    public void getMessageDetailTest() throws Exception {

        MessageDto messageDto = MessageDto.builder()
                                        .content("test")
                                        .id(1L)
                                        .rid(1L)
                                        .isChecked(0).build();

        when(messageService.getMessage(anyLong())).thenReturn(messageDto);

        mockMvc.perform(get("/api/message/{message_id}",1))
                .andExpect(status().isOk());
    }

    @DisplayName("메세지 디테일 에러스트")
    @Test
    public void getMessageDetailErrorTest() throws Exception {

        when(messageService.getMessage(anyLong())).thenThrow(new CustomException(ErrorCode.NO_MESSSAGE));

        mockMvc.perform(get("/api/message/1"))
                .andExpect(status().isNotFound());
    }

    @DisplayName("메세지 삭제 테스트")
    @Test
    public void messageDeleteTest() throws Exception {

        mockMvc.perform(delete("/api/message/{message_id}",1).with(csrf()))
                .andExpect(status().isOk());
    }

    @DisplayName("특정 유저의 메세지 수신 리트스 얻기 테스트")
    @Test
    public void getMessageListTest() throws Exception {

        MessageDto messageDto1 = MessageDto.builder().id(1L).rid(1L).content("test1").build();
        MessageDto messageDto2 = MessageDto.builder().id(2L).rid(1L).content("test2").build();
        MessageDto messageDto3 = MessageDto.builder().id(3L).rid(1L).content("test3").build();

        List<MessageDto> mList = new ArrayList<>();
        mList.add(messageDto1);
        mList.add(messageDto2);
        mList.add(messageDto3);

        when(messageService.getMessageList(anyLong())).thenReturn(mList);

        mockMvc.perform(get("/api/message/user/{user_id}",1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response", hasSize(mList.size())));
    }

    @DisplayName("읽지 않은 메세지 반환")
    @Test
    public void getUncheckedMessageList() throws  Exception {
        MessageDto messageDto1 = MessageDto.builder().rid(1L).isChecked(0).content("test1").build();
        MessageDto messageDto2 = MessageDto.builder().rid(1L).isChecked(0).content("test3").build();

        List<MessageDto> mList = new ArrayList<>();

        mList.add(messageDto1);
        mList.add(messageDto2);

        when(messageService.getUnCheckMessageList(anyLong())).thenReturn(mList);

        mockMvc.perform(get("/api/message/uncheck/user/1"))
                .andExpect(status().isOk());
    }

}
