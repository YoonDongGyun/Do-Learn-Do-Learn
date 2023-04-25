import styled from "styled-components";

export const STimer = styled.div`
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  font-family: ${(props) => props.theme.fontFamily.Bold};
  font-size: ${(props) => props.theme.fontSize.h1};
`;

export const SAlert = styled.div`
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  font-family: ${(props) => props.theme.fontFamily.Bold};
  font-size: ${(props) => props.theme.fontSize.h1};
  color: red;
`;
