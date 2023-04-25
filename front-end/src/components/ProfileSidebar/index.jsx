import React from "react";
import { Link } from "react-router-dom";
import {
  SSidebarContainer,
  SButtonContainer,
  SUserDeleteButtonContainer,
} from "./styles";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEnvelope } from "@fortawesome/free-solid-svg-icons";
import { faUser } from "@fortawesome/free-solid-svg-icons";
import { faCalendarCheck } from "@fortawesome/free-regular-svg-icons";
import { faCalendarXmark } from "@fortawesome/free-regular-svg-icons";
import WarningModal from "../WarningModal";
import { deleteUserAPI } from "../../utils/api/userAPI";
import ProfileClock from "../ProfileClock";

const ProfileSidebar = (props) => {
  const handleDeleteUser = () => {
    deleteUserAPI();
  };

  return (
    <>
      <SSidebarContainer>
        <div className="sidebar__container">
          {/* 프로필 탭 */}
          <Link to={"/mypage"} state="main" style={{ textDecoration: "none" }}>
            <SButtonContainer>
              <div
                className={
                  props.isProfileTabActive
                    ? "active tab__section profile-page"
                    : "tab__section tab-content profile-page"
                }
                onClick={props.handleTabValue}
              >
                <FontAwesomeIcon icon={faUser} />
                &emsp;프로필
              </div>
            </SButtonContainer>
          </Link>
          {/* 일정 탭 */}
          <Link
            to={"/mypage"}
            state="schedule"
            style={{ textDecoration: "none" }}
          >
            <SButtonContainer>
              <div
                className={
                  props.isScheduleTabActive
                    ? "active tab__section schedule-page"
                    : "tab__section tab-content schedule-page"
                }
                onClick={props.handleTabValue}
              >
                <FontAwesomeIcon icon={faCalendarCheck} />
                &emsp;일정
              </div>
            </SButtonContainer>
          </Link>

          {/* 미확정 강의 탭 */}
          <Link
            to={"/mypage"}
            state="unschedule"
            style={{ textDecoration: "none" }}
          >
            <SButtonContainer>
              <div
                className={
                  props.isUnScheduleTabActive
                    ? "active tab__section undecided-lecture-page"
                    : "tab__section tab-content undecided-lecture-page"
                }
                onClick={props.handleTabValue}
              >
                <FontAwesomeIcon icon={faCalendarXmark} />
                &emsp;미확정 강의
              </div>
            </SButtonContainer>
          </Link>
          {/* 메시지함 탭 */}
          <Link
            to={"/mypage"}
            state="message"
            style={{ textDecoration: "none" }}
          >
            <SButtonContainer>
              <div
                className={
                  props.isMessageTabActive
                    ? "active tab__section message-page"
                    : "tab__section tab-content message-page"
                }
                onClick={props.handleTabValue}
              >
                <FontAwesomeIcon icon={faEnvelope} />
                &emsp;메시지함
              </div>
            </SButtonContainer>
          </Link>
          {/* 회원탈퇴 버튼 */}
          <SUserDeleteButtonContainer>
            {/* ProfileSidebar에서 사용시 */}
            <WarningModal
              title="회원 탈퇴 확인"
              warningContent="회원 탈퇴 후에는 아이디와 데이터를 복구할 수 없습니다."
              content="탈퇴를 원하시면 확인을 눌러주세요."
              profileSidebar
              handler={handleDeleteUser}
            />
          </SUserDeleteButtonContainer>
        </div>
        <ProfileClock />
      </SSidebarContainer>
    </>
  );
};

export default ProfileSidebar;
