import React from "react";
import {
  SContainer,
  SStudentsContainer,
  SLecturerCameraContainer,
  SLecturerCamera,
} from "./styles";

const LectureCameraContainer = () => {
  return (
    <SContainer>
      <SStudentsContainer>
        <div className="first-user">1</div>
        <div>2</div>
        <div>3</div>
        <div>4</div>
        <div className="last-user">5</div>
      </SStudentsContainer>
      <SLecturerCameraContainer>
        <SLecturerCamera></SLecturerCamera>
      </SLecturerCameraContainer>
    </SContainer>
  );
};

export default LectureCameraContainer;
