import React from "react";
import { useState } from "react";
import MessageDeleteModal from "../MessageDeleteModal";
import MessageDetailModal from "../MessageDetailModal";
import { SMessageIcon, SMain, STrashIcon } from "./styles";
import {
  faEnvelope,
  faEnvelopeOpen,
  faTrashCan,
} from "@fortawesome/free-regular-svg-icons";

const checkType = (target) => {
  if (target === "confirm" || target === "complete") {
    return "확정";
  } else if (target === "cancle") {
    return "취소";
  } else if (target === "close") {
    return "폐강";
  }
};

const customTime = (target) => {
  const today = new Date();
  const date = new Date(target);
  const year = date.getFullYear().toString().slice(-2);
  const month = (date.getMonth() + 1).toString().padStart(2, "0");
  const day = date.getDate().toString().padStart(2, "0");
  const time = date.toLocaleTimeString("ko-KR", {
    hour: "numeric",
    minute: "numeric",
    hour12: false,
  });
  let custom;
  // 오늘 날짜에 수신한 메시지는 시간만 표시
  if (
    today.getFullYear() === date.getFullYear() &&
    today.getMonth() + 1 === date.getMonth() + 1 &&
    today.getDate() === date.getDate()
  ) {
    custom = time;
    // 그 외는 날짜 + 시간 표시
  } else {
    custom = `${year}.${month}.${day}\n${time}`;
  }
  return custom;
};

const MessageItem = ({ data, checkState, setCheckState }) => {
  const [deleteModalOpen, setDeleteModalOpen] = useState(false);
  const [detailModalOpen, setDetailModalOpen] = useState(false);

  // 메시지 읽음 여부 상태 변수
  const checkMessage = data.isChecked ? true : false;
  const type = checkType(data.type);
  const time = customTime(data.createdTime);

  const handleModalOpen = (target) => {
    if (target === "delete") {
      setDeleteModalOpen(true);
    } else if (target === "detail") {
      setDetailModalOpen(true);
    }
  };
  const handleDeleteModalClose = () => {
    setDeleteModalOpen(false);
  };
  const handleDetailModalClose = () => {
    setDetailModalOpen(false);
  };

  return (
    <>
      <SMain>
        <div
          className={
            checkMessage
              ? "main__container message__read"
              : "main__container message__unread"
          }
          onClick={() => handleModalOpen("detail")}
        >
          <div className="message-icon">
            {checkMessage ? (
              <div className="message-icon__read">
                <SMessageIcon icon={faEnvelopeOpen} />
              </div>
            ) : (
              <div className="message-icon__unread">
                <SMessageIcon icon={faEnvelope} />
              </div>
            )}
          </div>
          <div className="message-sender">
            <p>관리자</p>
          </div>
          <div className="message-content">
            <b>[{type}]</b> {data.title}
          </div>
          <div className="message-time">
            <p>{time}</p>
          </div>
          <div className="trash-icon">
            <div
              className={
                checkMessage ? "message-icon__read" : "message-icon__unread"
              }
            >
              <STrashIcon
                icon={faTrashCan}
                onClick={(e) => {
                  // 이벤트 버블링 방지
                  e.stopPropagation();
                  handleModalOpen("delete");
                }}
              />
            </div>
          </div>
        </div>
        {deleteModalOpen && (
          <MessageDeleteModal
            messageId={data.id}
            open={deleteModalOpen}
            handleClose={handleDeleteModalClose}
            checkState={checkState}
            setCheckState={setCheckState}
          />
        )}
        {detailModalOpen && (
          <MessageDetailModal
            data={data}
            open={detailModalOpen}
            handleClose={handleDetailModalClose}
            checkMessage={checkMessage}
            checkState={checkState}
            setCheckState={setCheckState}
          />
        )}
      </SMain>
    </>
  );
};

export default MessageItem;
