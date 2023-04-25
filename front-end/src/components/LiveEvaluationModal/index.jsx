import React, { useContext, useState } from "react";
import { Box, Modal, Typography } from "@mui/material";

import {
  SButtonContainer,
  SSpan,
  SScontent,
  SCustomFontAwesomeIcon,
  SIconContainer,
  SContentContainer,
} from "./styles";
import {
  faFaceFrown,
  faFaceMeh,
  faFaceSmile,
} from "@fortawesome/free-regular-svg-icons";
import { updateCheck } from "../../utils/api/lectureAPI";
import { LoginStateContext, LoginStateHandlerContext } from "../../App";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: "calc(1vw + 500px)",
  bgcolor: "background.paper",
  borderRadius: "8px",
  boxShadow: 24,
  outline: "none",
  padding: "20px 30px",
};

const LiveEvaluationModal = ({
  open,
  handleClose,
  exitRoom,
  roomId,
  lecturerId,
}) => {
  const { userInfo } = useContext(LoginStateContext);
  const { handleUserInfo } = useContext(LoginStateHandlerContext);
  const [gradeSelect, setGradeSelect] = useState(0);
  const toggleSelect = (e, grade) => {
    setGradeSelect(grade);
  };

  // 제출 버튼 눌렀을 때
  const handleSubmit = () => {
    updateCheck(
      roomId,
      userInfo.id,
      lecturerId,
      gradeSelect,
      exitRoom,
      handleUserInfo
    );
    console.log("제출 확인", gradeSelect, "포인트");
  };
  return (
    <div>
      <Modal open={open} onClose={handleClose} className="modal">
        <Box sx={style}>
          <Typography
            id="modal-modal-title"
            className="modal-title"
            variant="h6"
            component="h2"
          >
            <SSpan>강사 평가</SSpan>
          </Typography>
          <Typography sx={{ mt: 2 }}>
            <SScontent>강의는 어떠셨나요?</SScontent>
            <SScontent>더 나은 강의를 위해 평가해주세요😎</SScontent>
            <SContentContainer>
              <SIconContainer>
                <SCustomFontAwesomeIcon
                  className={gradeSelect === -10 ? "active" : ""}
                  onClick={(e) => toggleSelect(e, -10)}
                  icon={faFaceFrown}
                />
                <span className="evaluation_value">나쁨</span>
              </SIconContainer>
              <SIconContainer>
                <SCustomFontAwesomeIcon
                  className={gradeSelect === 0 ? "active" : ""}
                  onClick={(e) => toggleSelect(e, 0)}
                  icon={faFaceMeh}
                />
                <span className="evaluation_value">보통</span>
              </SIconContainer>
              <SIconContainer>
                <SCustomFontAwesomeIcon
                  className={gradeSelect === 10 ? "active" : ""}
                  onClick={(e) => toggleSelect(e, 10)}
                  icon={faFaceSmile}
                />
                <span className="evaluation_value">좋음</span>
              </SIconContainer>
            </SContentContainer>
          </Typography>
          <SButtonContainer>
            <button onClick={handleSubmit}>제출</button>
          </SButtonContainer>
        </Box>
      </Modal>
    </div>
  );
};

export default LiveEvaluationModal;
