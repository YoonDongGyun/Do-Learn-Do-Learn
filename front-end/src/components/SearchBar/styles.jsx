import styled from "styled-components";

export const SSearchContainer = styled.div`
  width: 50vw;
  margin: 0px 1vw;
  .JoyInput-root {
    border: none;
    outline: none;
    box-shadow: 5px 5px 30px #c4c4c454;
  }
  input {
    padding: calc(0.8vw + 1px);
  }

  /* input border 커스텀 */
  .css-c64t1i-JoyInput-root {
    --Input-focusedHighlight: white;
  }
`;

export const SWarning = styled.div`
  font-family: ${(props) => props.theme.fontFamily};
  font-size: ${(props) => props.theme.fontSize.h3};
  color: #ff0000;
  text-align: center;
  margin-top: 3vh;
`;
