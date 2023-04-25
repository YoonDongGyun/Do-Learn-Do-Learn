import axios from "axios";
import { BASE_URL } from "./URL";

const axiosDefaultURL = BASE_URL;

// 유저 회원탈퇴 api를 요청하는 함수
export const deleteUserAPI = () => {
  // 로컬스토리지에서 엑세스토큰과 유저 id 가져오기
  const accessToken = localStorage.getItem("accessToken");
  const userId = localStorage.getItem("id");

  // 회원 탈퇴 api 요청
  axios
    .delete(`${axiosDefaultURL}/user/${userId}`, {
      Authentication: accessToken,
    })
    .then((response) => {
      // 회원 탈퇴 요청 api 성공하면 로컬스토리지 비우고 메인페이지 이동시키기
      localStorage.clear();
      // 라우터를 이동시키지 않고, window를 이동시켜서 정보를 새로 할당받게 한다.
      // 라우터를 이동시키면 정보가 남아있어서 문제 발생
      const mainPage = window.location.origin;
      window.location.href = mainPage;
    })
    .catch((error) => {
      console.log(error.response);
    });
};

// 유저 로그아웃 api를 요청하는 함수
export const logoutAPI = (setIsLogined) => {
  const accessToken = localStorage.getItem("accessToken");
  const id = localStorage.getItem("id");
  // 엑세스 토큰이 있으면
  if (accessToken !== null) {
    // logout api 연결
    axios
      .post(
        `${axiosDefaultURL}/user/logout/${id}`,
        {},
        {
          headers: {
            Authentication: accessToken,
          },
        }
      )
      .then((response) => {
        // 요청 성공하면
        // 로그아웃 처리
        setIsLogined(false);
        // localStorage 비우기
        localStorage.clear();
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
              // 새로 저장한 엑세스 토큰으로 로그아웃 api 요청
              const accessToken = localStorage.getItem("accessToken");
              axios
                .post(
                  // id 수정해야 됨
                  `${axiosDefaultURL}/user/logout/1`,
                  {},
                  {
                    headers: {
                      Authentication: accessToken,
                    },
                  }
                )
                .then((response) => {
                  // 요청 성공하면
                  // 로그아웃 처리
                  setIsLogined(false);
                  // localStorage 비우기
                  localStorage.clear();
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
  }
};

// 유저 정보를 최신화하는 함수 (유저 정보를 가져와서 갱신시키는 함수)
export const getUserInfoAndUpdate = (setUserInfo) => {
  const id = localStorage.getItem("id");
  const accessToken = localStorage.getItem("accessToken");
  // api를 통해 유저 정보를 받아와서 저장
  axios
    .get(`${axiosDefaultURL}/user/${id}`, {
      headers: {
        Authentication: accessToken,
      },
    })
    .then((response) => {
      // 유저 정보 갱신
      const userData = response.data.response;
      setUserInfo(userData);
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
            // api를 통해 유저 정보를 받아와서 저장
            axios
              .get(`${axiosDefaultURL}/user/${id}`, {
                headers: {
                  Authentication: accessToken,
                },
              })
              .then((response) => {
                // 유저 정보 갱신
                const userData = response.data.response;
                setUserInfo(userData);
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

// 로그인 api를 요청하는 함수
export const loginAPI = (
  email,
  password,
  handleUserInfo,
  handleIsLogined,
  setIsCorrectEmail,
  setIsCorrectPassword,
  navigate
) => {
  // 자동로그아웃을 위한 핸들러 변수
  let timeoutHandler = null;
  // 리프레시 토큰 유효시간 : 1일
  // 자동로그아웃 -> 23시간 후 실행
  const logoutTimeInterval = 1000 * 60 * 60 * 23;
  // 테스트용 5초 후 자동로그아웃

  axios
    .post(`${axiosDefaultURL}/user/login`, {
      email,
      password,
    })
    .then((response) => {
      const responseData = response.data.response;
      localStorage.clear();
      // 로그인 성공하면 localStorage에 토큰과 유저 id 저장
      localStorage.setItem("accessToken", responseData.accessToken);
      localStorage.setItem("refreshToken", responseData.refreshToken);
      localStorage.setItem("id", responseData.id);
      // 유저 정보 상태 변경
      handleUserInfo(responseData);
      // 로그인 상태 변경
      handleIsLogined();

      // setTimeout() 함수를 통해 리프레시 토큰 끝나기 1시간 전에 자동로그아웃 시키기
      // 유저가 리프레시 토큰 유효기간 만료 전 로그인과 로그아웃을 연속으로 실행할 경우를 방지하기 위해
      // setTimeout()을 먼저 clear 시키고 setTimeout() 함수 로직 작성
      clearTimeout(timeoutHandler);
      timeoutHandler = setTimeout(() => {
        const accessToken = localStorage.getItem("accessToken");
        const id = localStorage.getItem("id");
        if (accessToken !== null) {
          // logout api 연결
          axios
            .post(
              `${axiosDefaultURL}/user/logout/${id}`,
              {},
              {
                headers: {
                  Authentication: accessToken,
                },
              }
            )
            .then((response) => {
              // 요청 성공하면
              // 로그아웃 처리
              // localStorage 비우기
              localStorage.clear();
              // 적용을 위해 페이지 새로고침
              window.location.reload();
            })
            .catch((error) => {
              console.log(error.response);
            });
        }
      }, logoutTimeInterval);

      // 메인페이지로 이동
      navigate("/");
    })
    .catch((error) => {
      console.log(error.response);
      if (error.response.data.response === "이메일 형식에 맞지 않습니다.") {
        setIsCorrectEmail("이메일 형식에 맞지 않습니다.");
        setIsCorrectPassword("");
      } else if (error.response.data.response === "없는 사용자입니다.") {
        setIsCorrectEmail("이메일을 확인해주세요.");
        setIsCorrectPassword("");
      } else if (
        error.response.data.response ===
        "비밀번호는 9~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요."
      ) {
        setIsCorrectPassword(
          "비밀번호는 9~16자 영문 대 소문자, 숫자, 특수문자를 사용합니다."
        );
        setIsCorrectEmail("");
      } else if (error.response.data.response === "비밀번호가 옳지 않습니다.") {
        setIsCorrectPassword("비밀번호가 옳지 않습니다.");
        setIsCorrectEmail("");
      }
    });
};

// 회원가입 시 이메일 중복을 확인하는 api를 요청하는 함수
export const duplicatedEmailCheckAPI = (
  isDuplicatedEmail,
  email,
  setIsNext,
  setIsDuplicatedEmail,
  setOpen
) => {
  if (isDuplicatedEmail) {
    axios
      .post(`${axiosDefaultURL}/user/check-email/${email}`)
      .then((response) => {
        // setIsNext(true);
      })
      .catch((error) => {
        if (error.response.data.response === "이미 존재하는 이메일입니다.") {
          setIsDuplicatedEmail(false);
          setOpen(true);
          setIsNext(false);
        }
      });
  }
};

// 회원가입 api를 요청하는 함수
export const signupAPI = (
  username,
  email,
  password,
  selfIntroduction,
  blogLink,
  youtubeLink,
  instagramLink,
  facebookLink,
  navigate
) => {
  axios
    .post(`${axiosDefaultURL}/user`, {
      // 보내야 될 데이터
      name: username,
      email,
      password,
      info: selfIntroduction,
      blog: blogLink,
      youtube: youtubeLink,
      instagram: instagramLink,
      facebook: facebookLink,
    })
    .then((response) => {
      // 회원가입 성공했으면 메인 페이지로 이동
      navigate("/login");
    })
    .catch((error) => {
      console.log("서버에 데이터 보내기 실패!");
      console.log(error);
    });
};

// 유저의 확정된 강의 목록 api를 요청하는 함수
export const getFixedLecture = (userInfo, setTodayScedule) => {
  const accessToken = localStorage.getItem("accessToken");
  let totalFixedLectures = [];
  axios
    .get(
      `${axiosDefaultURL}/user/fixed-lecture/${userInfo.id}`,
      {},
      {
        headers: {
          Authentication: accessToken,
        },
      }
    )
    .then((response) => {
      const responseData = response.data.response;
      totalFixedLectures = [...responseData];
      const todayLectures = totalFixedLectures.filter((item) => {
        const startTime = item.startTime;
        const year = new Date().getFullYear();
        const month = new Date().getMonth() + 1;
        const day = new Date().getDate();
        if (
          year === +startTime.slice(0, 4) &&
          month === +startTime.slice(5, 7) &&
          day === +startTime.slice(8, 10)
        ) {
          return true;
        }
      });
      setTodayScedule(todayLectures);
    })
    .catch((error) => {
      console.log(error.response);
    });
};

// 미확정 강의 내역 불러오는 api를 요청하는 함수 (모든 강의)
export const getUnScheduledLectureAPI = (userId, setFunction) => {
  const accessToken = localStorage.getItem("accessToken");
  axios
    .get(
      `${axiosDefaultURL}/user/request-lecture/${userId}`,
      {},
      {
        headers: {
          Authentication: accessToken,
        },
      }
    )
    .then((res) => {
      setFunction(res.data.response);
    });
};

// 미확정 강의 내역 불러오는 api를 요청하는 함수 (방장인 글)
export const getUnScheduledLectureHostAPI = (
  userId,
  setUnScheduledLectureList
) => {
  const accessToken = localStorage.getItem("accessToken");
  axios
    .get(
      `${axiosDefaultURL}/user/request-lecture/${userId}/host`,
      {},
      {
        headers: {
          Authentication: accessToken,
        },
      }
    )
    .then((res) => {
      setUnScheduledLectureList(res.data.response);
    });
};

// 미확정 강의 내역 불러오는 api를 요청하는 함수 (강사)
export const getUnScheduledLectureInstructorAPI = (
  userId,
  setUnScheduledLectureList
) => {
  const accessToken = localStorage.getItem("accessToken");
  axios
    .get(
      `${axiosDefaultURL}/user/request-lecture/${userId}/instructor`,
      {},
      {
        headers: {
          Authentication: accessToken,
        },
      }
    )
    .then((res) => {
      setUnScheduledLectureList(res.data.response);
    });
};

// 미확정 강의 내역 불러오는 api를 요청하는 함수 (수강생)
export const getUnScheduledLectureStudentAPI = (
  userId,
  setUnScheduledLectureList
) => {
  const accessToken = localStorage.getItem("accessToken");
  axios
    .get(
      `${axiosDefaultURL}/user/request-lecture/${userId}/student`,
      {},
      {
        headers: {
          Authentication: accessToken,
        },
      }
    )
    .then((res) => {
      setUnScheduledLectureList(res.data.response);
    });
};

// 확정 강의 내역 불러오는 api를 요청하는 함수(커스텀 api => 달력에서 쓰기 위함)
const preprocessingData = (obj) => {
  obj.map((item) => {
    item.start = item.startTime;
    item.end = item.endTime;
    item.color = "black";
    item.backgroundColor = "black";
    item.display = "item-list";
    delete item.startTime;
    delete item.endTime;
  });
};

export const getScheduledLectureAPI = (userId, setScheduledLecture) => {
  const accessToken = localStorage.getItem("accessToken");
  axios
    .get(
      `${axiosDefaultURL}/user/fixed-lecture/${userId}`,
      {},
      {
        headers: {
          Authentication: accessToken,
        },
      }
    )
    .then((res) => {
      const result = res.data.response;
      // 받아온 데이터를 전처리해서 반환
      preprocessingData(result);
      setScheduledLecture(result);
    })
    .catch((e) => {
      console.log(e);
    });
};

// DB에 프로필 수정 요청(프로필 사진 외 부가 정보)
export const updateUserInfoAPI = (data, handleUserInfo) => {
  axios
    .put(`${axiosDefaultURL}/user`, data, {
      headers: {
        Authentication: localStorage.getItem("accessToken"),
      },
      // 성공하면 app에서 관리중인 유저 데이터 정보도 업데이트
    })
    .then((res) => {
      handleUserInfo(res.data.response);
    });
};

// DB에 프로필 사진 수정 요청
export const updateProfileImgAPI = (data, img_file, handleUserInfo) => {
  const formData = new FormData();
  formData.append("profileImg", img_file);
  axios
    .post(`${axiosDefaultURL}/user/upload-img/${data.id}`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
    .then((res) => {
      handleUserInfo(res.data.response);
    })
    .then(() => {
      updateUserInfoAPI(data, handleUserInfo);
    });
};

// 확정된 강의의 강사 정보를 가져오는 함수
export const getUserInfo = (lecturerId, setLecturerInfo) => {
  const accessToken = localStorage.getItem("accessToken");
  axios
    .get(`${axiosDefaultURL}/user/${lecturerId}`, {
      headers: {
        Authentication: accessToken,
      },
    })
    .then((response) => {
      const userData = response.data.response;
      setLecturerInfo(userData);
    })
    .catch((error) => {
      console.log(error.response);
    });
};

// 상위 8명의 유저 데이터를 불러오는 함수
export const getSortedUserByPoint = (setRankingList) => {
  const accessToken = localStorage.getItem("accessToken");
  axios
    .get(`${axiosDefaultURL}/user/sort-point`)
    .then((response) => {
      const responseData = response.data.response;
      if (responseData.length <= 8) {
        setRankingList(responseData.slice(0));
      } else {
        setRankingList(responseData.slice(0, 8));
      }
    })
    .catch((error) => error.response);
};
