import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import styled from "styled-components";

export const SSpan = styled.span`
  font-family: ${(props) => props.theme.fontFamily.Medium};
  font-size: ${(props) => props.theme.fontSize.h1};
  color: #ff7979;
  display: block;
  border-bottom: 2px solid #cbcbcb;
`;

export const SScontent = styled.div`
  font-size: ${(props) => props.theme.fontSize.h4};
  font-weight: bold;
  text-align: center;
`;

export const SCustomFontAwesomeIcon = styled(FontAwesomeIcon)`
  height: 4vw;
  width: 4vw;
  cursor: pointer;
  &.active {
    color: ${(props) => props.theme.deeperYellow};
  }
  :hover {
    color: ${(props) => props.theme.deeperYellow};
  }
`;

export const SContentContainer = styled.div`
  width: 70%;
  margin: 20px auto;
  display: flex;
  justify-content: space-between;
`;

export const SIconContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  .evaluation_value {
    font-size: ${(props) => props.theme.fontSize.p};
    margin: 10px 0;
    font-weight: bold;
  }
`;

export const SButtonContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-evenly;
  button {
    font-size: ${(props) => props.theme.fontSize.p};
    color: white;
    width: calc(1vw + 100px);
    height: calc(1vh + 30px);
    border: none;
    outline: none;
    border-radius: 4px;
    cursor: pointer;
    background-color: #ff7979;
  }
`;
