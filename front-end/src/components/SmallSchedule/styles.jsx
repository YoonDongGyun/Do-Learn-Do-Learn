import styled from "styled-components";

export const SContainer = styled.div`
  font-family: ${(props) => props.theme.fontFamily.Medium};
  min-width: calc(2vw + 260px);
  height: ${(props) =>
    props.isEmpty
      ? "calc(1vw + 90px)"
      : props.isIncreaseTotalScheduleHight && props.isIncreaseTodayScheduleHight
      ? "calc(1vw + 450px)"
      : "calc(1vw + 340px)"};
  background-color: white;
  padding: calc(1vw + 1px) calc(1vw + 4px);
  border-radius: 20px;
  border: 4px solid #f4e3b4;
  display: flex;
  flex-direction: column;
  margin-right: calc(0.7vw + 1px);
  .header {
    font-size: calc(0.85vw + 1px);
    display: flex;
    justify-content: space-between;
    p {
      margin-top: 0px;
    }
  }
  .todaySchedule {
    height: calc(1vw + 180px);
  }
  .boundary {
    text-align: center;
    margin: calc(0.8vw + 2px) 0px;
    height: calc(0.1vw + 0.3px);
    width: 100%;
    background-color: black;
  }

  .totalSchedule__header {
    font-size: calc(0.85vw + 1px);
    margin-top: 0px;
  }

  .totalSchedule {
    height: calc(1vw + 180px);
  }
  .Scrollbars {
    height: calc(1vw + 100px);
  }
  .empty {
    height: auto;
  }
`;

export const SEmptyNotice = styled.div`
  background-color: ${(props) => props.theme.lighterYellow};
  text-align: center;
  padding: 20px;
  border-radius: 12px;
`;
