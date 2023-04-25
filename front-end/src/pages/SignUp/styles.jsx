import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

export const SMain = styled.main`
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
  background: rgb(255, 232, 154);
  background: linear-gradient(
    90deg,
    rgba(255, 232, 154, 1) 25%,
    rgba(255, 153, 98, 1) 100%
  );
`;

export const SMainContainer = styled.div`
  display: flex;
  justify-content: center;
  background-color: white;
  border-radius: 30px;
  img {
    height: calc(2vw + 20px);
  }
  position: relative;
`;

export const SImgSection = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: #ffd6c3;
  padding: calc(5vw + 20px) calc(5vw + 20px);
  border-radius: 0 30px 30px 0;
  h1 {
    font-family: "Pacifico", cursive;
    font-size: calc(1vw + 25px);
    margin-top: 0px;
    margin-bottom: 10px;
    user-select: none;
    text-shadow: 4px 6px 1px #f7986b;
  }
  div {
    width: calc(2vw + 400px);
    cursor: pointer;
  }
`;

export const SForm = styled.form`
  display: flex;
  flex-direction: column;
  width: calc(2vw + 450px);
  .nav__section {
    position: relative;
    top: 10px;
    left: 10px;
    z-index: 1;
    cursor: default;
    .Home-link {
      z-index: 2;
      img:hover {
        animation: swing 5000ms infinite;
      }

      @keyframes swing {
        0%,
        10% {
          transform: rotate(0deg);
        }
        5%,
        15%,
        25%,
        35%,
        45% {
          transform: rotate(5deg);
        }
        10%,
        20%,
        30%,
        40% {
          transform: rotate(-5deg);
        }
      }
    }
  }
`;

export const SContainer = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  position: relative;
  margin-top: ${(props) => (props.isNext ? "calc(1vw + -30px)" : "-50px")};
  h1 {
    font-size: ${(props) => props.theme.fontSize.h1};
    font-family: ${(props) => props.theme.fontFamily.ExtraBold};
    margin-top: 0px;
    margin-bottom: 40px;
  }
  .info-text {
    white-space: normal;
    padding: 0;
    margin-bottom: 8px;
    color: #595858;
  }
  p {
    font-size: ${(props) => props.theme.fontSize.h5};
    font-weight: bold;
    color: ${(props) => props.theme.red};
    margin: 0;
    padding-left: 10px;
  }
  .username__warning {
    margin-bottom: ${(props) => props.username && "0px"};
  }
`;

export const SInputContainer = styled.div`
  position: relative;
  margin-bottom: 15px;
  .self-introduction__img {
    top: 14px;
  }
  .active__input {
    border: 3px solid ${(props) => props.theme.deepYellow};
  }
  .active__icon {
    color: #590f0f;
  }
  .typing-length {
    text-align: right;
    color: black;
    margin-bottom: 0px;
  }
`;

export const SUsernameInput = styled.input`
  font-family: ${(props) => props.theme.fontFamily.Regular};
  width: calc(1vw + 240px);
  height: calc(1vh + 20px);
  border: 3px solid #cdcdcd;
  border-radius: 8px;
  outline: none;
  padding-left: 30px;
  font-size: ${(props) => props.theme.fontSize.p};
  &::placeholder {
    color: #cdcdcd;
  }
`;

export const SEmailInput = styled.input`
  font-family: ${(props) => props.theme.fontFamily.Regular};
  width: calc(1vw + 240px);
  height: calc(1vh + 20px);
  border: 3px solid #cdcdcd;
  border-radius: 8px;
  outline: none;
  padding-left: 30px;
  font-size: ${(props) => props.theme.fontSize.p};
  &::placeholder {
    color: #cdcdcd;
  }
`;

export const SEmailFontAwesomeIcon = styled(FontAwesomeIcon)`
  font-size: ${(props) => props.theme.fontSize.p};
  color: #cdcdcd;
  position: absolute;
  top: 10px;
  left: 10px;
`;

export const SPasswordInput = styled.input`
  font-family: ${(props) => props.theme.fontFamily.Regular};
  width: calc(1vw + 240px);
  height: calc(1vh + 20px);
  border: 3px solid #cdcdcd;
  border-radius: 8px;
  outline: none;
  padding-left: 30px;
  font-size: ${(props) => props.theme.fontSize.p};
  &::placeholder {
    color: #cdcdcd;
  }
`;

export const SPasswordCheckInput = styled.input`
  font-family: ${(props) => props.theme.fontFamily.Regular};
  width: calc(1vw + 240px);
  height: calc(1vh + 20px);
  border: 3px solid #cdcdcd;
  border-radius: 8px;
  outline: none;
  padding-left: 30px;
  font-size: ${(props) => props.theme.fontSize.p};
  &::placeholder {
    color: #cdcdcd;
  }
`;

export const SBlogInput = styled.input`
  font-family: ${(props) => props.theme.fontFamily.Regular};
  width: calc(1vw + 240px);
  height: calc(1vh + 20px);
  border: 3px solid #cdcdcd;
  border-radius: 8px;
  outline: none;
  padding-left: 30px;
  font-size: ${(props) => props.theme.fontSize.p};
  &::placeholder {
    color: #cdcdcd;
  }
`;

export const SYouTubeInput = styled.input`
  font-family: ${(props) => props.theme.fontFamily.Regular};
  width: calc(1vw + 240px);
  height: calc(1vh + 20px);
  border: 3px solid #cdcdcd;
  border-radius: 8px;
  outline: none;
  padding-left: 30px;
  font-size: ${(props) => props.theme.fontSize.p};
  &::placeholder {
    color: #cdcdcd;
  }
`;

export const SInstagramInput = styled.input`
  font-family: ${(props) => props.theme.fontFamily.Regular};
  width: calc(1vw + 240px);
  height: calc(1vh + 20px);
  border: 3px solid #cdcdcd;
  border-radius: 8px;
  outline: none;
  padding-left: 30px;
  font-size: ${(props) => props.theme.fontSize.p};
  &::placeholder {
    color: #cdcdcd;
  }
`;

export const SFacebookInput = styled.input`
  font-family: ${(props) => props.theme.fontFamily.Regular};
  width: calc(1vw + 240px);
  height: calc(1vh + 20px);
  border: 3px solid #cdcdcd;
  border-radius: 8px;
  outline: none;
  padding-left: 30px;
  font-size: ${(props) => props.theme.fontSize.p};
  &::placeholder {
    color: #cdcdcd;
  }
`;

export const SSelfIntroduction = styled.textarea`
  font-family: ${(props) => props.theme.fontFamily.Regular};
  width: calc(1vw + 240px);
  height: 100px;
  border: 3px solid #cdcdcd;
  border-radius: 8px;
  outline: none;
  padding-left: 30px;
  padding-top: 10px;
  padding-bottom: 10px;
  font-size: ${(props) => props.theme.fontSize.p};
  resize: none;
  &::placeholder {
    color: #cdcdcd;
  }

  /* 스크롤바 */
  /* Chrome, Edge, and Safari */
  ::-webkit-scrollbar {
    width: 12px;
  }
  ::-webkit-scrollbar-track {
    background: #ffffff;
  }
  ::-webkit-scrollbar-thumb {
    background-color: #c2c2c2;
    border-radius: 10px;
    border: 3px solid #ffffff;
  }
`;

export const SSNSContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
  margin-top: 20px;
  div {
    margin: 10px 60px;
  }
`;

export const CustomButton = styled.button`
  font-family: ${(props) => props.theme.fontFamily.Regular};
  font-size: ${(props) => props.theme.fontSize.p};
  width: calc(1vw + 280px);
  height: calc(1vh + 30px);
  border-radius: 10px;
  border-color: transparent;
  cursor: pointer;
`;

export const SNextButton = styled(CustomButton)`
  background-color: black;
  color: white;
  cursor: pointer;
  margin-top: 25px;
  :hover {
    color: ${(props) => props.theme.deeperYellow};
    font-weight: bold;
  }
`;

export const SSubmitButton = styled(CustomButton)`
  background-color: black;
  color: white;
  cursor: pointer;
  margin-top: 25px;
  :hover {
    color: ${(props) => props.theme.deeperYellow};
    font-weight: bold;
  }
`;

export const SBackToLoginButton = styled(CustomButton)`
  background-color: white;
  border-color: black;
  color: black;
  margin-top: 12px;
  :hover {
    color: ${(props) => props.theme.deeperYellow};
    font-weight: bold;
  }
`;

export const SCancelButton = styled.button`
  margin-top: 40px;
  margin-bottom: 20px;
  color: #f3bd2a;
  cursor: pointer;
  width: 100%;
  height: 100%;
  background-color: white;
  border: none;
  outline: none;
  font-family: ${(props) => props.theme.fontFamily.Regular};
  font-size: ${(props) => props.theme.fontSize.h3};
  font-weight: bold;
`;
