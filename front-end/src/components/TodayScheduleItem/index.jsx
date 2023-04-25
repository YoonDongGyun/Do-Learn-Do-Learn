import React, { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faClock } from "@fortawesome/free-solid-svg-icons";
import { SContainer } from "./styles";
import { useNavigate } from "react-router";
import { getLecturerId } from "../../utils/api/lectureAPI";
import { getUserInfo } from "../../utils/api/userAPI";

const TodayScheduleItem = (props) => {
  const [lecturerId, setLecturerId] = useState(null);
  const [lecturerInfo, setLecturerInfo] = useState(null);

  const startTime = props.item.startTime.split(" ")[1].slice(0, 5);
  const endTime = props.item.endTime.split(" ")[1].slice(0, 5);
  const navigate = useNavigate();

  const handleMoveToLecture = () => {
    navigate("/lecture", {
      state: {
        roomId: props.item.id,
        lecturerId: lecturerId,
        lecturerInfo: lecturerInfo,
        time: {
          startTime: props.item.startTime,
          endTime: props.item.endTime,
        },
      },
    });
  };

  // 강의 시작 10분 전 ~ 강의 끝날때까지 Live 입장 버튼 활성화
  const handleActiveClassName = () => {
    if (
      Math.floor(
        (new Date(props.item.startTime) - new Date()) / (1000 * 60) <= 10 &&
          // new Date() - new Date(props.item.endTime) <= 0
          new Date() <= new Date(props.item.endTime)
      )
    ) {
      return "active";
    } else {
      return "inactive";
    }
  };

  useEffect(() => {
    getLecturerId(props.item.id, setLecturerId);
  }, []);

  useEffect(() => {
    if (lecturerId) {
      getUserInfo(lecturerId, setLecturerInfo);
    }
  }, [lecturerId]);

  return (
    <SContainer>
      <p>
        <FontAwesomeIcon className="todaySchedule__clock" icon={faClock} />
        {startTime} ~ {endTime}
      </p>
      <div>
        <p>
          {props.item.title.length > 15
            ? props.item.title.slice(0, 15) + "..."
            : props.item.title}
        </p>
        <button
          className={handleActiveClassName()}
          onClick={handleMoveToLecture}
        >
          Live 입장
        </button>
      </div>
    </SContainer>
  );
};

export default TodayScheduleItem;
