import styled from "styled-components";

export const SContainer = styled.section`
  height: 100%;
  width: 24vw;
`;

// 유저 프로필 사진을 감싸는 div
export const SUsersContainer = styled.div`
  position: absolute;
  bottom: 16px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  justify-content: space-evenly;
  span {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 0px calc(0.5vw + 1px);
  }
  /* 강사 프로필 */
  .lecturer {
    border: 3px solid white;
  }
  img {
    width: calc(1vw + 24px);
    height: calc(1vw + 24px);
    border-radius: 50%;
  }
`;

export const SChattingContainer = styled.div`
  position: relative;
  height: 100%;
  margin: 0px;
  background-color: #dedede;
  border-radius: 12px;
  box-shadow: 5px 5px 5px #3737372d;
  overflow: auto;

  ::-webkit-scrollbar {
    width: 12px;
  }
  ::-webkit-scrollbar-track {
    margin-bottom: 4vw;
    background: transparent;
  }
  ::-webkit-scrollbar-thumb {
    background-color: white;
    border-radius: 12px;
    border: 1px solid #f0f0f0;
  }
`;

export const SChattingContent = styled.div`
  text-align: left;
  padding: calc(0.5vw + 5px);
  margin-bottom: 3vw;
  /* 채팅 작성자 표시 */
  p {
    margin: 0px;
    font-size: calc(0.5vw + 1px);
  }
  /* 채팅내용이 들어갈 div */
  .content {
    background-color: white;
    display: inline-block;
    padding: calc(0.5vw + 4px);
    border-radius: 8px;
    margin-top: calc(0.3vw + 1px);
    margin-bottom: calc(0.5vw + 1px);
    font-size: calc(0.5vw + 5px);
    max-width: calc(1vw + 150px);
    word-wrap: break-word;
    white-space: -moz-pre-wrap;
    white-space: pre-wrap;
  }
  .entrance {
    color: #ed6c02;
    margin: calc(0.5vw + 1px) 0px;
  }
  .content-container {
    margin-top: calc(0.5vw + 1px);
  }
`;

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
