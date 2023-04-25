import styled from "styled-components";

import { SCard } from "../CardBox/styles";

export const Scontainer = styled.div`
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  column-gap: 15px;
  row-gap: 22px;
  margin-top: 15px;
`;
export const SSCard = styled(SCard)`
  position: relative;
  height: 90vh;
  .title__section {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .pagination__section {
    position: absolute;
    bottom: 30px;
    left: 50%;
    transform: translateX(-50%);
  }
`;
