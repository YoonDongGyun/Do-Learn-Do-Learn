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
`;

export const SButtonContainer = styled.div`
  margin-top: 50px;
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
  }
  .delete-button {
    background-color: #ff7979;
  }
  .cancel-button {
    background-color: #cbcbcb;
  }
`;
