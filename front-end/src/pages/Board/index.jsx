import React, { useContext, useEffect, useState } from "react";
import Navbar from "../../components/Navbar";
import SearchBar from "../../components/SearchBar";
import WriteButton from "../../components/WriteButton";
import BoardList from "../../components/BoardList";
import { Grid } from "@mui/material";
import SmallSchedule from "../../components/SmallSchedule";
import { SOutterBox, SInnerBox } from "./styles";
import { LoginStateContext } from "../../App";
import {
  getScheduledLectureAPI,
  getUnScheduledLectureAPI,
} from "../../utils/api/userAPI";

const Board = () => {
  const { isLogined, userInfo } = useContext(LoginStateContext);
  const [scheduled, setScheduled] = useState([]); // Scheduled 목록 담을 변수
  const [unscheduled, setUnscheduled] = useState([]); // UnScheduled 목록 담을 변수

  useEffect(() => {
    // 동시간대 중복 수강신청 방지를 위한 작업
    getScheduledLectureAPI(userInfo.id, setScheduled);
    getUnScheduledLectureAPI(userInfo.id, setUnscheduled);
  }, []);

  return (
    <>
      <Navbar />
      <Grid container>
        {isLogined ? (
          <>
            <Grid item xs={0} md={1.5} />
            <Grid item xs={12} md={9}>
              <SOutterBox>
                <div style={{ display: "flex", flexDirection: "column" }}>
                  <SearchBar />
                  <SInnerBox>
                    <WriteButton
                      scheduled={scheduled}
                      unscheduled={unscheduled}
                    />
                    <BoardList />
                  </SInnerBox>
                </div>
                <SmallSchedule />
              </SOutterBox>
            </Grid>
            <Grid item xs={0} md={1.5} />
          </>
        ) : (
          <>
            <Grid item xs={0} md={3} />
            <Grid item xs={12} md={6}>
              <SOutterBox>
                <div style={{ display: "flex", flexDirection: "column" }}>
                  <SearchBar />
                  <SInnerBox>
                    <WriteButton />
                    <BoardList />
                  </SInnerBox>
                </div>
              </SOutterBox>
            </Grid>
            <Grid item xs={0} md={3} />
          </>
        )}
      </Grid>
    </>
  );
};

export default Board;
