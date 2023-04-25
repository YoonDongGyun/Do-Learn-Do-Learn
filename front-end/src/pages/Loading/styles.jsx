import styled from "styled-components"

export const SSection = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  & div {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  & h2 {
    font-weight: bold;
    font-size: 48px;
    color: #ffd217;
    margin-bottom: 48px;
  }
`
