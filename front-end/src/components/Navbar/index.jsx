import React from "react";
import { Link, NavLink } from "react-router-dom";
import { Box } from "./styles";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBell } from "@fortawesome/free-regular-svg-icons";
import logoImg from "../../assets/images/logo.png";
import defaultProfile from "../../assets/images/defaultProfile.png";
import { useContext } from "react";
import {
  LoginStateContext,
  LoginStateHandlerContext,
  UnreadMessageContext,
} from "../../App";
import { Badge, Grid } from "@mui/material";
import { BASE_URL } from "../../utils/api/URL";

const Navbar = () => {
  // context api를 통해 로그인 상태 받아오기
  const { isLogined, userInfo } = useContext(LoginStateContext);
  const getUserInfo = useContext(LoginStateContext);

  // context api를 통해 로그인 상태 관리 함수들 받아오기
  const { handleLogout } = useContext(LoginStateHandlerContext);
  const { unreadMessageCnt } = useContext(UnreadMessageContext);

  return (
    <Grid container>
      <Grid item xs={0} md={1.5} />
      <Grid item xs={12} md={9}>
        <Box>
          <div className="left-item">
            <NavLink to={"/"}>
              <img src={logoImg} alt="logo" />
            </NavLink>
            <NavLink to={"/board"} className="link link__board">
              강의장
            </NavLink>
          </div>
          <div className="right-item">
            {isLogined && (
              <NavLink to={"/mypage"} className="link username" state="main">
                <img
                  src={
                    getUserInfo.userInfo.imgUrl
                      ? `${BASE_URL}/user${getUserInfo.userInfo.imgUrl}`
                      : defaultProfile
                  }
                  alt="profileImg"
                />
                <span
                  style={{
                    margin: "auto 10px auto 5px",
                    cursor: "pointer",
                  }}
                >
                  {userInfo.name}
                </span>
              </NavLink>
            )}
            {isLogined && (
              <div className="unread-container">
                <Badge
                  // variant="dot"
                  badgeContent={unreadMessageCnt}
                  color="warning"
                  anchorOrigin={{
                    vertical: "top",
                    horizontal: "right",
                  }}
                >
                  <Link
                    to={"/mypage"}
                    state="message"
                    style={{ textDecoration: "none" }}
                  >
                    <FontAwesomeIcon
                      className="unread__notification"
                      icon={faBell}
                    />
                  </Link>
                </Badge>
              </div>
            )}

            <div className="user-state">
              {isLogined ? (
                <>
                  <p className="link logout" onClick={handleLogout}>
                    로그아웃
                  </p>
                </>
              ) : (
                <>
                  <Link to={"/login"} className="link user-state-nuLogined">
                    로그인
                  </Link>
                  <span id="division">|</span>
                  <Link to={"/signup"} className="link user-state-nuLogined">
                    회원가입
                  </Link>
                </>
              )}
            </div>
          </div>
        </Box>
      </Grid>
      <Grid item xs={0} md={1.5} />
    </Grid>
  );
};

export default Navbar;
