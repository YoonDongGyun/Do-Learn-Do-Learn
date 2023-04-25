import React, { useContext } from "react";
import { LoginStateHandlerContext, UnreadMessageContext } from "../../App";
import { deleteMessageAPI } from "../../utils/api/messageAPI";
import { SButtonContainer, SSpan, SScontent } from "./styles";
import { Box, Modal, Typography } from "@mui/material";

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

const MessageDeleteModal = ({
  messageId,
  open,
  handleClose,
  checkState,
  setCheckState,
}) => {
  const { setStateMessageUpdate } = useContext(UnreadMessageContext);
  const { handleSnackbarInfo } = useContext(LoginStateHandlerContext);

  const handleDeleteMessage = () => {
    deleteMessageAPI(
      messageId,
      setStateMessageUpdate,
      checkState,
      setCheckState
    );
    handleClose();
    handleSnackbarInfo({
      state: true,
      message: "메시지가 정상적으로 삭제되었습니다",
    });
  };

  return (
    <>
      <Modal open={open} onClose={handleClose} className="modal">
        <Box sx={style}>
          <Typography
            id="modal-modal-title"
            className="modal-title"
            variant="h6"
            component="h2"
          >
            <SSpan>메시지 삭제 확인</SSpan>
          </Typography>
          <Typography sx={{ mt: 2 }}>
            <SScontent>해당 메시지를 삭제하시겠습니까?</SScontent>
          </Typography>
          <SButtonContainer>
            <button className="cancel-button" onClick={handleClose}>
              취소
            </button>
            <button className="delete-button" onClick={handleDeleteMessage}>
              삭제
            </button>
          </SButtonContainer>
        </Box>
      </Modal>
    </>
  );
};

export default MessageDeleteModal;
