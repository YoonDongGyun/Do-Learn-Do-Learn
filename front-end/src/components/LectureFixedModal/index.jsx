import React, { useContext, useState } from "react";
import { Box, Modal, Tooltip, Typography } from "@mui/material";
import {
  faClock,
  faFileLines,
  faPersonChalkboard,
  faUsers,
} from "@fortawesome/free-solid-svg-icons";
import { SButtonBox, SButton } from "../LectureModalButton/styles";
import defaultProfile from "../../assets/images/defaultProfile.png";
import {
  SCustomFontAwesomeIcon,
  SSpan,
  SInfoItem,
  SContent,
  SDetail,
} from "./styles";
import {
  LoginStateContext,
  LoginStateHandlerContext,
  UnreadMessageContext,
} from "../../App";
import { cancleFixedLectureAPI } from "../../utils/api/lectureAPI";
import WarningModal from "../WarningModal";
import { useNavigate } from "react-router";
import { BASE_URL } from "../../utils/api/URL";
import { sendCnacleMessageAPI } from "../../utils/api/messageAPI";

const customLecTime = (startTime, endTime) => {
  const startDate = new Date(startTime);
  const endDate = new Date(endTime);
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

const checkButtonState = (endTime) => {
  const today = new Date();
  const endDate = new Date(endTime);

  // 지금 시간보다 강의 마감 시간이 이르다면 버튼 사라지게
  if (endDate < today) {
    return false;
  } else {
    return true;
  }
};

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: "35vw",
  bgcolor: "background.paper",
  borderRadius: "8px",
  boxShadow: 24,
  outline: "none",
  padding: "20px 30px",
};

const LectureFixedModal = ({
  open,
  handleClose,
  lectureInfo,
  instructorInfo,
  studentsInfo,
  setScheduledLecture,
  isLecturer,
  lectureTime,
}) => {
  const { userInfo } = useContext(LoginStateContext);
  const [cancleText, setCancleText] = useState("");
  const { setStateMessageUpdate } = useContext(UnreadMessageContext);
  const { handleUserInfo, handleSnackbarInfo } = useContext(
    LoginStateHandlerContext
  );

  const navigate = useNavigate();
  const buttonActive = checkButtonState(lectureTime.endTime);

  // 강사 프로필 섹션 눌렀을 때 프로필 상세보기 새 창으로 이동
  const handleOpenProfile = (uid) => {
    window.open(`board/profile/${uid}`);
  };

  // 수강생 신청 취소(강사 제외)
  const cancelClass = async () => {
    // 수강생의 경우 신청 취소
    cancleFixedLectureAPI(lectureInfo.id, userInfo.id, setScheduledLecture);
    handleClose();
    handleSnackbarInfo({
      state: true,
      message: "강의 신청이 정상적으로 취소되었습니다",
    });
  };

  // 강사 신청 취소
  const handleCancleLecture = () => {
    // 폐강 메시지 보내기
    // 취소사유 작성해야만 모달 닫기도록
    if (cancleText) {
      sendCnacleMessageAPI(
        lectureInfo.board.id,
        cancleText,
        "cancle",
        setStateMessageUpdate,
        lectureInfo.id,
        userInfo.id,
        setScheduledLecture,
        handleUserInfo
      );
      handleClose();
      handleClose();
    }
  };

  // 라이브 강의 입장
  const handleMoveToLecture = () => {
    navigate("/lecture", {
      state: {
        roomId: lectureInfo.board.id,
        lecturerId: instructorInfo.id,
        lecturerInfo: instructorInfo,
        time: {
          startTime: lectureTime.startTime,
          endTime: lectureTime.endTime,
        },
      },
    });
  };

  // 강의 시작 10분 전 ~ 강의 끝날때까지 Live 입장 버튼 활성화
  const handleActiveClassName = () => {
    if (
      Math.floor(
        (new Date(lectureTime.startTime) - new Date()) / (1000 * 60) <= 10 &&
          new Date() <= new Date(lectureTime.endTime)
      )
    ) {
      return "active";
    } else {
      return "inactive";
    }
  };

  return (
    <>
      <Modal open={open} onClose={handleClose}>
        <Box sx={style}>
          <Typography sx={{ mt: 1 }}>
            <b>{lectureInfo.board.title}</b>
          </Typography>
          <hr />
          <SContent>
            <SInfoItem>
              <SCustomFontAwesomeIcon icon={faPersonChalkboard} />
              <SSpan>
                <b>강사 정보</b>
              </SSpan>
            </SInfoItem>
            <Tooltip
              title={`${instructorInfo.name}님의 프로필 보러가기`}
              placement="bottom-end"
              componentsProps={{
                tooltip: {
                  sx: {
                    bgcolor: "orange",
                    fontWeight: "bold",
                    p: 1,
                  },
                },
              }}
            >
              <div
                className="instructor__section"
                onClick={(e) => handleOpenProfile(instructorInfo.id)}
              >
                <div>
                  <img
                    className="profile-img"
                    src={
                      instructorInfo.imgUrl
                        ? `${BASE_URL}/user${instructorInfo.imgUrl}`
                        : defaultProfile
                    }
                    alt=""
                  />
                </div>
                <div>
                  <div className="instructor-name">{instructorInfo.name}</div>
                  <div className="instructor-email">{instructorInfo.email}</div>
                </div>
              </div>
            </Tooltip>
          </SContent>
          <SInfoItem>
            <SCustomFontAwesomeIcon icon={faUsers} />
            <SSpan>
              <b>수강 인원</b> | {studentsInfo.length}명
            </SSpan>
          </SInfoItem>
          <SInfoItem>
            <SCustomFontAwesomeIcon icon={faClock} />
            <SSpan>
              <b>강의 시간</b> |{" "}
              {customLecTime(lectureTime.startTime, lectureTime.endTime)}
            </SSpan>
          </SInfoItem>
          <SInfoItem>
            <SCustomFontAwesomeIcon icon={faFileLines} />
            <SSpan>
              <b>강의 상세 내용</b>
            </SSpan>
          </SInfoItem>

          <SDetail>{lectureInfo.board.content}</SDetail>
          {buttonActive && (
            <SButtonBox>
              {isLecturer ? (
                <WarningModal
                  title="강의 취소 확인"
                  warningContent="강의를 취소하시면 -10점의 마일리지 패널티를 받게 됩니다."
                  content="강의 취소를 원하시면 취소 사유 기입 후, 확인을 눌러주세요."
                  handler={handleCancleLecture}
                  lectureCancel
                >
                  <textarea
                    style={{
                      resize: "none",
                      borderRadius: "8px",
                      fontFamily: "Pretendard-Regular",
                      fontSize: "1vw",
                      padding: "1vw",
                      width: "95%",
                    }}
                    value={cancleText}
                    onChange={(e) => setCancleText(e.target.value)}
                    rows="6"
                    placeholder="수강생들에게 공유되는 정보이므로 취소 사유를 반드시 입력해주세요!"
                  ></textarea>
                  <div
                    style={{
                      color: "blue",
                      fontSize: "0.8vw",
                      marginBottom: "1vw",
                    }}
                  >
                    {cancleText ? "" : "필수 입력사항입니다!"}
                  </div>
                </WarningModal>
              ) : (
                // 수강생에게 보여지는 취소 버튼
                <SButton onClick={(e) => cancelClass()}>신청취소</SButton>
              )}
              <SButton
                className={handleActiveClassName()}
                onClick={handleMoveToLecture}
              >
                Live 입장
              </SButton>
            </SButtonBox>
          )}
        </Box>
      </Modal>
    </>
  );
};

export default LectureFixedModal;
