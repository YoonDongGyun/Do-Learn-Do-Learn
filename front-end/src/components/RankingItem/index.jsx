import React from "react";
import { SContainer, SSNSContainer, SRank } from "./styles";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faInstagram,
  faFacebook,
  faYoutube,
} from "@fortawesome/free-brands-svg-icons";
import defaultProfile from "../../assets/images/defaultProfile.png";
import { faLink } from "@fortawesome/free-solid-svg-icons";
import { BASE_URL } from "../../utils/api/URL";
import { Tooltip } from "@mui/material";

const RankingItem = (props) => {
  const handleMoveToProfile = (e) => {
    if (
      !e.target.className.includes("link") ||
      !e.target.className.includes("icon")
    ) {
      window.open(`/board/profile/${props.item.id}`);
    }
  };

  return (
    <SContainer onClick={(e) => handleMoveToProfile(e)}>
      <div className="sns__container"></div>
      {/* 프로필 사진, 이름, 점수 표시 */}
      <div className="profile-container">
        <img
          src={
            props.item.imgUrl
              ? `${BASE_URL}/user${props.item.imgUrl}`
              : defaultProfile
          }
          alt="profile-img"
        />
        <div>
          <div className="name">{props.item.name}</div>
          <div className="email">{props.item.email}</div>
          <div className="point">{props.item.point}</div>
        </div>
      </div>
      {/* SNS */}
      <SSNSContainer>
        {props.item.blog && (
          <Tooltip title="블로그" placement="bottom">
            <a className="link" href={props.item.blog} target={"_blank"}>
              <FontAwesomeIcon className="icon" icon={faLink} />
            </a>
          </Tooltip>
        )}
        {props.item.youtube && (
          <Tooltip title="유튜브" placement="bottom">
            <a className="link" href={props.item.youtube} target={"_blank"}>
              <FontAwesomeIcon className="icon" icon={faYoutube} />
            </a>
          </Tooltip>
        )}
        {props.item.instagram && (
          <Tooltip title="인스타그램" placement="bottom">
            <a
              className="link"
              href={`https://www.instagram.com/${props.item.instagram}/`}
              target={"_blank"}
            >
              <FontAwesomeIcon className="icon" icon={faInstagram} />
            </a>
          </Tooltip>
        )}
        {props.item.facebook && (
          <Tooltip title="페이스북" placement="bottom">
            <a
              className="link"
              href={`https://facebook.com/${props.item.facebook}/`}
              target={"_blank"}
            >
              <FontAwesomeIcon className="icon" icon={faFacebook} />
            </a>
          </Tooltip>
        )}
      </SSNSContainer>
      {/* 랭크를 표시하기 위한 div */}
      <SRank className="rank">{props.index + 1}</SRank>
    </SContainer>
  );
};

export default RankingItem;
