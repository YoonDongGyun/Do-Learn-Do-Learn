import React from "react";
import { Snackbar, Slide, Alert } from "@mui/material";
import { useState } from "react";
import { useEffect } from "react";

function TransitionUp(props) {
  return <Slide {...props} direction="right" />;
}

const SimpleSnackbar = ({ message, setOpenSnackbar }) => {
  const [open, setOpen] = useState(true);
  const handleClose = (e, reason) => {
    if (reason === "clickaway") {
      return;
    }
    setOpen(false);
  };

  useEffect(() => {
    if (!open) {
      setTimeout(() => {
        setOpenSnackbar(false);
      }, 1000);
    }
  }, [open]);
  return (
    <>
      <Snackbar
        open={open}
        onClose={handleClose}
        autoHideDuration={3000}
        transitionDuration={1000}
        TransitionComponent={TransitionUp}
      >
        <Alert
          severity="success"
          color="warning"
          autoHideDuration={3000}
          sx={{
            fontWeight: "bold",
          }}
        >
          {message}
        </Alert>
      </Snackbar>
    </>
  );
};

export default SimpleSnackbar;
