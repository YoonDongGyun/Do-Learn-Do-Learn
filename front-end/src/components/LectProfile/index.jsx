import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router";
import { lecProfileAPI } from "../../utils/api/boardAPI";
import Profile from "../Profile";
import { Grid } from "@mui/material";
import walkingChick from "../../assets/images/walkingChick.gif";
import { SContainer } from "./styles";

const LecProfile = () => {
  // URL의 강사 아이디를 useParam로 받아오기
  const id = useParams().lid;
  const [data, setData] = useState([]);
  const [isUser, setIsUser] = useState(false); // 유저 ID 존재 여부 확인
  const navigate = useNavigate();

  useEffect(() => {
    lecProfileAPI(id, setData, setIsUser, navigate);
  }, [isUser]);

  if (isUser) {
    return (
      <Grid container style={{ height: "100%" }}>
        <Grid item xs={0} md={2.5} style={{ height: "100%" }} />
        <Grid item xs={12} md={7} style={{ height: "100%" }}>
          <SContainer>
            <h1 className="headline">
              📝강사 {data.name}님의 프로필 정보입니다!
            </h1>
            <Profile
              handleProfileEditBtn={null}
              user={data}
              userState={"other"}
              chick={true}
            />
            <div className="walkingChick_container">
              <img
                className="walkingChick"
                src={walkingChick}
                alt="삐약이는 삐약삐약"
              />
            </div>
          </SContainer>
        </Grid>
        <Grid item xs={0} md={2.5} style={{ height: "100%" }} />
      </Grid>
    );
  } else {
    return null;
  }
};

export default LecProfile;
