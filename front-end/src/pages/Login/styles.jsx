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

export const SLeftSection = styled.section`
  display: flex;
  direction: column;
  background-color: black !important;
`;

export const SImgSection = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: calc(5vw + 20px) calc(5vw + 20px);
  background-color: #fffbe6;
  border-radius: 30px 0 0 30px;
  .nav__section {
    position: absolute;
    top: 10px;
    left: 10px;
    cursor: default;
    .Home-link {
      z-index: 1;
    }
  }
  h1 {
    font-family: "Pacifico", cursive;
    font-size: calc(1vw + 25px);
    margin-bottom: 10px;
    margin-top: 0px;
    user-select: none;
    text-shadow: 4px 6px 1px ${(props) => props.theme.deeperYellow};
  }
  div {
    width: calc(2vw + 400px);
    cursor: pointer;
  }
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
`;

export const SForm = styled.form`
  display: flex;
  flex-direction: column;
  width: calc(2vw + 450px);
  .warning-message {
    font-size: ${(props) => props.theme.fontSize.h5};
    font-weight: bold;
    color: ${(props) => props.theme.red};
    margin: 0;
    padding-left: 10px;
    margin-bottom: 20px;
  }
  .password__warning {
    margin-bottom: 0px;
  }
`;

export const SContainer = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  h1 {
    font-size: ${(props) => props.theme.fontSize.h1};
    font-family: ${(props) => props.theme.fontFamily.ExtraBold};
    margin-top: 0px;
    margin-bottom: 40px;
  }
`;

export const SFindPassword = styled.div`
  width: calc(1vw + 260px);
  div {
    display: flex;
    justify-content: end;
    margin: 15px 0px;
    font-size: ${(props) => props.theme.fontSize.p};
    cursor: pointer;
  }
  div:hover {
    color: ${(props) => props.theme.deeperYellow};
  }
`;

export const SInputContainer = styled.div`
  position: relative;
  .active__input {
    border: 3px solid ${(props) => props.theme.deepYellow};
  }
  .active__icon {
    color: #590f0f;
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
  width: calc(1vw + 240px);
  height: calc(1vh + 20px);
  border: 3px solid #cdcdcd;
  border-radius: 8px;
  outline: none;
  padding-left: 30px;
  font-size: ${(props) => props.theme.fontSize.p};
  &::placeholder {
    font-family: ${(props) => props.theme.fontFamily.Regular};
    color: #cdcdcd;
  }
`;

export const SSNSContainer = styled.div`
  width: calc(1vw + 240px);
  display: flex;
  justify-content: center;
  margin-top: 20px;
  div {
    margin: 0px 20px;
  }
`;

export const CustomButton = styled.button`
  margin: 12px;
  margin-top: 0px;
  font-family: ${(props) => props.theme.fontFamily.Regular};
  font-size: ${(props) => props.theme.fontSize.p};
  width: calc(1vw + 280px);
  height: calc(1vh + 30px);
  border-radius: 10px;
  border-color: transparent;
  cursor: pointer;
`;

export const SLoginButton = styled(CustomButton)`
  background-color: black;
  color: white;
  :hover {
    color: ${(props) => props.theme.deeperYellow};
    font-weight: bold;
  }
`;

export const SnaverLoginButton = styled(CustomButton)`
  position: relative;
  background-color: #01c300 !important;

  img {
    position: absolute;
    top: 2px;
    left: 3px;
    height: 30px;
  }
`;
export const SkakaoLoginButton = styled(CustomButton)`
  position: relative;
  background-color: #f7e112 !important;

  img {
    position: absolute;
    top: 8px;
    left: 8px;
    height: 20px;
  }
`;
export const SgoogleLoginButton = styled(CustomButton)`
  position: relative;
  background-color: #ececec !important;
  color: black;

  img {
    position: absolute;
    top: 5px;
    left: 5px;
    height: 25px;
  }
`;

export const SSignUpButton = styled(CustomButton)`
  background-color: white;
  border-color: black;
  color: black;
  :hover {
    color: ${(props) => props.theme.deeperYellow};
    font-weight: bold;
  }
`;

export const SNaverContainer = styled.div`
  button {
    background-color: #23c03c;
    cursor: pointer;
    border: none;
    color: white;
    font-size: ${(props) => props.theme.fontSize.h2};
    font-family: ${(props) => props.theme.fontFamily.SemiBold};
    width: calc(1vw + 28px);
    height: calc(1vh + 32px);
    border-radius: 4px;
  }
`;

export const SKakaoContainer = styled.div`
  button {
    background-color: #f5d60b;
    cursor: pointer;
    border: none;
    border-radius: 4px;
    color: white;
    font-size: ${(props) => props.theme.fontSize.h2};
    font-family: ${(props) => props.theme.fontFamily.SemiBold};
    width: calc(1vw + 28px);
    height: calc(1vh + 32px);
  }
`;

export const SGoogleContainer = styled.div`
  button {
    background-color: #d9d9d9;
    cursor: pointer;
    border: none;
    border-radius: 4px;
    color: white;
    font-size: ${(props) => props.theme.fontSize.h2};
    font-family: ${(props) => props.theme.fontFamily.SemiBold};
    width: calc(1vw + 28px);
    height: calc(1vh + 32px);
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
`;
