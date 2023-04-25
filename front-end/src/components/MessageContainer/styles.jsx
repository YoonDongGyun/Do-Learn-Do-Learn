import styled from "styled-components";

// 메시지를 작성할 textarea를 감싸는 div
export const SMessageContainer = styled.div`
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  textarea {
    border-radius: 12px;
    border: none;
    outline: none;
    box-shadow: 5px 5px 5px #3737372d;
    font-family: ${(props) => props.theme.fontFamily.Regular};
    resize: none;
    font-size: calc(1vw + 2px);
    padding: calc(0.3vw + 8px);
    padding-right: calc(0.3vw + 45px);
    overflow: hidden;
    width: 19vw;
  }
  button {
    position: absolute;
    right: 0.5vw;
    bottom: 0.7vw;
    border: none;
    outline: none;
    background-color: #590f0f;
    border-radius: 50%;
    width: calc(1vw + 18px);
    height: calc(1vh + 26px);
    cursor: pointer;
  }
  .send-icon {
    color: white;
  }
`;
