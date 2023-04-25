import styled from "styled-components";
import { Tooltip } from "@mui/material";
import { SCard } from "../ProfileCardBox/styles";

export const SSCard = styled(SCard)`
  position: relative;
  height: ${(props) => (props.chick ? "29vw" : "33vw")};
  border: ${(props) => (props.chick ? "8px solid rgb(255, 199, 151)" : "")};
  border-radius: ${(props) => (props.chick ? "20px" : "")};
  box-shadow: ${(props) =>
    props.chick
      ? "20px 10px 40px rgba(255, 132, 24, 0.47)"
      : "5px 5px 30px #c4c4c454"};
  .button__section {
    position: absolute;
    bottom: 2vw;
    left: 50%;
    transform: translate(-50%);
  }
`;

export const SProfileContainer = styled.div`
  font-family: ${(props) => props.theme.fontFamily.Medium};
  width: 100%;
  .cd1tip {
    width: 100%;
    display: flex;
  }

  .tip {
    width: 100%;
    height: 13vw;
    word-wrap: break-word;
    white-space: -moz-pre-wrap;
    white-space: pre-wrap;
    border: 4px dashed ${(props) => props.theme.lightGray};
    color: black;
    padding: calc(1vw + 5px);
    border-radius: 10px;
    font-size: ${(props) => props.theme.fontSize.p};

    /* 스클롤러 변경 */
    overflow-y: auto;
    &::-webkit-scrollbar {
      width: 7px;
    }
    &::-webkit-scrollbar-track {
      background: transparent;
    }
    &::-webkit-scrollbar-thumb {
      background-color: #d4d4d4;
      border-radius: 10px;
    }
  }
`;

export const SSubContainerUp = styled.div`
  display: flex;
  align-items: center;
  /* 프로필 이미지 */
  .profile-img {
    height: 8vw;
    width: 8vw;
    object-fit: cover;
    text-align: center;
    border: 4px solid transparent;
    border-radius: 50%;
    object-fit: cover;
    text-align: center;
    background-image: linear-gradient(#fff, #fff),
      linear-gradient(to right, #ff6a00 0%, #ffb700 100%);
    background-origin: border-box;
    background-clip: content-box, border-box;
  }
  /* 이름 + 이메일 + 마일리지바 */
  section {
    width: 100%;
    margin-left: 15px;
    display: inline;
    .info__container {
      display: flex;
      justify-content: space-between;
    }
    /* 이름 */
    span {
      font-size: ${(props) => props.theme.fontSize.h3};
      font-weight: bold;
    }
    /* 이메일 */
    p {
      color: #9a9a9a;
      font-size: ${(props) => props.theme.fontSize.p};
      margin: 5px 0;
    }
    /* 마일리지바 */
    .wrapper {
      width: 100%;
      height: calc(1vw + 2px);
      background: rgb(238, 242, 67);
      background: linear-gradient(
        90deg,
        rgba(238, 242, 67, 1) 0%,
        rgba(198, 255, 117, 1) 50%,
        rgba(36, 232, 67, 1) 100%
      );
      border-radius: 4px;
    }
    .wrapper > div {
      flex: 1;
    }
  }
`;

export const SSnsContainer = styled.div`
  display: flex;
  column-gap: 8px;
  justify-content: space-between;
  align-items: center;
  img {
    height: calc(1.5vw + 0.1px);
    cursor: pointer;
  }
`;

export const SSubContainerDown = styled.div`
  width: 100%;
  padding: 20px 0;
  font-size: calc(0.7vw + 2px);
  line-height: calc(1vw + 5px);
  display: flex;
  flex-direction: column;
`;

export const SBlackButton = styled.button`
  width: calc(2vw + 80px);
  font-family: ${(props) => props.theme.fontFamily.Bold};
  font-size: ${(props) => props.theme.fontSize.p};
  background-color: black;
  padding: 8px 10px;
  border-radius: 8px;
  color: white;
  cursor: pointer;
  :hover {
    color: ${(props) => props.theme.deeperYellow};
    font-weight: bolder;
  }
`;

export const SCustomToolTip = styled(Tooltip)`
  background-color: yellow;
`;

export const SPointContainer = styled.div`
  padding-left: ${(props) => String((Number(props.point) / 5070) * 100) + "%"};
  width: 100%;
  display: flex;
  align-items: center;
  margin-top: 10px;
  margin-bottom: 5px;
  .point-icon {
    color: black;
    height: 1.7vw;
    -webkit-animation: action 1s infinite alternate;
    animation: action 1s infinite alternate;
  }
  .point {
    color: #9a9a9a;
    font-size: calc(0.85vw + 1px);
    margin-left: 0.5vw;
    -webkit-animation: action 1s infinite alternate;
    animation: action 1s infinite alternate;
  }

  @-webkit-keyframes action {
    0% {
      transform: translateY(3px);
    }
    100% {
      transform: translateY(-4px);
    }
  }

  @keyframes action {
    0% {
      transform: translateY(3px);
    }
    100% {
      transform: translateY(-4px);
    }
  }
`;
