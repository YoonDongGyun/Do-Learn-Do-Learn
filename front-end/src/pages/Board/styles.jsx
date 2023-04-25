import styled from "styled-components";

export const SOutterBox = styled.div`
  font-family: ${(props) => props.theme.fontFamily.Medium};
  display: flex;
  margin-top: 3vh;
`;

export const SInnerBox = styled.div`
  display: flex;
  flex-direction: column;
  width: 50vw;
  text-align: center;
  margin: 2vh 1vw;
`;
