import React, { useState } from "react";
import LectureChattingContainer from "../../components/LectureChattingContainer";
import LiveOptionContainer from "../../components/LiveOptionContainer";
import kurentoUtils from "kurento-utils";
import logoImg from "../../assets/images/logo.png";
import Grid from "@mui/material/Grid";
import {
  SMainContainer,
  SRightItemContainer,
  SContainer,
  SStudentsContainer,
  SLecturerCameraContainer,
  SLecturerCamera,
  SLeftItemContainer,
  SHeader,
} from "./styles";
import { useLocation } from "react-router";
import { useEffect } from "react";
import { useContext } from "react";
import { LoginStateContext } from "../../App";
import Timer from "../../components/Timer";
import { WEBRTC_URL } from "../../utils/api/URL";

/*
 * (C) Copyright 2014 Kurento (http://kurento.org/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

const Lecture = () => {
  const location = useLocation();
  const startTime = location.state.time.startTime;
  const endTime = location.state.time.endTime;

  const { userInfo } = useContext(LoginStateContext);

  const [username, setUsername] = useState(userInfo.name);
  const [roomId, setRoomId] = useState(location.state.roomId);
  const lecturerId = location.state.lecturerId;
  const lecturerInfo = location.state.lecturerInfo;

  // const ws = new WebSocket("wss://localhost:8443/groupcall");
  const ws = new WebSocket(`${WEBRTC_URL}`);

  var participants = {};
  var name = username;

  useEffect(() => {
    ws.onopen = () => {
      register(name, roomId);
    };
  }, []);

  const PARTICIPANT_MAIN_CLASS = "participant";
  const PARTICIPANT_CLASS = "participant";

  /**
   * Creates a video element for a new participant
   *
   * @param {String} name - the name of the new participant, to be used as tag
   *                        name of the video element.
   *                        The tag of the new element will be 'video<name>'
   * @return
   */

  var rtcPeer;

  function Participant(name) {
    this.name = name;
    // 카메라를 담을 컨테이너 div 생성
    let container = document.createElement("div");
    // 컨테이너 클래스 이름 부여 (메인이면 큰 화면, 메인이 아니면 작은 화면)
    container.className = isPresentMainParticipant()
      ? PARTICIPANT_CLASS
      : PARTICIPANT_MAIN_CLASS;
    container.id = name;
    let span = document.createElement("span");
    span.className = "username_span";
    let video = document.createElement("video");
    this.rtcPeer = rtcPeer;
    span.addEventListener("click", handleMainStage);

    container.appendChild(video);
    container.appendChild(span);
    if (name.includes("screen")) {
      span.appendChild(document.createTextNode(`${name.slice(6)} 님의 화면`));
    } else {
      span.appendChild(document.createTextNode(name));
    }

    if (name === lecturerInfo.name || name.includes("screen")) {
      document.getElementById("lectuerer").appendChild(container);

      video.id = "video-" + name;
      video.className = name.includes("screen") ? "mainScreen" : "main";
      // video.className = "main";
      video.autoplay = true;
      video.controls = false;
    } else {
      document.getElementById("participants").appendChild(container);

      video.id = "video-" + name;
      video.className = "sub";
      video.autoplay = true;
      video.controls = false;
    }

    const lecturerContainer = document.querySelector("#lectuerer");

    if (name === lecturerInfo.name) {
      if (lecturerContainer.childNodes.length === 2) {
        for (const item of lecturerContainer.childNodes) {
          if (item.id !== lecturerInfo.name) {
            item.firstChild.className = "sub";
            lecturerContainer.removeChild(item);
            document.getElementById("participants").appendChild(item);
          }
        }
      }
    }

    if (name.includes("screen")) {
      if (lecturerContainer.childNodes.length === 2) {
        for (const item of lecturerContainer.childNodes) {
          if (!item.id.includes("screen")) {
            item.firstChild.className = "sub";
            lecturerContainer.removeChild(item);
            document.getElementById("participants").appendChild(item);
          } else {
          }
        }
      }
    }

    const participantsContainer = document.getElementById("participants");
    for (const item of participantsContainer.childNodes) {
      if (item.id.includes("screen")) {
        item.firstChild.className = "subScreen";
      }
    }

    if (lecturerContainer.childNodes.length === 2) {
      for (const item of lecturerContainer?.childNodes) {
        if (item.firstChild.classList[0] === "main") {
        }
      }
    } else if (lecturerContainer.childNodes.length === 1) {
      lecturerContainer.childNodes[0].style.display = "";
    }

    this.getElement = function () {
      return container;
    };

    this.getVideoElement = function () {
      return video;
    };

    function switchContainerClass() {
      if (container.className === PARTICIPANT_CLASS) {
        var elements = Array.prototype.slice.call(
          document.getElementsByClassName(PARTICIPANT_MAIN_CLASS)
        );
        elements.forEach(function (item) {
          item.className = PARTICIPANT_CLASS;
        });

        container.className = PARTICIPANT_MAIN_CLASS;
      } else {
        container.className = PARTICIPANT_CLASS;
      }
    }

    function isPresentMainParticipant() {
      return (
        document.getElementsByClassName(PARTICIPANT_MAIN_CLASS).length !== 0
      );
    }

    this.offerToReceiveVideo = function (error, offerSdp, wp) {
      if (error) return console.error("sdp offer error");
      var msg = { id: "receiveVideoFrom", sender: name, sdpOffer: offerSdp };
      sendMessage(msg);
    };

    this.onIceCandidate = function (candidate, wp) {
      var message = {
        id: "onIceCandidate",
        candidate: candidate,
        name: name,
      };
      sendMessage(message);
    };

    Object.defineProperty(this, "rtcPeer", { writable: true });

    this.dispose = function () {
      this.rtcPeer.dispose();
      container.parentNode.removeChild(container);
    };
  }

  window.onbeforeunload = function () {
    ws.close();
  };

  ws.onmessage = function (message) {
    var parsedMessage = JSON.parse(message.data);

    switch (parsedMessage.id) {
      case "joinRoom":
        onJoinRoom(parsedMessage);
        break;
      case "leaveRoom":
        onLeaveRoom(parsedMessage);
        break;
      case "helpUser":
        handleHelpRequest(parsedMessage);
        break;
      case "existingParticipants":
        onExistingParticipants(parsedMessage);
        break;
      case "newParticipantArrived":
        onNewParticipant(parsedMessage);
        break;
      case "participantLeft":
        onParticipantLeft(parsedMessage);
        break;
      case "receiveVideoAnswer":
        receiveVideoResponse(parsedMessage);
        break;
      case "iceCandidate":
        participants[parsedMessage.name].rtcPeer.addIceCandidate(
          parsedMessage.candidate,
          function (error) {
            if (error) {
              console.error("Error adding candidate: " + error);
              return;
            }
          }
        );
        break;
      default:
        console.error("Unrecognized message", parsedMessage);
    }
  };

  function register(name, roomId) {
    document.getElementById("room-header").innerText = "ROOM " + roomId;
    document.getElementById("room").style.display = "block";

    var message = {
      id: "joinRoom",
      room: roomId,
      name: name,
    };
    sendMessage(message);

    return false;
  }

  function onNewParticipant(request) {
    receiveVideo(request.name);
  }

  function receiveVideoResponse(result) {
    participants[result.name].rtcPeer.processAnswer(
      result.sdpAnswer,
      function (error) {
        if (error) return console.error(error);
      }
    );
  }

  function onExistingParticipants(msg) {
    var constraints = {
      audio: true,
      video: {
        mandatory: {
          maxWidth: 320,
          maxFrameRate: 15,
          minFrameRate: 15,
        },
      },
    };
    if (name.includes("screen")) {
      var participant = new Participant(name);
      participants[name] = participant;
      var video = participant.getVideoElement();
      if (navigator.getDisplayMedia || navigator.mediaDevices.getDisplayMedia) {
        if (navigator.mediaDevices.getDisplayMedia) {
          navigator.mediaDevices
            .getDisplayMedia({ video: true, audio: true })
            .then((stream) => {
              video.srcObject = stream;
              options = {
                videoStream: stream,
                mediaConstraints: constraints,
                sendSource: "screen",
                onicecandidate: participant.onIceCandidate.bind(participant),
              };
              participant.rtcPeer =
                new kurentoUtils.WebRtcPeer.WebRtcPeerSendrecv(
                  options,
                  function (error) {
                    if (error) {
                      return console.error(error);
                    }
                    this.generateOffer(
                      participant.offerToReceiveVideo.bind(participant)
                    );
                  }
                );
              msg.data.forEach(receiveVideo);
            });
        }
      }
    } else {
      console.log(name + " registered in room " + msg.room);
      var participant = new Participant(name);
      participants[name] = participant;
      var video = participant.getVideoElement();

      var options = {
        localVideo: video,
        mediaConstraints: constraints,
        onicecandidate: participant.onIceCandidate.bind(participant),
      };
      participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(
        options,
        function (error) {
          if (error) {
            return console.error(error);
          }
          this.generateOffer(participant.offerToReceiveVideo.bind(participant));
        }
      );

      msg.data.forEach(receiveVideo);
    }
  }

  function leaveRoom() {
    var message = {
      id: "leaveRoom",
      name: name,
    };

    sendMessage(message);

    for (var key in participants) {
      participants[key].dispose();
    }

    document.getElementById("room").style.display = "none";

    ws.close();
  }

  function receiveVideo(sender) {
    var participant = new Participant(sender);
    participants[sender] = participant;
    var video = participant.getVideoElement();

    var options = {
      remoteVideo: video,
      onicecandidate: participant.onIceCandidate.bind(participant),
    };

    participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(
      options,
      function (error) {
        if (error) {
          return console.error(error);
        }
        this.generateOffer(participant.offerToReceiveVideo.bind(participant));
      }
    );
  }

  function onParticipantLeft(request) {
    var participant = participants[request.name];
    participant.dispose();
    delete participants[request.name];
  }

  function sendMessage(message) {
    var jsonMessage = JSON.stringify(message);
    ws.send(jsonMessage);
  }

  const vidOnOff = () => {
    if (participants[username].rtcPeer.videoEnabled) {
      participants[username].rtcPeer.videoEnabled = false;
      document.getElementById("vidOn").style.display = "none";
      document.getElementById("vidOff").style.display = "";
    } else {
      participants[username].rtcPeer.videoEnabled = true;
      document.getElementById("vidOn").style.display = "";
      document.getElementById("vidOff").style.display = "none";
    }
  };

  const audOnOff = () => {
    if (participants[username].rtcPeer.audioEnabled) {
      participants[username].rtcPeer.audioEnabled = false;
      document.getElementById("audOn").style.display = "none";
      document.getElementById("audOff").style.display = "";
    } else {
      participants[username].rtcPeer.audioEnabled = true;
      document.getElementById("audOn").style.display = "";
      document.getElementById("audOff").style.display = "none";
    }
  };

  const handleOnClickHelpRequest = () => {
    let message = {
      id: "helpUser",
      name: name,
    };
    sendMessage(message);
  };

  const handleHelpRequest = (request) => {
    const targetUser = request.name;

    const userVideo = document.getElementById("video-" + targetUser);

    if (!userVideo.classList.contains("help")) {
      userVideo.classList.add("help");
      document.getElementById("helpOn").style.display = "none";
      document.getElementById("helpOff").style.display = "";
    } else {
      userVideo.classList.remove("help");
      document.getElementById("helpOn").style.display = "";
      document.getElementById("helpOff").style.display = "none";
    }
  };

  const onJoinRoom = (request) => {
    let entranceFlag = false;
    const entranceNodes = document.querySelectorAll(".entrance");
    for (const node of entranceNodes) {
      if (node.innerText === `${request.name} 님이 입장했습니다.`) {
        entranceFlag = true;
        break;
      } else if (node.innerText === `${request.name} 님이 종료했습니다.`) {
        entranceFlag = false;
        break;
      }
    }

    // 이미 입장 메세지가 있다면 (화면 공유 해제를 통한 재입장시 메세지를 띄울 필요 없음)
    if (!entranceFlag) {
      const chattingContentContainer =
        document.getElementById("chatting-Content");
      const entranceContent = document.createElement("p");
      entranceContent.className = "entrance";
      entranceContent.innerText = `${request.name} 님이 입장했습니다.`;
      chattingContentContainer.appendChild(entranceContent);
    }
  };

  const onLeaveRoom = (request) => {
    const chattingContentContainer =
      document.getElementById("chatting-Content");
    const entranceContent = document.createElement("p");
    entranceContent.className = "entrance";
    entranceContent.innerText = `${request.name} 님이 종료했습니다.`;
    chattingContentContainer.appendChild(entranceContent);
  };

  const handleMainStage = (e) => {
    if (e.target.previousSibling.className.includes("subScreen")) {
      // 비디오 클릭시 참가자들에서 해당 타겟 제거
      const participants = document.querySelector("#participants");
      const userVideoContainer = e.target.parentNode;
      const userVideo = userVideoContainer.firstChild;
      // 비디오 화면 크기 조정
      userVideo.classList.remove("subScreen");
      userVideo.classList.add("mainScreen");
      participants.removeChild(userVideoContainer);

      // 강사 화면을 타겟 화면으로 대체
      const mainStage = document.querySelector("#lectuerer");
      // mainStage에 비디오가 있으면 대체
      // 없으면 target화면만 이동
      if (mainStage.firstChild) {
        const mainVideoContainer = mainStage.firstChild;
        const mainVideo = mainVideoContainer.firstChild;
        if (mainVideo.className.includes("mainScreen")) {
          // 비디오 화면 크기 조정
          mainVideo.classList.remove("mainScreen");
          mainVideo.classList.add("subScreen");

          // mainStage에서 화면 제거
          mainStage.removeChild(mainVideoContainer);
          // subStage에 mainStage에 있던 화면 추가
          participants.appendChild(mainVideoContainer);
        } else if (mainVideo.className.includes("main")) {
          // 비디오 화면 크기 조정
          mainVideo.classList.remove("main");
          mainVideo.classList.add("sub");

          // mainStage에서 화면 제거
          mainStage.removeChild(mainVideoContainer);
          // subStage에 mainStage에 있던 화면 추가
          participants.appendChild(mainVideoContainer);
        }
      }
      // mainStage에 tatget 화면 추가
      mainStage.appendChild(userVideoContainer);
    } else if (e.target.previousSibling.className.includes("sub")) {
      // 비디오 클릭시 참가자들에서 해당 타겟 제거
      const participants = document.querySelector("#participants");
      const userVideoContainer = e.target.parentNode;
      const userVideo = userVideoContainer.firstChild;
      // 비디오 화면 크기 조정
      userVideo.classList.remove("sub");
      userVideo.classList.add("main");
      participants.removeChild(userVideoContainer);

      // 강사 화면을 타겟 화면으로 대체
      const mainStage = document.querySelector("#lectuerer");
      // mainStage에 비디오가 있으면 대체
      // 없으면 target화면만 이동
      if (mainStage.firstChild) {
        const mainVideoContainer = mainStage.firstChild;
        const mainVideo = mainVideoContainer.firstChild;
        if (mainVideo.className.includes("mainScreen")) {
          // 비디오 화면 크기 조정
          mainVideo.classList.remove("mainScreen");
          mainVideo.classList.add("subScreen");

          // mainStage에서 화면 제거
          mainStage.removeChild(mainVideoContainer);
          // subStage에 mainStage에 있던 화면 추가
          participants.appendChild(mainVideoContainer);
        } else if (mainVideo.className.includes("main")) {
          // 비디오 화면 크기 조정
          mainVideo.classList.remove("main");
          mainVideo.classList.add("sub");

          // mainStage에서 화면 제거
          mainStage.removeChild(mainVideoContainer);
          // subStage에 mainStage에 있던 화면 추가
          participants.appendChild(mainVideoContainer);
        }
      }
      // mainStage에 tatget 화면 추가
      mainStage.appendChild(userVideoContainer);
    } else {
      if (Object.keys(participants).length === 1) {
        const mainStage = document.querySelector("#lectuerer");

        const mainVideoContainer = mainStage.firstChild;
        const mainVideo = mainVideoContainer.firstChild;
        if (mainVideo.className.includes("mainScreen")) {
          mainVideo.classList.remove("mainScreen");
          mainVideo.classList.add("subScreen");

          const participants = document.querySelector("#participants");

          // mainStage에서 화면 제거
          mainStage.removeChild(mainVideoContainer);
          // subStage에 mainStage에 있던 화면 추가
          participants.appendChild(mainVideoContainer);
        } else if (mainVideo.className.includes("main")) {
          // 비디오 화면 크기 조정
          mainVideo.classList.remove("main");
          mainVideo.classList.add("sub");

          const participants = document.querySelector("#participants");

          // mainStage에서 화면 제거
          mainStage.removeChild(mainVideoContainer);
          // subStage에 mainStage에 있던 화면 추가
          participants.appendChild(mainVideoContainer);
        }
      }
    }
  };

  // 화면 공유
  const shareScreen = (e) => {
    if (name === username) {
      if (!document.querySelector(".screen")) {
        sendMessage({
          id: "leaveRoomToShareScreen",
        });
        for (let key in participants) {
          if (participants[key].name !== name) {
            let partVideoContainer = document.getElementById(
              participants[key].name
            );

            document
              .getElementById(participants[key].name)
              .parentNode.removeChild(partVideoContainer);
          } else if (participants[key].name === name) {
            let partVideoContainer = document.getElementById(
              participants[key].name
            );

            document
              .getElementById(participants[key].name)
              .parentNode.removeChild(partVideoContainer);
          }
        }
        delete participants[name];

        const message = {
          id: "shareScreen",
          name: username,
          room: roomId,
        };
        ws.send(JSON.stringify(message));
        name = "screen" + name;
        document.getElementById("shareScreenOn").style.display = "none";
        document.getElementById("shareScreenOff").style.display = "";
      }
    } else {
      sendMessage({
        id: "leaveRoomToShareScreen",
      });
      for (var key in participants) {
        participants[key].dispose();
        if (participants[key].name !== name) {
          var partVideoContainer = document.getElementById(
            participants[key].name
          );
        }
      }
      delete participants[name];
      const message = {
        id: "joinRoom",
        name: username,
        room: roomId,
      };
      ws.send(JSON.stringify(message));
      name = username;
      document.getElementById("shareScreenOn").style.display = "";
      document.getElementById("shareScreenOff").style.display = "none";
    }
  };

  return (
    <>
      <Grid container>
        <Grid item xs={0} md={0.5} />
        <Grid item xs={12} md={11}>
          <SHeader>
            <img className="logo" src={logoImg} alt="" />
            <Timer
              lecturerId={lecturerId}
              startTime={startTime}
              endTime={endTime}
            />
          </SHeader>
        </Grid>
        <Grid item xs={0} md={0.5} />
      </Grid>
      <SMainContainer>
        <SLeftItemContainer>
          <SContainer>
            <SLecturerCameraContainer>
              <SLecturerCamera>
                <div id="lectuerer"></div>
              </SLecturerCamera>
            </SLecturerCameraContainer>
            <SStudentsContainer>
              <div id="container">
                <div id="wrapper">
                  <div id="room" style={{ display: "none" }}>
                    <h2 id="room-header" style={{ display: "none" }}></h2>
                    <div id="participants"></div>
                  </div>
                </div>
              </div>
            </SStudentsContainer>
          </SContainer>
        </SLeftItemContainer>
        <SRightItemContainer>
          <LectureChattingContainer
            roomId={roomId}
            username={username}
            lecturerInfo={lecturerInfo}
          ></LectureChattingContainer>
        </SRightItemContainer>
        <LiveOptionContainer
          leaveRoom={leaveRoom}
          vidOnOff={vidOnOff}
          audOnOff={audOnOff}
          handleOnClickHelpRequest={handleOnClickHelpRequest}
          shareScreen={shareScreen}
          roomId={roomId}
          lecturerId={lecturerId}
          userId={userInfo.id}
        />
      </SMainContainer>
    </>
  );
};

export default Lecture;
