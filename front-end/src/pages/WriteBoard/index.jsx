import React from "react";
import Navbar from "../../components/Navbar";
import NewBoard from "../../components/NewBoard";
import { Grid } from "@mui/material";

const WriteBoard = () => {
  return (
    <>
      <Grid container>
        {/* navbar 부분 그리드 */}
        <Grid item xs={0} md={1.5} />
        <Grid item xs={12} md={9}>
          <Navbar />
          <NewBoard />
        </Grid>
        <Grid item xs={0} md={1.5} />
      </Grid>
    </>
  );
};

export default WriteBoard;
