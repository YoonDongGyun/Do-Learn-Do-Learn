import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import styled from "styled-components";

export const SContent = styled.div`
  display: flex;
  flex-direction: column;
  font-family: ${(props) => props.theme.fontFamily.Medium};
  .instructor__section {
    display: flex;
    align-items: center;
    gap: 20px;
    margin-bottom: 1vw;
    border-radius: 30px;
    background-color: ${(props) => props.theme.lightYellow};
    padding: 0.7vw 0;
    cursor: pointer;
  }
  .instructor__section:hover {
    transform: scale(1.03);
    transition: all 0.2s ease-in-out;
  }
  .profile-img {
    width: 4.5vw;
    height: 4.5vw;
    margin-left: 1vw;
    border: 3px solid transparent;
    border-radius: 50%;
    background-image: linear-gradient(#fff, #fff),
      linear-gradient(to right, #ff6a00 0%, #ffb700 100%);
    background-origin: border-box;
    background-clip: content-box, border-box;
  }
  .instructor-name {
    font-size: ${(props) => props.theme.fontSize.h3};
    font-weight: bold;
  }
  .instructor-email {
    font-size: ${(props) => props.theme.fontSize.p};
    color: #9a9a9a;
    margin: 5px 0;
  }
`;

export const SCustomFontAwesomeIcon = styled(FontAwesomeIcon)`
  width: 2vw;
`;

export const SSpan = styled.span`
  font-family: ${(props) => props.theme.fontFamily.Medium};
  font-size: ${(props) => props.theme.fontSize.p};
`;

export const SInfoItem = styled.div`
  display: flex;
  column-gap: 5px;
  align-items: center;
  margin-bottom: 1vw;
`;

export const SDetail = styled.div`
  font-size: ${(props) => props.theme.fontSize.p};
  line-height: 1.2vw;
  background-color: #f0f0f0;
  color: black;
  padding: 1vw 1vw 0.2vw 1vw;
  border-radius: 5px;
  height: 120px;
  word-wrap: break-word;
  white-space: -moz-pre-wrap;
  white-space: pre-wrap;
  overflow-y: auto;

  ::-webkit-scrollbar {
    width: 10px;
  }
  ::-webkit-scrollbar-track {
    background: transparent;
  }
  ::-webkit-scrollbar-thumb {
    background-color: #c2c2c2;
    border-radius: 12px;
    border: 3px solid #f0f0f0;
  }
`;
