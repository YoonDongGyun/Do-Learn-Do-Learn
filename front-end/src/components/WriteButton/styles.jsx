import styled from "styled-components";

export const SWriteButton = styled.button`
  border: none;
  margin: 1vh 0;
  margin-left: auto;
  background-color: black;
  color: white;
  font-family: ${(props) => props.theme.fontFamily.Medium};
  font-size: calc(0.8vw + 0.5px);
  width: 5rem;
  height: 2rem;
  border-radius: 5px;
  cursor: pointer;
  place-items: center;
  text-align: center;

  :hover {
    font-family: ${(props) => props.theme.fontFamily.Bold};
    transform: translateY(-1px);
    color: ${(props) => props.theme.deeperYellow};
    cursor: pointer;
  }
`;
