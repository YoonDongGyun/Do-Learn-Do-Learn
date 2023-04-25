import React from "react";
import { SCard } from "./styles";

const CardBox = (props) => {
  return <SCard>{props.children}</SCard>;
};

export default CardBox;
