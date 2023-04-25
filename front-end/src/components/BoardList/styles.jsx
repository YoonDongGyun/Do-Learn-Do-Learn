import styled from "styled-components";

export const SContainer = styled.div`
  font-size: ${(props) => props.theme.fontSize.p};
  grid-template-columns: repeat(3, 1fr);
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  flex-wrap: wrap;
  justify-content: center;
  font-size: ${(props) => props.theme.fontSize.p};
`;

export const SUniDiv = styled.div`
  font-family: ${(props) => props.theme.fontFamily.Medium};
`;

export const SNoBoard = styled.h3`
  font-family: ${(props) => props.theme.fontFamily.Light};
`;
