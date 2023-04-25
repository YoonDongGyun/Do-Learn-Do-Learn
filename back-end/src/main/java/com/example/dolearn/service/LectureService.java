package com.example.dolearn.service;

import com.example.dolearn.domain.*;
import com.example.dolearn.dto.BoardDto;
import com.example.dolearn.dto.LectureDto;
import com.example.dolearn.dto.UserLectureDto;
import com.example.dolearn.exception.CustomException;
import com.example.dolearn.exception.error.ErrorCode;
import com.example.dolearn.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class LectureService {

    private final LectureRepository lectureRepository;

    private final UserLectureRepository userLectureRepository;

    private final BoardRepository boardRepository;

    private final UserBoardRepository userBoardRepository;

    private final UserRepository userRepository;

    private final MessageRepository messageRepository;

    public LectureDto getDetail(Long lecture_id) {
        Optional<Lecture> lecture = lectureRepository.findById(lecture_id);

        //null이 아니면
        if(lecture.isPresent()) {

            return lecture.get().toMessageDto();
        }

        throw new CustomException(ErrorCode.NO_MESSSAGE);
    }

    public Long getInstructor(Long lid){
        List<UserLecture> userLectureList = userLectureRepository.searchLecture(lid);
        Long instructorId =0L;

        if(userLectureList.isEmpty()) throw new CustomException(ErrorCode.NO_LECTURE);

        for(UserLecture userLecture: userLectureList){
            log.info("참가자: {}",userLecture);
            if(userLecture.getMemberType().equals("강사")){
                instructorId=userLecture.getUser().getId();
                log.info("강사 id: {}",instructorId);
            }
        }
        return instructorId;
    }

    public LectureDto save(Lecture lecture){
        return lectureRepository.save(lecture).toDto();
    }

    public UserLectureDto save(UserLecture userLecture) {
        return userLectureRepository.save(userLecture).toDto();
    }

    @Transactional
    public LectureDto updateFix(Long bid, Long Luid) throws Exception{
        Optional<Board> result = boardRepository.findById(bid); //수정할 글 읽어오기

        if(result.isEmpty()) throw new CustomException(ErrorCode.NO_BOARD);//수정할 글이 없는 경우 오류 발생

        if(result.get().getIsFixed()==1) throw new CustomException(ErrorCode.FIXED_LECTURE); //이미 확정된 강의의 경우 오류 발생

        //bid에 해당하는 학생 알림 보내기

        BoardDto board = result.get().toDto();//수정할 글 dto로 변환
        board.setFixed(1);//is_fixed 1로 변환

        Board updatedBoard = boardRepository.save(board.toEntity());//수정한 정보 저장

        Lecture lecture = Lecture.builder()
                .memberCnt(0).board(updatedBoard).build(); //Lecture 생성

        Lecture updatedLecture = lectureRepository.save(lecture);//생성되었던 lecture 저장

        List<UserBoard> applicantList = userBoardRepository.findStudents(bid); //신청했던 학생 목록 가져오기
        Optional<User> lecturerData = userRepository.findOneById(Luid); //강사의 User 데이터 가져오기

        if(lecturerData.isEmpty()) throw new CustomException(ErrorCode.NO_USER); //강사 데이터가 없는 경우 에러 발생

        UserBoard lecturer = UserBoard.builder()
                .uid(Luid).user(lecturerData.get()).board(updatedBoard).userType("강사").build();//UserBoard 형식으로 강사 데이터 구성

        applicantList.add(lecturer);//목록에 강사 추가

        for(UserBoard userBoard: applicantList){
            UserLecture userLecture = UserLecture.builder()
                    .user(userBoard.getUser()).lecture(updatedLecture).evaluateStatus(0).memberType(userBoard.getUserType()).evaluateStatus(0).build();//member_board table에 저장하기 위해 UserLecture로 재구성

            userLectureRepository.save(userLecture);//member_board table에 저장
        }
        return updatedLecture.toDto();//확정된 강의 반환
    }

    public LectureDto updateLecture(LectureDto lectureDto){
        Optional<Lecture> lecture = lectureRepository.findById(lectureDto.getId());

        if(lecture.isEmpty()) throw new CustomException(ErrorCode.NO_LECTURE);

        Lecture updateLecture = lecture.get();

        updateLecture.setMemberCnt(lectureDto.getMemberCnt());
        updateLecture.setStartRealTime(lectureDto.getStartRealTime());
        updateLecture.setEndRealTime(lectureDto.getEndRealTime());

        return lectureRepository.save(updateLecture).toDto();
    }

    public UserLectureDto updateLectureMember(Long lid, Long uid){
        UserLecture userLecture = userLectureRepository.searchLectureMember(lid,uid);

        userLecture.setEvaluateStatus(1);

        return userLectureRepository.save(userLecture).toDto();
    }

    public List<UserLecture> getList(Long lid){
        return userLectureRepository.searchLecture(lid);
    }

    public int deleteLecture(Long lid){
        Optional<Lecture> lecture = lectureRepository.findById(lid);

        if(lecture.isEmpty()) throw new CustomException(ErrorCode.NO_LECTURE);

        try{
            lectureRepository.deleteById(lid);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    @Transactional
    public int cancelApply(Long lid, Long uid){
        UserLecture userLecture = userLectureRepository.searchLectureMember(lid, uid);
        Optional<Lecture> updateLecture = lectureRepository.findById(lid);

        if(userLecture==null || updateLecture.isEmpty()) throw new CustomException(ErrorCode.NO_APPLICANT);

        if(userLecture.getMemberType().equals("강사")){

            userLectureRepository.deleteByLid(lid);

            int result = deleteLecture(lid);

            log.info("Lecture 삭제 :{}",result);

            if(result ==0) throw new CustomException(ErrorCode.NO_LECTURE);

        }

        return userLectureRepository.deleteLectureMember(lid,uid);
    }

}
