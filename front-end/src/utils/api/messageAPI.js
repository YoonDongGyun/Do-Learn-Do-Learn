import axios from "axios";
import { cancleFixedLectureAPI, updatePoint } from "./lectureAPI";
import { BASE_URL } from "./URL";

const axiosDefaultURL = BASE_URL;

// 유저 정보를 최신화하는 함수 (유저 정보를 가져와서 갱신시키는 함수)
export const getUnreadMessageCnt = (setUnreadMessageCnt) => {
  const id = localStorage.getItem("id");
  const accessToken = localStorage.getItem("accessToken");
  // api를 통해 유저 정보를 받아와서 저장
  axios
    .get(`${axiosDefaultURL}/message/uncheck/user/${id}`, {
      headers: {
        Authentication: accessToken,
      },
    })
    .then((response) => {
      // 유저 정보 갱신
      const unreadMessage = response.data.response;
      setUnreadMessageCnt(unreadMessage.length);
    })
    .catch((error) => {
      // 실패하면
      const errorCode = error.response.data.code;
      if (errorCode === "403") {
        // 로컬스토리지에 있는 리프레시 토큰으로 엑세스 토큰 재발급 api 요청
        const refreshToken = localStorage.getItem("refreshToken");
        axios
          .post(
            `${axiosDefaultURL}/user/access`,
            {},
            {
              headers: {
                Authentication: refreshToken,
              },
            }
          )
          .then((response) => {
            // 요청 성공하면 응답받은 엑세스 토큰을 로컬 스토리지에 저장
            const responseData = response.data.response;
            localStorage.setItem("accessToken", responseData);
          })
          .then((response) => {
            const id = localStorage.getItem("id");
            const accessToken = localStorage.getItem("accessToken");
            // api를 통해 메시지 정보를 받아와서 저장
            axios
              .get(`${axiosDefaultURL}/message/uncheck/user/${id}`, {
                headers: {
                  Authentication: accessToken,
                },
              })
              .then((response) => {
                // 유저 정보 갱신
                const unreadMessage = response.data.response;
                setUnreadMessageCnt(unreadMessage.length);
              })
              .catch((error) => {
                console.log(error.response);
              });
          })
          .catch((error) => {
            console.log(error.response);
          });
      }
    });
};

// 받은 메시지 모두 불러오는 요청
export const getMessageListAPI = async (userId, setMessageData) => {
  const res = await axios.get(`${axiosDefaultURL}/message/user/${userId}`);
  setMessageData(res.data.response);
};

// 메시지 삭제 요청
export const deleteMessageAPI = (
  // messageId,
  // setStateMessageUpdate,
  messageId,
  setStateMessageUpdate,
  checkState,
  setCheckState
) => {
  const accessToken = localStorage.getItem("accessToken");
  axios
    .delete(
      `${axiosDefaultURL}/message/${messageId}`,
      {},
      {
        headers: {
          Authentication: accessToken,
        },
      }
    )
    .then(() => {
      setStateMessageUpdate(true);
      setCheckState(!checkState);
    });
};

// 메시지 읽음 상태로 상태 변경
export const changeMessageReadStateAPI = async (
  id,
  setStateMessageUpdate,
  checkState,
  setCheckState
) => {
  const accessToken = localStorage.getItem("accessToken");
  await axios
    .put(
      `${axiosDefaultURL}/message`,
      { id },
      {
        headers: {
          Authentication: accessToken,
        },
      }
    )
    .then(() => {
      setStateMessageUpdate(true);
      setCheckState(!checkState);
    });
};

// 메시지 보내기(확정)
export const sendMessageAPI = async (
  bid,
  content,
  type,
  setStateMessageUpdate
) => {
  const accessToken = localStorage.getItem("accessToken");
  const res = await axios.post(
    `${axiosDefaultURL}/message`,
    { bid, content, type },
    {
      headers: {
        Authentication: accessToken,
      },
    }
  );
  setStateMessageUpdate(true);
};

// 메시지 보내기(폐강)
export const sendCnacleMessageAPI = async (
  bid,
  content,
  type,
  setStateMessageUpdate,
  lid,
  uid,
  setScheduledLecture,
  handleUserInfo
) => {
  const accessToken = localStorage.getItem("accessToken");
  await axios.post(
    `${axiosDefaultURL}/message`,
    { bid, content, type },
    {
      headers: {
        Authentication: accessToken,
      },
    }
  );
  setStateMessageUpdate(true);
  // 메시지 보내기 성공했으면, 일정에서 삭제하는 api 호출
  cancleFixedLectureAPI(lid, uid, setScheduledLecture);
  // 취소한 강사의 마일리지 점수 업데이트(-10점)
  updatePoint(uid, -10, handleUserInfo);
};
