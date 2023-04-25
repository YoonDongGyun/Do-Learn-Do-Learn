import { useState } from "react";
import LectureModal from "../LectureModal";
import { SImg, SUniBoard } from "./styles";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import * as F from "@fortawesome/free-solid-svg-icons";
import scrum from "../../assets/images/thumbnail/scrum.svg";
import cooking from "../../assets/images/thumbnail/cooking.svg";
import exercise from "../../assets/images/thumbnail/exercise.svg";
import drawing from "../../assets/images/thumbnail/drawing.svg";
import meeting from "../../assets/images/thumbnail/meeting.svg";
import conference from "../../assets/images/thumbnail/conference.svg";
import study from "../../assets/images/thumbnail/study.svg";
import teamwork from "../../assets/images/thumbnail/teamwork.svg";

// 개별 게시물 component
const UniBoard = ({ data }) => {
  // Modal 파트 ========================
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  // ===================================

  const createdTime = data.createdTime.substring(5, 10).replaceAll("-", ".");
  const deadline = data.deadline.substring(5, 10).replaceAll("-", ".");
  const startTime = data.startTime.substring(5, 13).replaceAll("-", ".");

  const thumbnails = [
    scrum,
    cooking,
    exercise,
    drawing,
    meeting,
    conference,
    study,
    teamwork,
  ];

  return (
    <SUniBoard>
      <div onClick={handleOpen}>
        <SImg src={thumbnails[data.tid]} />
        <h3>{data.title}</h3>
        <p>{data.summary}</p>
        <div style={{ textAlign: "left" }}>
          <p>
            <FontAwesomeIcon icon={F.faClock} />
            &nbsp;모집기간 | {createdTime} ~ {deadline}
          </p>
          <p>
            <FontAwesomeIcon icon={F.faCalendarDays} />
            &nbsp;강의시간 | {startTime}시
          </p>
          <p>
            <FontAwesomeIcon icon={F.faPersonChalkboard} />
            &nbsp;강사 신청현황 | {data.instructors}명
          </p>
          <p>
            <FontAwesomeIcon icon={F.faChalkboardUser} />
            &nbsp;수강생 신청현황 | {data.students} / {data.maxCnt}명
          </p>
        </div>
      </div>
      {open ? (
        <LectureModal
          data={data}
          open={open}
          setOpen={setOpen}
          handleClose={handleClose}
        />
      ) : null}
    </SUniBoard>
  );
};

export default UniBoard;
