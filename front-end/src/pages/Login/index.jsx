import React, { useCallback, useState } from "react";
import logoImg from "../../assets/images/logo.png";
import Backdrop from "@mui/material/Backdrop";
import Box from "@mui/material/Box";
import Modal from "@mui/material/Modal";
import Fade from "@mui/material/Fade";
import Typography from "@mui/material/Typography";
import naverLogoImg from "../../assets/images/login/naver_logo.png";
import kakaoLogoImg from "../../assets/images/login/kakao_logo.png";
import googleLogoImg from "../../assets/images/login/google_logo.png";
import {
  SMain,
  SForm,
  SImgSection,
  SContainer,
  SInputContainer,
  SEmailFontAwesomeIcon,
  SEmailInput,
  SPasswordInput,
  SLoginButton,
  SnaverLoginButton,
  SkakaoLoginButton,
  SgoogleLoginButton,
  SMainContainer,
  SSignUpButton,
  SFindPassword,
  SCancelButton,
} from "./styles";
import { faEnvelope, faLock } from "@fortawesome/free-solid-svg-icons";
import { NavLink, useNavigate } from "react-router-dom";
import Lottie from "react-lottie";
import animationData from "../../assets/images/LOGIN";
import { useContext } from "react";
import { LoginStateHandlerContext } from "../../App";
import { loginAPI } from "../../utils/api/userAPI";
import { SOCIAL_LOGIN_URL } from "../../utils/api/URL";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  borderRadius: "8px",
  boxShadow: 24,
  outline: "none",
};

const defaultOptions = {
  loop: true,
  autoplay: true,
  animationData: animationData,
  rendererSettings: {
    preserveAspectRatio: "xMidYMid slice",
  },
};

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isCorrectPassword, setIsCorrectPassword] = useState("");
  const [isCorrectEmail, setIsCorrectEmail] = useState("");
  const [open, setOpen] = React.useState(false);
  const [isNaverLogin, setIsNaverLogin] = useState(false);
  const navigate = useNavigate();

  // context api를 통해 handleIsLogined 함수 가져오기
  const { handleIsLogined, handleUserInfo } = useContext(
    LoginStateHandlerContext
  );

  const onSubmit = (e) => {
    e.preventDefault();
    if (!open) {
      // 로그인 api 요청
      loginAPI(
        email,
        password,
        handleUserInfo,
        handleIsLogined,
        setIsCorrectEmail,
        setIsCorrectPassword,
        navigate
      );
    }
  };

  // 회원가입으로 이동시키는 함수
  const handleMoveToSignUp = () => {
    navigate("/signup");
  };

  // 모달을 여는 핸들러 함수
  const handleOpen = (e) => {
    if (!email || !password) {
      setOpen(true);
    }
  };

  // 모달을 닫는 핸들러 함수
  const handleClose = async () => {
    let timeout = null;
    await setOpen(false);
    clearTimeout(timeout);
    timeout = setTimeout(() => {
      setIsNaverLogin(false);
    }, 150);
  };

  const handleOnChangeEmail = useCallback((e) => {
    setEmail(e.target.value);
  }, []);

  const handleOnPassword = useCallback((e) => {
    if (e.target.value.length === 0) {
      setIsCorrectPassword("");
    }
    setPassword(e.target.value);
  }, []);

  const openNaverLogin = () => {
    setIsNaverLogin(true);
    setOpen(true);
  };

  return (
    <SMain>
      <SMainContainer>
        <SImgSection>
          <div className="nav__section">
            <NavLink className="Home-link" to={"/"}>
              <img src={logoImg} alt="logo_img" />
            </NavLink>
          </div>
          <h1>Welcome Back!</h1>
          <div>
            <Lottie options={defaultOptions} />
          </div>
        </SImgSection>
        <SForm onSubmit={onSubmit}>
          <SContainer>
            <h1>로그인</h1>
            <SInputContainer>
              <SEmailInput
                className={email ? "active__input" : ""}
                value={email}
                onChange={handleOnChangeEmail}
                type="text"
                placeholder="이메일을 입력해주세요"
              />
              <SEmailFontAwesomeIcon
                className={email ? "active__icon" : ""}
                icon={faEnvelope}
              />
              {<p className="warning-message">{isCorrectEmail}</p>}
            </SInputContainer>
            <SInputContainer>
              <SPasswordInput
                className={password ? "active__input" : ""}
                value={password}
                onChange={handleOnPassword}
                type="password"
                placeholder="비밀번호를 입력해주세요"
              />
              <SEmailFontAwesomeIcon
                className={password ? "active__icon" : ""}
                icon={faLock}
              />
              {isCorrectPassword && (
                <p className="warning-message password__warning">
                  {isCorrectPassword}
                </p>
              )}
            </SInputContainer>
            <SFindPassword>
              <div style={{ margin: "15px 0px" }}></div>
            </SFindPassword>
            <SLoginButton type="submit" onClick={(e) => handleOpen(e)}>
              로그인
            </SLoginButton>
            <SSignUpButton type="button" onClick={handleMoveToSignUp}>
              새로운 계정 만들기
            </SSignUpButton>
            <hr
              style={{
                width: "62%",
                height: "2px",
                border: "0px",
                backgroundColor: "#e4e4e4",
                marginTop: "20px",
                marginBottom: "30px",
              }}
            />
            <SnaverLoginButton type="button" onClick={() => openNaverLogin()}>
              <img src={naverLogoImg} alt="naver_logo" />
              네이버로 로그인
            </SnaverLoginButton>

            <SkakaoLoginButton
              type="button"
              onClick={(e) =>
                (window.location.href =
                  // 주소 수정해야 됨
                  `${SOCIAL_LOGIN_URL}/oauth2/authorization/kakao`)
              }
            >
              <img src={kakaoLogoImg} alt="kakao_logo" />
              카카오로 로그인
            </SkakaoLoginButton>

            <SgoogleLoginButton
              type="button"
              onClick={(e) =>
                (window.location.href = `${SOCIAL_LOGIN_URL}/oauth2/authorization/google`)
              }
            >
              <img src={googleLogoImg} alt="google_logo" />
              구글로 로그인
            </SgoogleLoginButton>
          </SContainer>
        </SForm>
      </SMainContainer>
      <Modal
        aria-labelledby="transition-modal-title"
        aria-describedby="transition-modal-description"
        open={open}
        onClose={handleClose}
        closeAfterTransition
        BackdropComponent={Backdrop}
        BackdropProps={{
          timeout: 500,
        }}
      >
        <Fade in={open}>
          <Box sx={style}>
            <Typography
              sx={{
                textAlign: "center",
                marginTop: "32px",
                fontFamily: "KIMM_Bold",
              }}
              id="transition-modal-title"
              variant="h6"
              component="h2"
            >
              {isNaverLogin
                ? "서비스 예정입니다."
                : email
                ? password
                  ? ""
                  : "비밀번호를 입력해주세요."
                : "이메일을 입력해주세요."}
            </Typography>
            <Typography id="transition-modal-description" sx={{ mt: 2 }}>
              <SCancelButton onClick={(e) => setOpen(false)}>
                확인
              </SCancelButton>
            </Typography>
          </Box>
        </Fade>
      </Modal>
    </SMain>
  );
};

export default Login;
