import styled from "styled-components";

export const SOptionContainer = styled.div`
  position: absolute;
  bottom: 0px;
  display: flex;
  left: 50%;
  transform: translateX(-50%);
  justify-content: center;
  align-items: center;
  background-color: #dedede;
  text-align: center;
  margin: auto;
  width: 65%;
  margin-top: calc(0.8vw + 2px);
  border-radius: 8px 8px 0px 0px;
  button {
    cursor: pointer;
    display: flex;
    justify-content: space-evenly;
    align-items: center;
    margin: calc(0.5vw + 2px);
    background-color: transparent;
    width: 10%;
    height: 10%;
    border-radius: 8px;
    padding: calc(0.8vw + 2px) calc(1vh + 12px);
    border: none;
    transition: all 300ms linear;
    outline: none;
  }
  .icon {
    transition: all 300ms linear;
  }
  button:hover {
    background-color: white;
    .icon {
      transform: scale(1.2);
      color: ${(props) => props.theme.deepYellow};
    }
  }
  .icon {
    width: calc(1vw + 12px);
    height: calc(1vh + 12px);
    color: #7e7e7e;
  }
  .exit-button:hover {
    background-color: #e53e3e;
    opacity: 1;
    .exit-icon {
      color: white;
    }
  }
`;
