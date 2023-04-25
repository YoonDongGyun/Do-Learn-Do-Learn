import styled from "styled-components";

export const SContent = styled.div`
  font-family: ${(props) => props.theme.fontFamily.Medium};
  width: 100%;
  .upper__container {
    width: 100%;
    span {
      font-size: ${(props) => props.theme.fontSize.h3};
      font-weight: bold;
    }
    p {
      font-size: ${(props) => props.theme.fontSize.p};
      color: #7b7b7b;
      margin-top: 0;
      margin-bottom: 23px;
      display: block;
      border-bottom: 2px solid #cbcbcb;
    }
  }
  .down__container {
    width: 100%;
    font-size: ${(props) => props.theme.fontSize.h4};
    display: flex;
    flex-direction: column;
    .lecture-info {
      display: flex;
      .custom-content {
        width: 100%;
        display: block;
        background-color: ${(props) => props.theme.lightYellow};
        margin-left: 10px;
        padding: 10px;
        border-radius: 10px;
      }
    }
    .additional-text {
      color: #ff8282;
      font-size: ${(props) => props.theme.fontSize.p};
    }
  }
`;

export const SButton = styled.div`
  margin-top: 20px;
  width: 100%;
  display: flex;
  justify-content: space-evenly;
  button {
    font-size: ${(props) => props.theme.fontSize.p};
    background-color: black;
    color: white;
    width: calc(1vw + 100px);
    height: calc(1vh + 30px);
    border: none;
    outline: none;
    border-radius: 4px;
    cursor: pointer;
  }
`;
