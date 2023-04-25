import React from "react";
import { SContainer } from "./styles";
import error404 from "../../assets/images/error404.png";

const NotFound = () => {
  return (
    <SContainer>
      <img src={error404} alt="404error" />
      <h1>죄송합니다. 현재 찾을 수 없는 페이지를 요청 하셨습니다.</h1>
      <p>
        존재하지 않는 주소를 입력하셨거나,
        <br />
        요청하신 페이지의 주소가 변경, 삭제되어 찾을 수 없습니다.
      </p>
    </SContainer>
  );
};

export default NotFound;
