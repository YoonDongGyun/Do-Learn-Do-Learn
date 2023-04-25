import styled from "styled-components";

export const SContainer = styled.div`
  cursor: pointer;
  .totalSchedule__calendar {
    margin-right: calc(0.3vw + 1px);
  }
  border-radius: 8px;
  padding: calc(0.8vw + 1px) calc(0.8vw + 2px);
  padding-right: calc(0.8vw + 1px);
  background-color: #fffaec;
  font-size: calc(0.7vw + 1px);
  margin-bottom: calc(0.7vw + 1px);
  p {
    margin-top: 0px;
  }
  div {
    display: flex;
    justify-content: space-between;
    align-items: center;
    p {
      font-size: calc(0.7vw + 1px);
      margin-bottom: 0px;
    }
    button {
      cursor: pointer;
      font-size: calc(0.5vw + 1px);
      font-weight: bold;
      border: none;
      border-radius: 20px;
      background-color: #f3bd2a;
      padding: calc(0.3vw + 1px) calc(0.3vw + 8px);
    }
  }
`;
