import React, { useContext, useEffect, useState } from "react";
import { UnreadMessageContext } from "../../App";
import { getMessageListAPI } from "../../utils/api/messageAPI";
import MessageItem from "../MessageItem";
import Pagination from "../Pagination";
import { SSCard } from "../UnScheduleLecture/styles";

const Message = () => {
  const userId = localStorage.getItem("id");
  const { unreadMessageCnt } = useContext(UnreadMessageContext);
  const [messageData, setMessageData] = useState([]);
  // 메시지 읽음 or 삭제 여부 처리할 변수
  const [checkState, setCheckState] = useState(false);
  // ===============페이지네이션 변수===================
  const limit = 7;
  const [page, setPage] = useState(1); // 현재 페이지 번호
  const offset = (page - 1) * limit; // 첫 게시물의 위치
  // ======================================================

  // 처음 렌더링 될때 모든 메시지 불러옴
  useEffect(() => {
    getMessageListAPI(userId, setMessageData);
  }, []);

  useEffect(() => {
    // 메시지를 읽거나 지웠다면, 다시 모든 메시지 불러옴
    getMessageListAPI(userId, setMessageData);
  }, [checkState]);

  return (
    <>
      <SSCard>
        {messageData.length === 0 ? (
          <h3>📮 현재 메시지함이 비어있습니다</h3>
        ) : (
          <div>
            <h3>📮 아직 읽지 않은 메시지가 {unreadMessageCnt}통 있습니다</h3>
            {messageData.slice(offset, offset + limit).map((item, i) => {
              return (
                <div key={i} style={{ margin: "15px 0" }}>
                  <MessageItem
                    data={item}
                    checkState={checkState}
                    setCheckState={setCheckState}
                  />
                </div>
              );
            })}
          </div>
        )}
        {messageData.length ? (
          <div className="pagination__section">
            <Pagination
              total={messageData.length}
              limit={limit}
              page={page}
              setPage={setPage}
            />
          </div>
        ) : null}
      </SSCard>
    </>
  );
};

export default Message;
