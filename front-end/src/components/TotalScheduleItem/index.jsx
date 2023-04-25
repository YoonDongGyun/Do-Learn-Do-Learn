import React, { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCalendarDays } from "@fortawesome/free-solid-svg-icons";
import { SContainer } from "./styles";
import LectureModal from "../LectureModal";

const TotalScheduleItem = (props) => {
  const year = props.item.startTime.slice(0, 4);
  const month = props.item.startTime.slice(5, 7);
  const day = props.item.startTime.slice(8, 10);
  const startTime = props.item.startTime.split(" ")[1].slice(0, 5);
  const endTime = props.item.endTime.split(" ")[1].slice(0, 5);

  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  return (
    <>
      <SContainer onClick={handleOpen}>
        <p>
          <FontAwesomeIcon
            className="totalSchedule__calendar"
            icon={faCalendarDays}
          />
          {`${year}. ${month}. ${day}.`} &nbsp; {`${startTime} ~ ${endTime}`}
        </p>
        <div>
          <p>
            {props.item.title.length > 15
              ? props.item.title.slice(0, 15) + "..."
              : props.item.title}
          </p>
        </div>
      </SContainer>
      {open && (
        <LectureModal
          data={props.item}
          open={open}
          setOpen={setOpen}
          handleClose={handleClose}
        />
      )}
    </>
  );
};

export default TotalScheduleItem;
