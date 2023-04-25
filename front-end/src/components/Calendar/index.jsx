import React, { useContext, useEffect, useState } from "react";
import FullCalendar from "@fullcalendar/react"; // must go before plugins
import dayGridPlugin from "@fullcalendar/daygrid"; // a plugin!
import timeGridPlugin from "@fullcalendar/timegrid";
import { SCalendar } from "./styles";
import { getScheduledLectureAPI } from "../../utils/api/userAPI";
import CardBox from "../CardBox";
import LectureFixedModal from "../LectureFixedModal";
import { getFixedLectureInfo } from "../../utils/api/boardAPI";
import { LoginStateContext } from "../../App";

const Calendar = () => {
  // Modal 파트 ========================
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const [checkModalState, setCheckModalState] = useState(false); // 모달에 전달할 데이터 상태 체크 변수
  // ===================================
  const [scheduledLecture, setScheduledLecture] = useState({});

  // 일정 상세 api 통해 받아올 변수
  const { userInfo } = useContext(LoginStateContext);
  const [lectureInfo, setLectureInfo] = useState([]);
  const [lectureTime, setLectureTime] = useState([]);
  const [instructorInfo, setInstructorInfo] = useState([]);
  const [studentsInfo, setStudentsInfo] = useState([]);
  // 렌더링 됐을 때, 일정 정보 불러오기
  useEffect(() => {
    getScheduledLectureAPI(localStorage.getItem("id"), setScheduledLecture);
  }, []);

  // 달력에 일정 클릭했을 때 LectureModal띄울 수 있도록 데이터 정제하기
  const handleEventClick = (arg) => {
    setLectureTime({
      startTime: arg.event.startStr,
      endTime: arg.event.endStr,
    });
    getFixedLectureInfo(
      arg.event.id,
      setLectureInfo,
      setInstructorInfo,
      setStudentsInfo,
      setCheckModalState
    );
  };

  // 모달에 보낼 데이터 상태 바뀌면 LectureModal 띄움
  useEffect(() => {
    if (checkModalState) {
      handleOpen();
      setCheckModalState(false);
    }
  }, [checkModalState]);

  const eventContent = (eventInfo) => {
    return (
      <div
        style={{
          margin: 0,
        }}
      >
        <b>
          <p style={{ margin: 0, color: "orange", fontSize: "0.8vw" }}>
            {eventInfo.timeText}
          </p>
        </b>
        <p
          style={{
            margin: 0,
            whiteSpace: "normal",
            color: "white",
            fontSize: "0.9vw",
          }}
        >
          {eventInfo.event.title}
        </p>
      </div>
    );
  };

  return (
    <CardBox>
      <SCalendar>
        <FullCalendar
          plugins={[dayGridPlugin, timeGridPlugin]}
          initialView="dayGridMonth"
          events={scheduledLecture}
          eventClick={handleEventClick}
          headerToolbar={{
            left: "title",
            right: "prev,next today",
            center: "dayGridMonth,timeGridWeek,timeGridDay",
          }}
          contentHeight="80vh"
          eventTimeFormat={{
            hour: "2-digit",
            minute: "2-digit",
            meridiem: false,
            hour12: false,
          }}
          nowIndicator="true"
          handleWindowResize="true"
          displayEventEnd="true"
          eventContent={eventContent}
        />
        {open ? (
          <LectureFixedModal
            open={open}
            handleClose={handleClose}
            lectureInfo={lectureInfo}
            instructorInfo={instructorInfo}
            studentsInfo={studentsInfo}
            setCheckModalState={setCheckModalState}
            setScheduledLecture={setScheduledLecture}
            isLecturer={userInfo.id === instructorInfo.id ? true : false}
            lectureTime={lectureTime}
          />
        ) : null}
      </SCalendar>
    </CardBox>
  );
};

export default Calendar;
