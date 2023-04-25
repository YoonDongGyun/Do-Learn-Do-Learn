import styled from "styled-components";
import rankItemImg from "../../assets/images/rankItem.png";

export const SContainer = styled.div`
  cursor: pointer;
  position: relative;
  font-family: ${(props) => props.theme.fontFamily.Light};
  background-image: url(${rankItemImg});
  background-size: cover;
  height: 9.5vw;
  border-radius: 12px;
  color: #000000;
  box-shadow: 5px 5px 15px #c5c5c5;
  transition: all 300ms linear;

  &:hover {
    transform: translateY(-5%);
  }
  &:hover .sns__container {
    background: linear-gradient(0deg, #ffae00, #ffe07a41);
    opacity: 1;
  }
  .sns__container {
    width: 100%;
    height: 80%;
    position: absolute;
    bottom: 0;
    opacity: 0;
    z-index: -1;
    border-radius: 12px;
  }

  /* 프로필 사진과 이름, 점수를 묶는 div */
  .profile-container {
    margin: calc(0.5vw + 3px) calc(0.5vw + 5px);
    display: flex;
    align-items: center;
    /* 이름 */
    .name {
      margin: 0;
      font-size: ${(props) => props.theme.fontSize.h2};
      font-weight: bold;
    }
    /* 이메일 */
    .email {
      font-size: ${(props) => props.theme.fontSize.p};
      color: #3c3c3c;
    }
    /* 점수 */
    .point {
      margin: 0px;
      margin-top: 0.5vw;
      font-size: ${(props) => props.theme.fontSize.h4};
    }
    /* 프로필 사진 */
    img {
      width: 4.5vw;
      height: 4.5vw;
      margin-right: calc(0.5vw + 2px);
      border: 3px solid transparent;
      border-radius: 50%;
      object-fit: cover;
      text-align: center;
      background-image: linear-gradient(#fff, #fff),
        linear-gradient(to right, #ff6a00 0%, #ffb700 100%);
      background-origin: border-box;
      background-clip: content-box, border-box;
    }
  }
`;

// SNS 링크를 묶는 div
export const SSNSContainer = styled.div`
  display: flex;
  justify-content: start;
  gap: 0.67vw;
  bottom: 1vw;
  margin: 1.5vw calc(0.5vw + 5px);
  z-index: 100;
  .icon {
    font-size: calc(1vw + 4px);
    color: #000000;
    margin-bottom: 0px;
    transition: all 300ms linear;
  }
  .icon:hover {
    color: #ff7300;
  }
`;

export const SRank = styled.div`
  user-select: none;
  font-size: calc(1vw + 24px);
  font-family: "Pacifico";
  color: #ffffff;
  position: absolute;
  bottom: calc(0.8vw + 1px);
  right: calc(1vw + 12px);
  z-index: 100;
`;
