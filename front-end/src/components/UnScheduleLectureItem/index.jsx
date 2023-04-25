import React from "react";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCalendarDays, faClock } from "@fortawesome/free-solid-svg-icons";
import { SMain } from "./styles";
import LectureModal from "../LectureModal";
import scrum from "../../assets/images/thumbnail/scrum.svg";
import cooking from "../../assets/images/thumbnail/cooking.svg";
import exercise from "../../assets/images/thumbnail/exercise.svg";
import drawing from "../../assets/images/thumbnail/drawing.svg";
import meeting from "../../assets/images/thumbnail/meeting.svg";
import conference from "../../assets/images/thumbnail/conference.svg";
import study from "../../assets/images/thumbnail/study.svg";
import teamwork from "../../assets/images/thumbnail/teamwork.svg";

const customPostingTime = (start, end) => {
  const startDate = new Date(start);
  const endDate = new Date(end);
  const startYear = startDate.getFullYear().toString().slice(-2);
  const startMonth = (startDate.getMonth() + 1).toString().padStart(2, "0");
  const startDay = startDate.getDate().toString().padStart(2, "0");
  const endYear = endDate.getFullYear().toString().slice(-2);
  const endMonth = (endDate.getMonth() + 1).toString().padStart(2, "0");
  const endDay = endDate.getDate().toString().padStart(2, "0");

  const custom = `${startYear}.${startMonth}.${startDay} ~ ${endYear}.${endMonth}.${endDay}`;
  return custom;
};

const customLecTime = (start, end) => {
  const startDate = new Date(start);
  const endDate = new Date(end);
  const year = startDate.getFullYear().toString().slice(-2);
  const month = (startDate.getMonth() + 1).toString().padStart(2, "0");
  const day = startDate.getDate().toString().padStart(2, "0");
  const time = startDate.toLocaleTimeString("ko-KR", {
    hour: "numeric",
    minute: "numeric",
    hour12: false,
  });
  let remain = endDate.getHours() - startDate.getHours();
  if (remain < 0) remain += 24;
  const custom = `${year}.${month}.${day} ${time} (${remain}시간)`;
  return custom;
};

const UnScheduleLectureItem = ({ data }) => {
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const postingTime = customPostingTime(data.createdTime, data.deadline);
  const lecTime = customLecTime(data.startTime, data.endTime);
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
    <>
      <SMain onClick={handleOpen}>
        <div className="main__container">
          <img className="thumbnail-img" src={thumbnails[data.tid]} />
          <div className="lecture-info__section">
            <span>{data.title}</span>
            {/* <span className="content">{data.content}</span> */}
            <p>
              <FontAwesomeIcon icon={faClock} />
              &nbsp;{postingTime}
            </p>
            <p>
              <FontAwesomeIcon icon={faCalendarDays} />
              &nbsp;{lecTime}
            </p>
          </div>
        </div>
      </SMain>
      {open ? (
        <LectureModal
          data={data}
          open={open}
          setOpen={setOpen}
          handleClose={handleClose}
        />
      ) : null}
    </>
  );
};

export default UnScheduleLectureItem;
