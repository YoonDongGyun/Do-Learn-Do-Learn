import styled from "styled-components";

export const SSmallScheduleMain = styled.main`
  .smallSchedule-container {
    position: absolute;
    right: 20px;
    top: 20px;
    display: flex;
    .smallSchedule__toggle-button {
      width: calc(1vw + 50px);
      height: calc(1vw + 50px);
      border-radius: 50%;
      border: none;
      background-color: white;
      transition: all 300ms linear;
      font-size: ${(props) => props.theme.fontSize.h1};
      cursor: pointer;
      box-shadow: 4px 4px 20px rgba(248, 166, 51, 0.766);
      transition: all 1s ease-in;
    }
    .smallSchedule__toggle-button:hover {
      transform: scale(1.1);
    }

    .mount {
      animation: loadEffect 1s ease-in-out 0s 1 normal forwards;
    }
    .unmount {
      animation: closeEffect 1.2s ease-in-out 0s 1 normal forwards;
    }

    @keyframes loadEffect {
      from {
        transform: translateX(200%);
      }
      to {
        transform: translateX(0%);
      }
    }
    @keyframes closeEffect {
      from {
        transform: translateX(0%);
      }
      to {
        transform: translateX(120%);
      }
    }
  }
`;
