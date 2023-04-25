import styled from "styled-components";

export const SCalendar = styled.div`
  /* 현재 날짜 셀 */
  .fc-day-today {
    background: white !important;

    /* 현재 날짜 셀의 우측상단 날짜 */
    .fc-daygrid-day-number {
      background-color: #ff9898;
      color: white;
      border-radius: 50%;
    }
    .fc-col-header-cell-cushion {
      background-color: #ffb3b3;
      border-radius: 20px;
      color: white;
    }
  }

  /* 달력 타이틀 */
  .fc-toolbar-title {
    font-family: ${(props) => props.theme.fontFamily.ExtraBold};
    font-size: ${(props) => props.theme.fontSize.h1} !important;
  }

  /* 달력 타이틀 옆 버튼 */
  .fc-button {
    font-family: ${(props) => props.theme.fontFamily.Medium};
    font-size: ${(props) => props.theme.fontSize.p} !important;
  }
  /* 버튼 활성일 때 */
  .fc .fc-button-primary:not(:disabled).fc-button-active,
  .fc .fc-button-primary:not(:disabled):active {
    background-color: ${(props) => props.theme.deeperYellow};
    border-color: ${(props) => props.theme.deeperYellow};
    color: white;
  }
  /* 비활성일 때 */
  .fc .fc-button-primary {
    background-color: #ffffff;
    border-color: ${(props) => props.theme.deeperYellow};
    /* border-color: var(--fc-button-active-border-color); */
    color: ${(props) => props.theme.deeperYellow};
  }
  .fc .fc-prev-button {
    background-color: #ffffff;
    border-color: ${(props) => props.theme.deeperYellow};
    color: ${(props) => props.theme.deeperYellow};
  }
  .fc .fc-next-button {
    background-color: #ffffff;
    border-color: ${(props) => props.theme.deeperYellow};
    color: ${(props) => props.theme.deeperYellow};
  }
  .fc .fc-today-button {
    background-color: #ffebd2;
    border-color: #ffebd2;
    color: #f88d00;
  }
  .fc .fc-button-primary:disabled {
    background-color: #fd9e23;
    border-color: #fd9e23;
    color: #ffebd2;
  }

  /* 일자 */
  .fc-daygrid-day-number {
    font-size: ${(props) => props.theme.fontSize.p};
  }

  /* 요일 */
  .fc-col-header {
    /* background-color: #e8e8e8; */
    font-size: ${(props) => props.theme.fontSize.h4};
  }

  /* 일정 셀 */
  .fc-daygrid-day-frame {
    overflow-y: auto;
    overflow-x: hidden;
    max-height: 50px;

    /* 스크롤바 */
    /* Chrome, Edge, and Safari */
    ::-webkit-scrollbar {
      width: 10px;
    }
    ::-webkit-scrollbar-track {
      background: #ffffff;
    }
    ::-webkit-scrollbar-thumb {
      background-color: #c2c2c2;
      border-radius: 10px;
      border: 3px solid #ffffff;
    }

    /* 이벤트 콘텐츠 */
    .fc-event-title-container {
      border: none;
    }
    .fc-event {
      font-family: ${(props) => props.theme.fontFamily.Light};
      font-size: ${(props) => props.theme.fontSize.p};
      cursor: pointer;
    }
    /* 이벤트 배경 설정 */
    .fc-daygrid-event-harness {
      background-color: #000000;
      border-radius: 8px;
      margin-bottom: 1px;
      padding: 1px;
    }
    .fc-daygrid-event-dot {
      display: none;
    }
    /* 일자에 커서 올리면 포인트로 만들기 */
    .fc-event-title {
      cursor: pointer;
      white-space: nowrap;
    }
    .fc-event-time {
      color: green;
      font-weight: bold;
    }
    .fc-event-title {
      color: black;
      font-weight: 500;
    }
  }

  .fc-timeGridWeek-view {
    .fc-scrollgrid-section-body:first-child {
      display: none;
    }
    .fc-timegrid-divider {
      display: none;
    }
    .fc-scroller {
      overflow: hidden !important;
    }
    .fc-scroller-liquid-absolute {
      overflow: auto !important;

      /* 스크롤바 */
      /* Chrome, Edge, and Safari */
      ::-webkit-scrollbar {
        width: 12px;
      }
      ::-webkit-scrollbar-track {
        background: #ffffff;
      }
      ::-webkit-scrollbar-thumb {
        background-color: #c2c2c2;
        border-radius: 10px;
        border: 3px solid #ffffff;
      }
    }
  }

  .fc-timeGridDay-view {
    .fc-scrollgrid-section-body:first-child {
      display: none;
    }
    .fc-timegrid-divider {
      display: none;
    }
    .fc-scroller {
      overflow: hidden !important;
    }
    .fc-scroller-liquid-absolute {
      overflow: auto !important;

      /* 스크롤바 */
      /* Chrome, Edge, and Safari */
      ::-webkit-scrollbar {
        width: 12px;
      }
      ::-webkit-scrollbar-track {
        background: #ffffff;
      }
      ::-webkit-scrollbar-thumb {
        background-color: #c2c2c2;
        border-radius: 10px;
        border: 3px solid #ffffff;
      }
    }
  }
`;
