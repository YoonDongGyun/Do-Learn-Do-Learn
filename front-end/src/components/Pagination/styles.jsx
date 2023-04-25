import styled from "styled-components";

export const SNav = styled.nav`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 4px;
`;

export const SButton = styled.button`
  border: none;
  border-radius: 5px;
  padding: 0.7vh 0.5vw;
  margin: 0.1rem;
  background: black;
  color: white;
  font-size: 1rem;

  &:hover {
    background: ${(props) => props.theme.deeperYellow};
    cursor: pointer;
    border: none;
    transform: translateY(-2px);
  }

  &[disabled] {
    background: grey;
    cursor: revert;
    transform: revert;
  }

  &[aria-current] {
    // 선택된 버튼 표시
    background: ${(props) => props.theme.deeperYellow};
    font-weight: bold;
    cursor: revert;
    transform: revert;
    border: none;
  }
`;
