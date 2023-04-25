import styled from "styled-components";

export const SContainer = styled.div`
  .walkingChick_container {
    position: absolute;
    bottom: 0;
    left: 0;
    background-color: white;
    height: 2vw;
    width: 100%;
  }
  .walkingChick {
    position: absolute;
    left: 0;
    bottom: 0;
    width: 10vw;
    animation: transX 8000ms infinite linear;
    transform: translateX(-10vw);
  }
  @keyframes transX {
    from {
      transform: translateX(-10vw);
    }
    to {
      transform: translateX(100vw);
    }
  }
`;
