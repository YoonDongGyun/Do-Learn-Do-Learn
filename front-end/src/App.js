import React from "react";
import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import pMinDelay from "p-min-delay";
import { Suspense } from "react";
import { ThemeProvider } from "styled-components";
import theme from "./theme";
import Loading from "./pages/Loading";
import { useState } from "react";
import { useEffect } from "react";
import { getUserInfoAndUpdate, logoutAPI } from "./utils/api/userAPI";
import { getUnreadMessageCnt } from "./utils/api/messageAPI";
import OauthRedirect from "./pages/OauthRedirect";
import { boardListAPI } from "./utils/api/boardAPI";
import SimpleSnackbar from "./components/SimpleSnackbar";

// 코드 스플리팅 (Code Splitting)
const Home = React.lazy(() => pMinDelay(import("./pages/Home/index"), 1000));
const Login = React.lazy(() => pMinDelay(import("./pages/Login/index"), 1000));
const SingUp = React.lazy(() =>
  pMinDelay(import("./pages/SignUp/index"), 1000)
);
const User = React.lazy(() => pMinDelay(import("./pages/User/index")));
const NotFound = React.lazy(() =>
  pMinDelay(import("./pages/NotFound/index"), 1000)
);
const Board = React.lazy(() => pMinDelay(import("./pages/Board"), 0)); // delay 0
const WriteBoard = React.lazy(() => pMinDelay(import("./pages/WriteBoard"), 0)); // 요 친구도 delay 0
const LecturerProfile = React.lazy(() =>
  pMinDelay(import("./pages/LecturerProfile"), 0)
); // 요 친구도 delay 0
const Lecture = React.lazy(() => pMinDelay(import("./pages/Lecture"), 0)); // 나도 delay 0 할래

// 로그인 상태가 담긴 context API
export const LoginStateContext = React.createContext();
// 로그인 상태를 관리하는 함수가 담긴 context API
export const LoginStateHandlerContext = React.createContext();
// 안읽은 메시지 상태를 관리하는 함수가 담긴 context API
export const UnreadMessageContext = React.createContext();
// 강의 정보와 렌더링 여부(flag)를 관리하는 context API
export const BoardDataContext = React.createContext();

function App() {
  const [list, setList] = useState([]); // 강의 정보 List
  const [flag, setFlag] = useState(false); // 강의 목록 갱신 변수
  const [isLogined, setIsLogined] = useState(false);
  const [userInfo, setUserInfo] = useState({});
  const [unreadMessageCnt, setUnreadMessageCnt] = useState(0);
  const [stateMessageUpdate, setStateMessageUpdate] = useState(false);
  const [snackbarInfo, setSnackbarInfo] = useState({
    state: false,
    message: "",
  });

  // flag의 true/false 여부가 바뀌면 목록 업데이트
  useEffect(() => {
    boardListAPI(setList);
  }, [flag]);

  useEffect(() => {
    if (localStorage.getItem("accessToken") !== null) {
      setIsLogined(true);
      getUnreadMessageCnt(setUnreadMessageCnt);
    } else {
      setIsLogined(false);
    }

    // 로컬 스토리지에 유저 id가 있으면
    if (localStorage.getItem("id") !== null) {
      // 유저 정보 최신화시키기
      getUserInfoAndUpdate(setUserInfo);
    }
  }, []);

  // 안 읽은 메시지 정보 최신화시키기
  useEffect(() => {
    // 로그인 했을 경우 보냄
    if (isLogined) {
      setStateMessageUpdate(false);
      getUnreadMessageCnt(setUnreadMessageCnt);
    }
  }, [stateMessageUpdate, isLogined]);

  // 로그인 상태 관리 함수
  const handleIsLogined = () => {
    setIsLogined(!isLogined);
  };

  // 유저 로그아웃 함수
  const handleLogout = () => {
    logoutAPI(setIsLogined);
  };

  // 유저의 정보를 저장하는 함수
  const handleUserInfo = (info) => {
    setUserInfo(info);
  };

  const handleSnackbarInfo = (info) => {
    setSnackbarInfo(info);
  };

  return (
    <>
      <ThemeProvider theme={theme}>
        <LoginStateContext.Provider value={{ isLogined, userInfo }}>
          <LoginStateHandlerContext.Provider
            value={{
              handleIsLogined,
              handleLogout,
              handleUserInfo,
              handleSnackbarInfo,
            }}
          >
            <UnreadMessageContext.Provider
              value={{
                unreadMessageCnt,
                setStateMessageUpdate,
              }}
            >
              <BoardDataContext.Provider
                value={{ list, flag, setList, setFlag }}
              >
                <Suspense fallback={<Loading />}>
                  <BrowserRouter>
                    <Routes>
                      <Route path="/" element={<Home />} />
                      <Route
                        path="/login"
                        element={isLogined ? <Home /> : <Login />}
                      />
                      <Route
                        path="/signup"
                        element={isLogined ? <Home /> : <SingUp />}
                      />
                      <Route path="/board" element={<Board />} />
                      <Route path="/write" element={<WriteBoard />} />
                      <Route
                        path="/board/profile/:lid"
                        element={<LecturerProfile />}
                      />
                      <Route
                        path="/mypage"
                        element={isLogined ? <User /> : <Home />}
                      />
                      <Route path="/lecture" element={<Lecture />} />
                      <Route
                        path="/oauth-redirect"
                        element={<OauthRedirect />}
                      />
                      <Route path="/not-found" element={<NotFound />} />
                      <Route path="*" element={<NotFound />} />
                    </Routes>
                  </BrowserRouter>
                </Suspense>
              </BoardDataContext.Provider>
            </UnreadMessageContext.Provider>
          </LoginStateHandlerContext.Provider>
        </LoginStateContext.Provider>
      </ThemeProvider>
      {snackbarInfo.state && (
        <SimpleSnackbar
          message={snackbarInfo.message}
          setOpenSnackbar={setSnackbarInfo}
        />
      )}
    </>
  );
}

export default App;
