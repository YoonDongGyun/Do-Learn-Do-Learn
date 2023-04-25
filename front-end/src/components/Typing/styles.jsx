import styled from "styled-components";

export const SSection = styled.section`
  user-select: none;
  width: 100%;
  height: calc(1vh + 150px);
  display: flex;
  justify-content: center;
  align-items: center;
  span {
    font-family: ${(props) => props.theme.fontFamily.ExtraBold};
    font-weight: 700;
    font-size: calc(1vw + 28px);
    background: linear-gradient(to left top, #ff4f23, #ffaa00, #f5ca4a);
    color: transparent;
    -webkit-background-clip: text;
  }
  span::after {
    content: "|";
    display: inline-block;
    font-size: calc(1vw + 32px);
    animation: moveCursor 500ms infinite;
    color: #ffaa00;
  }

  @keyframes moveCursor {
    from {
      opacity: 1;
    }

    to {
      opacity: 0;
    }
  }
`;
