import React, { useState } from "react";
import { SMessageContainer } from "./styles";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPaperPlane } from "@fortawesome/free-solid-svg-icons";

const MessageContainer = (props) => {
  const [inputData, setInputData] = useState("");

  return (
    <>
      <SMessageContainer>
        <textarea
          cols="38"
          rows="1"
          value={inputData}
          onChange={(e) => setInputData(e.target.value)}
          onKeyDown={(e) =>
            props.handleMeesageSendKeyEvent(e, inputData, setInputData)
          }
        />
        {/* 메시지 전송 버튼 */}
        <button
          onClick={(e) => props.handleMessageSend(e, inputData, setInputData)}
        >
          <FontAwesomeIcon className="send-icon" icon={faPaperPlane} />
        </button>
      </SMessageContainer>
    </>
  );
};

export default MessageContainer;
