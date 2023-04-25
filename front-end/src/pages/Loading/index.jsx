import React from "react";
import { SyncLoader } from "react-spinners";
import { SSection } from "./styles";

const Loading = () => {
  return (
    <SSection>
      <div>
        <h2>LOADING</h2>
        <SyncLoader color="#FFD217" margin={16} size={48} speedMultiplier={1} />
      </div>
    </SSection>
  );
};

export default Loading;
