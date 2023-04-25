import React, { useState } from "react";
import { SOptionContainer } from "./styles";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faHand,
  faVideo,
  faVideoSlash,
  faMicrophone,
  faMicrophoneSlash,
} from "@fortawesome/free-solid-svg-icons";
import { faHand as faRegularHand } from "@fortawesome/free-regular-svg-icons";
import CastIcon from "@mui/icons-material/Cast";
import CastConnectedIcon from "@mui/icons-material/CastConnected";
import ExitToAppIcon from "@mui/icons-material/ExitToApp";
import { useNavigate } from "react-router";
import LiveEvaluationModal from "../LiveEvaluationModal";
import { getLecturePacitipants } from "../../utils/api/lectureAPI";

const LiveOptionContainer = (props) => {
  const navigate = useNavigate();
  const [checkEvalState, setCheckEvalState] = useState(false); // 평가했는지 판단 (useContext써야할듯?!)
  const [open, setOpen] = useState(false); // 평가 모달 열기
  const handleClose = () => setOpen(false); // 평가 모달 닫기

  const exitRoom = () => {
    props.leaveRoom();
    navigate("/");
  };

  const handleMoveToHome = () => {
    console.log(props.roomId);
    getLecturePacitipants(
      props.userId,
      props.roomId,
      props.lecturerId,
      setOpen,
      exitRoom
    );
  };

  return (
    <SOptionContainer>
      <button
        id="helpOff"
        style={{ display: "none" }}
        onClick={(e) => props.handleOnClickHelpRequest(e)}
      >
        <FontAwesomeIcon className="icon" icon={faHand} />
      </button>
      <button id="helpOn" onClick={(e) => props.handleOnClickHelpRequest(e)}>
        <FontAwesomeIcon className="icon" icon={faRegularHand} />
      </button>
      <button
        id="vidOff"
        onClick={(e) => props.vidOnOff(e)}
        style={{ display: "none" }}
      >
        <FontAwesomeIcon className="icon camera-icon" icon={faVideoSlash} />
      </button>
      <button id="vidOn" onClick={(e) => props.vidOnOff(e)}>
        <FontAwesomeIcon className="icon camera-icon" icon={faVideo} />
      </button>
      <button
        id="audOff"
        onClick={(e) => props.audOnOff(e)}
        style={{ display: "none" }}
      >
        <FontAwesomeIcon className="icon mike-icon" icon={faMicrophoneSlash} />
      </button>
      <button id="audOn" onClick={(e) => props.audOnOff(e)}>
        <FontAwesomeIcon className="icon mike-icon" icon={faMicrophone} />
      </button>
      <button
        id="shareScreenOff"
        onClick={(e) => props.shareScreen(e)}
        style={{ display: "none" }}
      >
        <CastIcon className="icon big-icon" />
      </button>
      <button id="shareScreenOn" onClick={(e) => props.shareScreen(e)}>
        <CastConnectedIcon className="icon big-icon" />
      </button>
      <button className="exit-button" onClick={handleMoveToHome}>
        <ExitToAppIcon className="icon big-icon exit-icon" />
      </button>
      {open ? (
        <LiveEvaluationModal
          open={open}
          handleClose={handleClose}
          setCheckEvalState={setCheckEvalState}
          exitRoom={exitRoom}
          roomId={props.roomId}
          lecturerId={props.lecturerId}
        />
      ) : null}
    </SOptionContainer>
  );
};

export default LiveOptionContainer;
