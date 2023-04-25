import styled from "styled-components";

export const SMainContainer = styled.main`
  display: flex;
  justify-content: center;
  width: 100%;
  height: 35.5rem;
  background-color: white;
  .participant {
    margin-bottom: 1vw;
    text-align: center;
    position: relative;
  }
  #participants {
    display: flex;
    flex-direction: column;
    height: 34vw;
    overflow-y: auto;
    overflow-x: hidden;
    ::-webkit-scrollbar {
      width: 0.8vw;
    }
    ::-webkit-scrollbar-track {
      background: transparent;
    }
    ::-webkit-scrollbar-thumb {
      background-color: white;
      border-radius: 12px;
      border: 1px solid white;
    }
    box-sizing: content-box;
  }
  video {
    border-radius: 12px;
    border: 5px solid transparent;
    cursor: pointer;
  }
  .username_span {
    user-select: none;
    font-size: 8rem;
    cursor: pointer;
    position: absolute;
    display: flex;
    flex-direction: column;
    justify-content: center;
    top: 0;
    left: 0;
    width: 100%;
    height: 98%;
    background-color: black;
    color: white;
    opacity: 0;
    transition: all 300ms ease;
    border-radius: 12px;
    border: 5px solid transparent;
  }
  .participant:hover .username_span {
    opacity: 0.8;
  }
  .help {
    animation: help 500ms infinite;
  }
  @keyframes help {
    0% {
      border: 5px solid transparent;
    }

    50% {
      border: 5px solid #ff7979;
    }

    100% {
      border: 5px solid transparent;
    }
  }

  #container {
    width: 13.5rem;
  }
  #lectuerer {
  }
  .main {
    width: 100%;
  }
  .main + span {
    cursor: default;
  }
  .sub {
    width: 11rem;
  }
  .sub + span {
    font-size: 2rem;
    width: 12rem;
    height: 8.3rem;
  }
  .mainScreen {
    width: 45rem;
    height: 30rem;
  }
  .mainScreen + span {
    cursor: default;
    font-size: 5rem;
  }
  .subScreen {
    width: 11rem;
  }
  .subScreen + span {
    font-size: 1.5rem;
    width: 12rem;
  }
`;

export const SHeader = styled.div`
  position: relative;
  display: flex;
  align-items: center;
  .logo {
    width: 8vw;
    margin: 1vw;
  }
  .timer {
    position: absolute;
    left: 50%;
  }
`;

export const SLeftItemContainer = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

export const SRightItemContainer = styled.div`
  position: relative;
  margin-left: 2vw;
  height: 36.8vw;
`;

export const SContainer = styled.section`
  box-sizing: content-box;
  border-radius: 12px;
  background-color: #dedede;
  height: 100%;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0 0.3vw;
  padding-top: 1vw;
`;

export const SStudentsContainer = styled.div`
  margin-left: 1vw;
`;

export const SLecturerCameraContainer = styled.div`
  width: 45rem;
`;

export const SLecturerCamera = styled.div`
  width: 100%;
  border-radius: 12px;
`;

export const SOptionContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #fffbe6;
  text-align: center;
  margin: auto;
  width: 65%;
  margin-top: calc(0.8vw + 2px);
  border-radius: 8px;
  button {
    cursor: pointer;
    display: flex;
    justify-content: space-evenly;
    align-items: center;
    margin: calc(0.5vw + 2px);
    background-color: transparent;
    width: 10%;
    height: 10%;
    border-radius: 8px;
    padding: calc(0.8vw + 2px) calc(1vh + 12px);
    border: none;
    outline: none;
  }
  button:hover {
    background-color: #ffc07d;
    .icon {
      color: white;
    }
  }
  .icon {
    width: calc(1vw + 12px);
    height: calc(1vh + 12px);
    color: #7e7e7e;
  }
  .exit-button:hover {
    background-color: #e53e3e;
    opacity: 1;
    .exit-icon {
      color: white;
    }
  }
`;
