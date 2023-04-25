import styled from "styled-components";

export const SSidebarContainer = styled.section`
  margin-top: calc(1vw + 0.5px);
  position: fixed;
  width: 14vw;
  .sidebar__container {
    position: relative;
    height: 55vh;
    border-radius: 15px;
    padding-top: 2vw;
    background-color: #ff9d00;
  }
`;

export const SButtonContainer = styled.div`
  position: relative;
  .tab__section {
    width: 80%;
    border-radius: 10px;
    margin-bottom: 30px;
    margin-left: auto;
    margin-right: auto;
    padding: 10px;
    font-family: ${(props) => props.theme.fontFamily.Bold};
    font-size: ${(props) => props.theme.fontSize.h3};
  }
  .tab__section:hover {
    background-color: #fff0e0;
    cursor: pointer;
  }
  .active {
    background-color: #fff0e0;
    color: black;
    font-weight: bold;
  }
  .tab-content {
    background-color: transparent;
    color: white;
    position: relative;
    z-index: 2;
    text-align: left;
    border: none;
    outline: none;
    cursor: pointer;
  }
  .tab-content:hover {
    cursor: pointer;
    color: black;
    font-weight: bold;
  }
`;

export const SUserDeleteButtonContainer = styled.div`
  position: absolute;
  display: flex;
  align-items: center;
  left: 10px;
  bottom: 10px;

  button {
    color: rgba(255, 255, 255, 0.849);
    text-align: left;
    font-family: ${(props) => props.theme.fontFamily};
    background-color: transparent;
    border: none;
    outline: none;
    cursor: pointer;
  }
`;
