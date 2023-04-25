import styled from "styled-components";

export const SContainer = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  & img {
    width: 377px;
    height: 268px;
  }
  & h1 {
    font-size: 22px;
    margin: 34px;
    color: #02a0e7;
  }
  & p {
    text-align: center;
    font-size: 15px;
  }
`;
