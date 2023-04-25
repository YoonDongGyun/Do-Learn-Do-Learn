import React from "react";
import Navbar from "../../components/Navbar/index";
import Typing from "../../components/Typing/index";
import { SContainer } from "./styles";
import Lottie from "react-lottie";
import animationData from "../../assets/images/HOME";
import SmallScheduleToggle from "../../components/SmallScheduleToggle";
import RankingList from "../../components/RankingList";
import { LoginStateContext } from "../../App";
import { useContext } from "react";

const Home = () => {
  const { isLogined } = useContext(LoginStateContext);

  const defaultOptions = {
    loop: true,
    autoplay: true,
    animationData: animationData,
    rendererSettings: {
      preserveAspectRatio: "xMidYMid slice",
    },
  };

  return (
    <SContainer>
      <Navbar />

      <section className="main__section">
        <div>
          <div className="main__content">
            {/* <h1>Do Learn, Do Run </h1> */}
            {/* <h3>우리가 만들고, 우리가 배우고</h3> */}
            <h3>원하는 강의</h3>
            <h3>이젠 찾지말고 직접 만들어요</h3>
            <h1>두런두런에서</h1>
            {/* <h3>우리가 만들어가는 강의</h3>
            <h1>두런두런 하세요</h1> */}
            <h4>#실시간 &nbsp;#신개념 &nbsp;#맞춤형 강의 &nbsp;#채팅</h4>
            <h4>#나만의 수업 &nbsp;#마일리지 획득</h4>
          </div>
        </div>
        <div className="lottie-container">
          <Lottie options={defaultOptions} />
        </div>
        {isLogined && <SmallScheduleToggle />}
      </section>
      <Typing />
      <div
        style={{
          backgroundColor: "#f8f8f8",
          paddingTop: "30px",
          borderRadius: "20% 20% 0 0",
        }}
      >
        <RankingList />
      </div>
    </SContainer>
  );
};

export default Home;
