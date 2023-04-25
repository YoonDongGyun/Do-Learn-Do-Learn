import React, { useContext, useEffect, useState } from "react";
import {
  BoardDataContext,
  LoginStateContext,
  LoginStateHandlerContext,
  UnreadMessageContext,
} from "../../App";
import { SButton, SButtonBox, SNone } from "./styles";
import {
  cancelEnrollAPI,
  enrollClassAPI,
  enrollLecturerAPI,
  fixClassAPI,
  lecListAPI,
  stuListAPI,
  deleteClassAPI,
} from "../../utils/api/boardAPI";

const LectureModalButton = ({ data, setOpen, Luid }) => {
  const { flag, setFlag } = useContext(BoardDataContext);
  const [check, setCheck] = useState(false);
  const { isLogined, userInfo } = useContext(LoginStateContext);
  const { handleSnackbarInfo } = useContext(LoginStateHandlerContext);
  const { setStateMessageUpdate } = useContext(UnreadMessageContext);

  // API 요청 사항 정리 =================================
  // 수강 신청
  const enrollClass = async () => {
    await enrollClassAPI(userInfo.id, data.id);
    setFlag(!flag);
    setOpen(false);
  };

  // 강사 신청
  const enrollLecturer = async () => {
    await enrollLecturerAPI(userInfo.id, data.id);
    setFlag(!flag);
    setOpen(false);
  };

  // 폐강
  const deleteClass = async () => {
    await deleteClassAPI(data.id);
    setFlag(!flag);
    setOpen(false);
    handleSnackbarInfo({
      state: true,
      message: "생성한 강의 게시글이 정상적으로 삭제되었습니다",
    });
  };

  // 신청 취소
  const cancelClass = async () => {
    await cancelEnrollAPI(userInfo.id, data.id, setCheck);
    setFlag(!flag);
    setOpen(false);
    handleSnackbarInfo({
      state: true,
      message: "강의 신청이 정상적으로 취소되었습니다",
    });
  };

  // 모집 완료
  const fixClass = async () => {
    await fixClassAPI(data.id, Luid, setStateMessageUpdate);
    setFlag(!flag);
    setOpen(false);
    handleSnackbarInfo({
      state: true,
      message: "강의가 정상적으로 확정되었습니다",
    });
  };

  // 강사 목록 호출
  // LectureModal 클릭시 즉시 확인
  const [stuList, setStuList] = useState([]);
  const [lecList, setLecList] = useState([]);
  useEffect(() => {
    stuListAPI(data.id, setStuList);
    lecListAPI(data.id, setLecList, setCheck);
  }, []);
  // ================================================

  if (!isLogined) {
    return "";
  } else {
    if (data.uid === userInfo.id) {
      // 방장이고, 모집완료 이전
      if (data.isFixed === 0) {
        return (
          <>
            {/* 신청 강사 목록이 비어있지 않은 경우에는 목록을 보여주고 그 외에는 공백 */}
            <SButtonBox>
              <SButton onClick={deleteClass}>강의삭제</SButton>
              {Luid === "none" ? (
                <SNone disabled>모집완료</SNone>
              ) : (
                <SButton onClick={fixClass}>모집완료</SButton>
              )}
            </SButtonBox>
          </>
        );
        // 방장이고, 모집완료 이후
      }
    } else if (stuList.includes(userInfo.id) || lecList.includes(userInfo.id)) {
      if (data.isFixed === 0) {
        return (
          <SButtonBox>
            <SButton onClick={cancelClass}>신청취소</SButton>
          </SButtonBox>
        );
      }
    } else if (check) {
      return (
        <SButtonBox>
          <SButton onClick={enrollLecturer}>강사 신청</SButton>
          {stuList.length === data.maxCnt ? (
            <SNone disabled>마감</SNone>
          ) : (
            <SButton onClick={enrollClass}>수강생 신청</SButton>
          )}
        </SButtonBox>
      );
    }
  }
};

export default LectureModalButton;
