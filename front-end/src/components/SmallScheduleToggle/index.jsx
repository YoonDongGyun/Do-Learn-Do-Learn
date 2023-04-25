import React, { useState } from "react";
import SmallSchedule from "../SmallSchedule";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCalendarDays } from "@fortawesome/free-regular-svg-icons";
import { faAnglesRight } from "@fortawesome/free-solid-svg-icons";
import { SSmallScheduleMain } from "./styles";

const SmallScheduleToggle = () => {
  const [mount, setMount] = useState(true);
  const [effect, setEffect] = useState("mount");

  const handleSmallScheduleToggle = () => {
    if (mount) {
      setEffect("unmount");
      setTimeout(() => {
        setMount(!mount);
      }, 1200);
    } else {
      setEffect("mount");
      setMount(!mount);
    }
  };

  return (
    <SSmallScheduleMain>
      <div className="smallSchedule-container">
        <div className={`${effect}`}>{mount && <SmallSchedule />}</div>
        <button
          className="smallSchedule__toggle-button"
          onClick={handleSmallScheduleToggle}
        >
          {mount ? (
            <FontAwesomeIcon icon={faAnglesRight} />
          ) : (
            <FontAwesomeIcon icon={faCalendarDays} />
          )}
        </button>
      </div>
    </SSmallScheduleMain>
  );
};

export default SmallScheduleToggle;
