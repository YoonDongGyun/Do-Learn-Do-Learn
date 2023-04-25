import axios from "axios";
import { BASE_URL } from "./URL";
import { sendMessageAPI } from "./messageAPI";

const BOARD_URL = `${BASE_URL}/board`;
const PROFILE_URL = `${BASE_URL}/user`;
const LECTURE_URL = `${BASE_URL}/lecture`;

// 강의 목록 요청 API
export const boardListAPI = async (setList) => {
  try {
    const res = await axios.get(`${BOARD_URL}/list`);
    setList(res.data.response);
  } catch (err) {
    console.log("강의 목록 출력 실패");
    setList([]);
  }
};

// 신규 강의 등록 API
export const newBoardAPI = async (
  uid,
  tid,
  title,
  maxCnt,
  content,
  summary,
  startTime,
  endTime,
  deadline,
  isFixed
) => {
  try {
    await axios.post(`${BOARD_URL}`, {
      uid,
      tid,
      title,
      maxCnt,
      content,
      summary,
      startTime,
      endTime,
      deadline,
      isFixed,
    });
  } catch (err) {
    console.log(err);
    console.log("신규 강의 등록 실패");
  }
};

// 수강 신청 API
export const enrollClassAPI = async (uid, bid) => {
  try {
    await axios.post(`${BOARD_URL}/student`, {
      uid,
      bid,
    });
  } catch (err) {
    if (err.response.data.response === "신청 학생 수를 초과하였습니다") {
      console.log("신청 가능 인원 초과");
    }
    console.log("수강 신청 실패");
  }
};

// 강사 신청 API
export const enrollLecturerAPI = async (uid, bid) => {
  try {
    await axios.post(`${BOARD_URL}/instructor`, {
      uid,
      bid,
    });
  } catch (err) {
    console.log(err);
    console.log("강사 신청 실패");
  }
};

// 폐강 API
export const deleteClassAPI = async (board) => {
  try {
    await axios.delete(`${BOARD_URL}/${board}`);
  } catch (err) {
    console.log(err);
    console.log("폐강 실패");
  }
};

// 신청 취소 API
export const cancelEnrollAPI = async (user, lecture, setCheck) => {
  try {
    await axios.delete(`${BOARD_URL}/apply/${user}/${lecture}`);
    setCheck(false);
  } catch (err) {
    console.log(err);
    console.log("신청 취소 실패");
  }
};

// 모집 완료 API
export const fixClassAPI = async (lecture, uid, setStateMessageUpdate) => {
  try {
    const res = await axios.post(`${LECTURE_URL}/fix`, {
      bid: lecture,
      Luid: uid,
    });
    // 강의 확정 성공하면 확정 메시지 보내기
    sendMessageAPI(res.data.response.bid, "", "confirm", setStateMessageUpdate);
  } catch (err) {
    console.log(err);
    console.log("강의 확정 실패");
  }
};

// 강의 검색 API
export const searchAPI = async (keyword, setList, setIsEmpty) => {
  try {
    const res = await axios.get(`${BOARD_URL}/search/${keyword}`);
    setList(res.data.response);
    setIsEmpty(false);
  } catch (err) {
    if (err.response.data.response === "게시물이 없습니다.") {
      setIsEmpty(true);
      setList([]);
    }
  }
};

// 강사 후보 명단 확인 API
export const lecturerNameAPI = async (board, setNameList) => {
  try {
    const res = await axios.get(`${BOARD_URL}/instructor-list/${board}`);
    if (res.data.response === "신청한 강사가 없습니다") {
      setNameList([]);
    } else {
      setNameList(res.data.response);
    }
  } catch (err) {
    console.log(err);
    console.log("강사 이름 가져오기 실패");
  }
};

// 강사 목록 확인 API
export const lecListAPI = async (board, setLecList, setCheck) => {
  try {
    const list = [];
    const res = await axios.get(`${BOARD_URL}/instructor-list/${board}`);
    if (res.data.response === "신청한 강사가 없습니다") {
      setLecList([]);
    } else {
      res.data.response.map((item) => {
        list.push(item.uid);
      });
      setLecList(list);
    }
    setCheck(true);
  } catch (err) {
    console.log(err);
    console.log("강사 목록 가져오기 실패");
  }
};

// 수강생 목록 확인 API
export const stuListAPI = async (board, setStuList) => {
  try {
    const list = [];
    const res = await axios.get(`${BOARD_URL}/student-list/${board}`);
    if (res.data.response === "신청한 학생이 없습니다.") {
      setStuList([]);
    } else {
      res.data.response.map((item) => {
        list.push(item.uid);
      });
      setStuList(list);
    }
  } catch (err) {
    console.log(err);
    console.log("수강생 목록 가져오기 실패");
  }
};

// 강사 프로필 확인 API
export const lecProfileAPI = async (id, setData, setIsUser, navigate) => {
  try {
    const res = await axios.get(`${PROFILE_URL}/summary-info/${id}`);
    setData(res.data.response);
    setIsUser(true);
  } catch (err) {
    console.log(err);
    console.log("프로필 정보 반환 실패");
    navigate("/not-found");
  }
};

// 확정 강의(일정 상세보기) 강사/수강생 정보 확인 API
export const getFixedLectureInfo = async (
  lid,
  setLectureInfo,
  setInstructorInfo,
  setStudentsInfo,
  setCheckModalState
) => {
  const res = await axios.get(`${LECTURE_URL}/list/${lid}`);
  let lecture;
  let instructor;
  let students = [];
  res.data.response.forEach((element) => {
    if (element.memberType === "강사") {
      instructor = element.user;
      lecture = element.lecture;
    } else {
      students.push(element.user);
    }
  });
  setLectureInfo(lecture); // 확정 강의 정보
  setInstructorInfo(instructor); // 강사 정보
  setStudentsInfo(students); // 수강생(들) 정보
  setCheckModalState(true);
};
