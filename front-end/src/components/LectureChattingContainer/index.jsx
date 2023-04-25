import React from "react";
import { SContainer, SChattingContainer, SChattingContent } from "./styles";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { useEffect } from "react";
import { useRef } from "react";
import { SOCKET_URL } from "../../utils/api/URL";
import MessageContainer from "../MessageContainer";

const LectureChattingContainer = (props) => {
  const messageBoxRef = useRef();

  let sockJS = new SockJS(`${SOCKET_URL}`);
  let client = Stomp.over(sockJS);
  const accessToken = localStorage.getItem("accessToken");

  const scrollToBottom = () => {
    if (messageBoxRef.current) {
      messageBoxRef.current.scrollTop = messageBoxRef.current.scrollHeight + 65;
    }
  };

  const handleMessageSend = (e, inputData, setInputData) => {
    e.preventDefault();
    if (inputData) {
      client.send(
        `/pub/normal/${props.roomId}`,
        {
          Authentication: accessToken,
        },
        JSON.stringify({
          roomId: props.roomId,
          sender: props.username,
          content: inputData,
        })
      );
      setInputData("");
    }
  };

  const handleMeesageSendKeyEvent = async (e, inputData, setInputdata) => {
    if (e.key === "Enter") {
      if (!e.shiftKey) {
        await client.send(
          `/pub/normal/${props.roomId}`,
          {
            Authentication: accessToken,
          },
          JSON.stringify({
            roomId: props.roomId,
            sender: props.username,
            content: inputData,
          })
        );
        setInputdata("");
        await e.preventDefault();
      }
    }
  };

  const handleInnerMessage = (meesage) => {
    const chattingContentContainer =
      document.getElementById("chatting-Content");

    const contentContainer = document.createElement("div");
    contentContainer.className = "content-container";

    const userNameTag = document.createElement("p");
    userNameTag.innerText = `${meesage.sender} ${
      props.lecturerInfo.name === meesage.sender ? "(강사)" : "(수강생)"
    }`;

    const contentTag = document.createElement("div");
    contentTag.innerText = meesage.content;
    contentTag.className = "content";
    if (meesage.sender === props.username) {
      contentContainer.style.textAlign = "right";
      contentTag.style.backgroundColor = "rgb(255 235 168)";
    }

    if (meesage.sender === props.username) {
      contentContainer.style.textAlign = "right";
      contentTag.style.backgroundColor = "rgb(255 235 168)";
    }

    contentContainer.appendChild(userNameTag);
    contentContainer.appendChild(contentTag);
    chattingContentContainer.appendChild(contentContainer);
  };

  useEffect(() => {
    client.connect(
      {
        Authentication: accessToken,
      },
      (frame) => {
        client.subscribe(`/sub/${props.roomId}`, (response) => {
          console.log("STOMP Connection");
          const receivedMessage = JSON.parse(response.body);
          handleInnerMessage(receivedMessage);
          scrollToBottom();
        });
      }
    );
  }, []);

  return (
    <SContainer>
      <SChattingContainer ref={messageBoxRef}>
        <SChattingContent id="chatting-Content"></SChattingContent>
      </SChattingContainer>
      <MessageContainer
        handleMeesageSendKeyEvent={handleMeesageSendKeyEvent}
        handleMessageSend={handleMessageSend}
      />
    </SContainer>
  );
};

export default LectureChattingContainer;
