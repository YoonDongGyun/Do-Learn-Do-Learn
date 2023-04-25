import styled from "styled-components";

export const SImg = styled.img`
  display: flex;
  margin: auto;
  margin-top: 0.5vh;
  width: 10vw;
  aspect-ratio: 3/2;
`;

// UniBoard에 스타일 입히기
export const SUniBoard = styled.div`
  transition: all 300ms ease;
  width: calc(1vw + 180px);
  cursor: pointer;
  font-family: ${(props) => props.theme.fontFamily.Medium};
  font-size: ${(props) => props.theme.fontSize.p};
  margin: 1vw;
  padding: 10px;
  border: 0.1px solid #f4e3b4;
  box-shadow: 5px 5px 5px #3737372d;
  border-radius: 5px;
  :hover {
    transform: scale(1.05);
  }

  h3,
  p {
    // 초과 텍스트 ... 처리
    word-wrap: break-word;
    white-space: nowrap;
    overflow-y: auto;
    overflow-x: hidden;
    text-overflow: ellipsis;
  }
`;
