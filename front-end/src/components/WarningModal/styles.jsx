import styled from "styled-components";

export const SSection = styled.section`
  font-family: ${(props) => props.theme.fontFamily.Medium};

  .lecture__cancel-button {
    border: none;
    background-color: black;
    margin: 0 1rem;
    color: white;
    font-family: ${(props) => props.theme.fontFamily.Medium};
    font-size: calc(0.8vw + 0.5px);
    width: 5rem;
    height: 2rem;
    border-radius: 5px;
    cursor: pointer;
    place-items: center;
    text-align: center;
  }
  .lecture__cancel-button:hover {
    font-family: ${(props) => props.theme.fontFamily.Bold};
    transform: translateY(-1px);
    color: ${(props) => props.theme.deeperYellow};
    background-color: black;
  }
`;

export const SSpan = styled.span`
  font-size: ${(props) => props.theme.fontSize.h1};
  font-weight: bold;
  color: #ff7979;
  display: block;
  border-bottom: 2px solid #cbcbcb;
`;

export const SUl = styled.ul`
  margin-top: ${(props) => props.lectureCancel && "0px"};
  padding-left: 30px;
  font-size: calc(0.5vw + 8px);
  .delete-warning {
    color: #ff0000;
  }
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
    background-color: black;
  }
`;
