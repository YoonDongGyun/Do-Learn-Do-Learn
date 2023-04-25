import styled from "styled-components";

export const Box = styled.main`
  width: 100%;
  height: 80px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-family: ${(props) => props.theme.fontFamily.Medium};
  img {
    height: calc(2vw + 17px);
  }
  img:hover {
    animation: swing 5000ms infinite;
  }

  @keyframes swing {
    0%,
    10% {
      transform: rotate(0deg);
    }
    5%,
    15%,
    25%,
    35%,
    45% {
      transform: rotate(5deg);
    }
    10%,
    20%,
    30%,
    40% {
      transform: rotate(-5deg);
    }
  }

  .left-item {
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .right-item {
    display: flex;
    justify-content: space-between;
    align-content: center;
    align-items: center;
    img {
      height: calc(1vw + 10px);
      width: calc(1vw + 10px);
      border-radius: 50%;
      object-fit: cover;
      text-align: center;
    }
    .unread__notification {
      color: black;
      cursor: pointer;
      width: calc(1vw + 5px);
      height: calc(1vw + 5px);
    }
    .unread__notification:hover {
      -webkit-animation: fa-shake 2s infinite linear;
      -moz-animation: fa-shake 2s infinite linear;
      -o-animation: fa-shake 2s infinite linear;
      animation: fa-shake 2s infinite linear;
      color: #ed6c02;
    }
    .user-state {
      background-color: black;
      padding: 8px 12px;
      border-radius: 8px;
      color: white;
      margin-left: calc(1vw + 15px);
    }
    .username {
      color: black;
      font-size: ${(props) => props.theme.fontSize.h4};
      display: flex;
    }
    .user-state {
      color: white;
      font-size: ${(props) => props.theme.fontSize.p};
    }
  }
  .link__board {
    font-size: ${(props) => props.theme.fontSize.h4};
    color: #545151;
    margin-left: calc(1vw + 6px);
  }
  .link {
    margin-top: 0px;
    text-decoration: none;
    transition: all 300ms ease;
  }
  .link:hover {
    color: #f3bd2a;
    font-weight: bold;
    transform: scale(1.1);
  }
  .logout {
    margin-bottom: 0px;
    cursor: pointer;
  }
  .MuiBadge-badge.MuiBadge-standard.MuiBadge-anchorOriginTopRight.MuiBadge-anchorOriginTopRightRectangular.MuiBadge-overlapRectangular.MuiBadge-colorWarning.MuiBadge-badge.css-x3w4s2-MuiBadge-badge {
    font-size: 0.5vw;
    height: 1.2vw;
    min-width: 1.2vw;
    border-radius: 50%;
    right: -0.3vw;
  }
  .unread-message {
    position: absolute;
    width: calc(0.3vw + 1px);
    height: calc(0.3vw + 1px);
    background-color: red;
    border-radius: 50%;
    top: 0px;
  }
  .user-state-nuLogined {
    color: white;
  }
  #division {
    margin: 0px 12px;
    cursor: default;
  }
`;
