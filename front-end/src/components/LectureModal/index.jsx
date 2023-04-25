import React, { useContext, useState } from "react";
import Box from "@mui/material/Box";
import Modal from "@mui/material/Modal";
import * as S from "./styles";
import * as f from "@fortawesome/free-solid-svg-icons";
import LecturerList from "../LecturerList";
import LectureModalButton from "../LectureModalButton";
import { LoginStateContext } from "../../App";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  bgcolor: "background.paper",
  borderRadius: "8px",
  boxShadow: 24,
  outline: "none",
  padding: "3vw",
};

const customPostingTime = (start, end) => {
  const startDate = new Date(start);
  const endDate = new Date(end);
  const startYear = startDate.getFullYear().toString().slice(-2);
  const startMonth = (startDate.getMonth() + 1).toString().padStart(2, "0");
  const startDay = startDate.getDate().toString().padStart(2, "0");
  const endYear = endDate.getFullYear().toString().slice(-2);
  const endMonth = (endDate.getMonth() + 1).toString().padStart(2, "0");
  const endDay = endDate.getDate().toString().padStart(2, "0");

  const custom = `${startYear}.${startMonth}.${startDay} ~ ${endYear}.${endMonth}.${endDay}`;
  return custom;
};

const customLecTime = (start, end) => {
  const startDate = new Date(start);
  const endDate = new Date(end);
  const year = startDate.getFullYear().toString().slice(-2);
  const month = (startDate.getMonth() + 1).toString().padStart(2, "0");
  const day = startDate.getDate().toString().padStart(2, "0");
  const time = startDate.toLocaleTimeString("ko-KR", {
    hour: "numeric",
    minute: "numeric",
    hour12: false,
  });
  let remain = endDate.getHours() - startDate.getHours();
  if (remain < 0) remain += 24;
  const custom = `${year}.${month}.${day} ${time} (${remain}시간)`;
  return custom;
};

// Uniboard에서 데이터 받아올 것
const LectureModal = ({ data, open, setOpen, handleClose }) => {
  const { userInfo } = useContext(LoginStateContext);
  const [Luid, setLuid] = useState("none"); // 확정 강사 id 저장
  const postingTime = customPostingTime(data.createdTime, data.deadline);
  const lecTime = customLecTime(data.startTime, data.endTime);

  return (
    <Modal
      open={open}
      onClose={handleClose}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <Box sx={style}>
        <S.SNoBtnBox>
          <S.SContentBox>
            {/* 1. 제목 */}
            <S.STitle>{data.title}</S.STitle>
            <div id="modal-modal-description" sx={{ mt: 2 }}>
              <S.SInfoItem>
                {/* 3. 모집 기간 */}
                <S.SCalendar icon={f.faCalendarDays} />
                <S.SSpan>
                  <b>모집 기간</b> | {postingTime}
                </S.SSpan>
              </S.SInfoItem>
              <S.SInfoItem>
                {/* 4. 강의 시간 */}
                <S.SClock icon={f.faClock} />
                <S.SSpan>
                  <b>강의 시간</b> | {lecTime}
                </S.SSpan>
              </S.SInfoItem>
              <S.SInfoItem>
                {/* 5. 강사 신청 현황 */}
                <S.SLecturer icon={f.faPersonChalkboard} />
                <S.SSpan>
                  <b>강사 신청 현황</b> | {data.instructors} 명
                </S.SSpan>
              </S.SInfoItem>
              <S.SInfoItem>
                {/* 6. 수강생 신청 현황 */}
                <S.SStudent icon={f.faUsers} />
                <S.SSpan>
                  <b>수강생 신청 현황</b> | {data.students} / {data.maxCnt} 명
                </S.SSpan>
              </S.SInfoItem>
              <S.SInfoItem>
                {/* 7. 강의 디테일 */}
                <S.SPencil icon={f.faFileLines} />
                <S.SSpan>
                  <b>강의 상세 내용</b>
                </S.SSpan>
              </S.SInfoItem>
              <S.SDetail>{data.content}</S.SDetail>
            </div>
          </S.SContentBox>
          {/* 8. 여기는 각 경우에 따른 추가 컴포넌트 띄우는 곳 */}
          <div>
            {data.uid === userInfo.id && data.isFixed === 0 ? (
              <LecturerList data={data} setLuid={setLuid} />
            ) : (
              ""
            )}
          </div>
        </S.SNoBtnBox>
        <LectureModalButton Luid={Luid} data={data} setOpen={setOpen} />
      </Box>
    </Modal>
  );
};

export default LectureModal;
