import React from "react";
import WarningModal from "../WarningModal";

const LectureCancleModal = (handleClose) => {
  return (
    <WarningModal
      title="강의 취소 확인"
      warningContent="강의를 취소하면 점수 패널티를 받게 됩니다."
      content="강의 취소를 원하시면 확인을 눌러주세요."
      lectureCancel
    >
      <textarea
        style={{
          resize: "none",
          borderRadius: "8px",
          fontFamily: "Pretendard-Regular",
          fontSize: "calc(0.6vw + 5px)",
          padding: "calc(0.5vw + 2px)",
          width: "calc(1vw + 380px)",
        }}
        cols="52"
        rows="6"
      ></textarea>
    </WarningModal>
  );
};

export default LectureCancleModal;
