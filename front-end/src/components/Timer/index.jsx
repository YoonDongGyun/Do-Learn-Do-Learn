import React, { useEffect, useState } from "react";
import { SAlert, STimer } from "./styles";
import useInterval from "../../hooks/useInterval";
import { updatePoint2 } from "../../utils/api/lectureAPI";
import { useNavigate } from "react-router";

const useResultOfIntervalCalculator = (calculator, delay) => {
  const [result, setResult] = useState(calculator());
  useInterval(() => {
    const newResult = calculator();
    if (newResult !== result) {
      setResult(newResult);
    } else {
      setResult(result);
    }
  }, delay);

  return result;
};

const Timer = (props) => {
  const navigate = useNavigate();
  const classTime = props.startTime;

  let durationTime;
  if (
    Number(props.startTime.slice(11, 13)) < Number(props.endTime.slice(11, 13))
  ) {
    durationTime =
      Number(props.endTime.slice(11, 13)) -
      Number(props.startTime.slice(11, 13));
  } else {
    durationTime =
      24 -
      Number(props.startTime.slice(11, 13)) +
      Number(props.endTime.slice(11, 13));
  }

  const initTime = () => {
    if (new Date(classTime) - new Date() < 0) {
      return Math.floor(
        (durationTime * 60 * 60 * 1000 - (new Date() - new Date(classTime))) /
          1000
      );
    } else {
      return durationTime * 60 * 60;
    }
  };

  const [time, setTime] = useState(initTime()); // 여기서 타이머 시간 설정(1시간 or 2시간)
  const [targetTime, setTargetTime] = useState(0); // 마지막 시간(0시 0분 0초)

  const isNotYet = useResultOfIntervalCalculator(
    () => new Date(classTime) - new Date() > 0,
    10
  );

  useEffect(() => {
    if (isNotYet) {
      setTime(durationTime * 60 * 60);
    }

    const id = setInterval(() => {
      if (time === targetTime) {
        // 0초 되면 강사 기본 점수 지급
        updatePoint2(props.lecturerId, 10);
        clearInterval(id); // clearing을 통해 해당 interval 정리
        // 메인으로 보내기
        navigate("/", { replace: true });
      } else {
        setTime(time - 1);
      }
    }, 1000); // 1초 단위로 작동

    return () => {
      clearInterval(id);
    };
  }, [time, targetTime]);

  const hours = String(Math.floor(time / 3600)); // 시
  const minutes = String(Math.floor((time % 3600) / 60)); // 분
  const seconds = String(time % 60); // 초

  if (isNotYet) {
    return <STimer>수업 시작 전입니다.</STimer>;
  } else {
    if (time < 300) {
      return (
        <SAlert>
          {hours.padStart(2, "0")} : {minutes.padStart(2, "0")} :{" "}
          {seconds.padStart(2, "0")}
        </SAlert>
      );
    } else {
      return (
        <STimer>
          {hours.padStart(2, "0")} : {minutes.padStart(2, "0")} :{" "}
          {seconds.padStart(2, "0")}
        </STimer>
      );
    }
  }
};

export default Timer;
