import React, { useCallback, useState } from "react";
import logoImg from "../../assets/images/logo.png";
import { Link, NavLink, useNavigate } from "react-router-dom";
import Backdrop from "@mui/material/Backdrop";
import Box from "@mui/material/Box";
import Modal from "@mui/material/Modal";
import Fade from "@mui/material/Fade";
import Typography from "@mui/material/Typography";
import {
  faEnvelope,
  faLock,
  faUser,
  faUnlock,
  faLink,
  faComment,
} from "@fortawesome/free-solid-svg-icons";
import {
  faInstagram,
  faFacebook,
  faYoutube,
} from "@fortawesome/free-brands-svg-icons";
import {
  SMain,
  SForm,
  SImgSection,
  SContainer,
  SInputContainer,
  SEmailFontAwesomeIcon,
  SUsernameInput,
  SEmailInput,
  SPasswordInput,
  SPasswordCheckInput,
  SBlogInput,
  SYouTubeInput,
  SInstagramInput,
  SFacebookInput,
  SSelfIntroduction,
  SMainContainer,
  SNextButton,
  SCancelButton,
  SBackToLoginButton,
  SSubmitButton,
} from "./styles";
import useInput from "../../hooks/useInput"; // 커스텀 훅
import Lottie from "react-lottie";
import animationData from "../../assets/images/SIGNUP";
import { useEffect } from "react";
import { signupAPI } from "../../utils/api/userAPI";
import { duplicatedEmailCheckAPI } from "../../utils/api/userAPI";
import { LoginStateHandlerContext } from "../../App";
import { useContext } from "react";

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

const SignUp = () => {
  const [username, onChangeUsername] = useInput("");
  const [email, onChangeEmail] = useInput("");
  const [password, onChangePassword] = useInput("");
  const [passwordCheck, onChangePasswordCheck] = useInput("");
  const [blogLink, setBlogLink] = useState("");
  const [youtubeLink, setYoutubeLink] = useState("");
  const [instagramLink, setInstagramLink] = useState("");
  const [facebookLink, setFacebookLink] = useState("");
  const [selfIntroduction, setSelfIntroduction] = useState("");
  const [isNext, setIsNext] = useState(false);
  const [isDuplicatedEmail, setIsDuplicatedEmail] = useState(false);
  const [isCorrect, setIsCorrect] = useState(false);
  const [open, setOpen] = useState(false);

  const navigate = useNavigate();

  const { handleSnackbarInfo } = useContext(LoginStateHandlerContext);

  // 이메일 유효성 검사를 위한 정규표현식
  const regexEmail = new RegExp("[a-z0-9]+@[a-z]+.[a-z]{2,3}");

  // 비밀번호 유효성 검사를 위한 정규표현식
  const regExpPassword =
    /^.*(?=^.{9,16}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;

  // 이름, 이메일, 비밀번호, 비밀번호 확인을 규칙에 맞게 작성했는지 확인하는 함수
  // 규칙에 맞게 작성하지 않았으면 isCorrect를 false로 만들고, 규칙에 맞게 작성했으면 isCorrect를 true로 만든다.
  const handleInputCorrectCheck = useCallback(() => {
    if (
      username.length > 30 ||
      !regexEmail.test(email) ||
      !regExpPassword.test(password) ||
      password !== passwordCheck
    ) {
      setIsCorrect(false);
    }
    if (
      username.length <= 30 &&
      regexEmail.test(email) &&
      regExpPassword.test(password) &&
      password === passwordCheck
    ) {
      setIsCorrect(true);
    }
  }, [username, email, password, passwordCheck, isCorrect]);

  // handleNextForm()를 통해 isDuplicatedEmail이 바뀌면 이메일 중복을 검사하는 axios 요청 실행
  // 이메일이 중복되지 않으면 setIsNext(true)를 통해 추가 입력사항 폼을 보여준다.
  // 이메일이 중복되면 모달을 통해 알림을 준다.
  useEffect(() => {
    duplicatedEmailCheckAPI(
      isDuplicatedEmail,
      email,
      setIsNext,
      setIsDuplicatedEmail,
      setOpen
    );
  }, [isDuplicatedEmail]);

  // 필수입력사항을 모두 입력했으면 이메일 중복 검사를 실행시키는 트리거 함수(useEffect를 실행시킴)
  const handleNextForm = () => {
    if (username && email && password && passwordCheck) {
      setIsNext(true);
    } else {
      setOpen(true);
      setIsNext(false);
    }
    setIsDuplicatedEmail(true);
  };

  // 회원가입 api를 사용하여 회원가입을 진행하는 함수
  const onSubmit = (e) => {
    e.preventDefault();
    if (isNext === true) {
      signupAPI(
        username,
        email,
        password,
        selfIntroduction,
        blogLink,
        youtubeLink,
        instagramLink,
        facebookLink,
        navigate
      );
      handleSnackbarInfo({
        state: true,
        message: "회원가입이 완료되었습니다🎉",
      });
    }
  };

  const handleClose = () => setOpen(false);

  // 필수 입력사항에서 문제가 발생할 경우 모달에 표시될 문구를 반환해주는 함수
  const handleModalText = () => {
    if (!username) return "이름(실명)을 입력해주세요.";
    if (!email) return "이메일을 입력해주세요.";
    if (!password) return "비밀번호를 입력해주세요.";
    if (!passwordCheck) return "비밀번호를 다시 입력해주세요.";
    if (!isDuplicatedEmail) return "이미 존재하는 이메일입니다.";
  };

  return (
    <SMain>
      <SMainContainer>
        <SForm isNext={isNext} onSubmit={onSubmit}>
          <div className="nav__section">
            <NavLink className="Home-link" to={"/"}>
              <img src={logoImg} alt="logo_img" />
            </NavLink>
          </div>
          <SContainer isNext={isNext}>
            {isNext ? null : <h1>회원가입</h1>}

            {isNext ? (
              <div style={{ display: "flex", flexDirection: "column" }}>
                <p className="info-text">
                  * 선택 입력 사항으로, 다른 사용자들에게 공유되는 정보입니다.
                </p>
                <SInputContainer>
                  <SBlogInput
                    className={blogLink ? "active__input" : ""}
                    value={blogLink}
                    onChange={(e) => setBlogLink(e.target.value)}
                    type="text"
                    placeholder="블로그 링크를 입력해주세요"
                  />
                  <SEmailFontAwesomeIcon
                    className={blogLink ? "active__icon" : ""}
                    icon={faLink}
                  />
                </SInputContainer>
                <SInputContainer>
                  <SYouTubeInput
                    className={youtubeLink ? "active__input" : ""}
                    value={youtubeLink}
                    onChange={(e) => setYoutubeLink(e.target.value)}
                    type="text"
                    placeholder="유튜브 링크를 입력해주세요"
                  />
                  <SEmailFontAwesomeIcon
                    className={youtubeLink ? "active__icon" : ""}
                    icon={faYoutube}
                  />
                </SInputContainer>
                <SInputContainer>
                  <SInstagramInput
                    className={instagramLink ? "active__input" : ""}
                    value={instagramLink}
                    onChange={(e) => setInstagramLink(e.target.value)}
                    type="text"
                    placeholder="인스타 계정을 입력해주세요"
                  />
                  <SEmailFontAwesomeIcon
                    className={instagramLink ? "active__icon" : ""}
                    icon={faInstagram}
                  />
                </SInputContainer>
                <SInputContainer>
                  <SFacebookInput
                    className={facebookLink ? "active__input" : ""}
                    value={facebookLink}
                    onChange={(e) => setFacebookLink(e.target.value)}
                    type="text"
                    placeholder="페이스북 계정을 입력해주세요"
                  />
                  <SEmailFontAwesomeIcon
                    className={facebookLink ? "active__icon" : ""}
                    icon={faFacebook}
                  />
                </SInputContainer>
                <SInputContainer>
                  <SSelfIntroduction
                    maxLength={500}
                    className={selfIntroduction ? "active__input" : ""}
                    value={selfIntroduction}
                    onChange={(e) => setSelfIntroduction(e.target.value)}
                    type="text"
                    placeholder="자기소개를 입력해주세요"
                  />
                  <p className="typing-length">
                    {selfIntroduction.length} / 500
                  </p>
                  <SEmailFontAwesomeIcon
                    className={selfIntroduction ? "active__icon" : ""}
                    icon={faComment}
                  />
                </SInputContainer>
              </div>
            ) : (
              <>
                <SInputContainer>
                  <SUsernameInput
                    className={username ? "active__input" : ""}
                    value={username}
                    onChange={onChangeUsername}
                    type="text"
                    placeholder="이름(실명)을 입력해주세요"
                    maxLength={30}
                    onKeyUp={handleInputCorrectCheck}
                  />
                  <SEmailFontAwesomeIcon
                    className={username ? "active__icon" : ""}
                    icon={faUser}
                  />
                  <p>
                    {username.length >= 30 && "최대 30자까지 입력 가능합니다."}
                  </p>
                </SInputContainer>
                <SInputContainer>
                  <SEmailInput
                    className={email ? "active__input" : ""}
                    value={email}
                    onChange={onChangeEmail}
                    type="text"
                    placeholder="이메일을 입력해주세요"
                    onKeyUp={handleInputCorrectCheck}
                  />
                  <SEmailFontAwesomeIcon
                    className={email ? "active__icon" : ""}
                    icon={faEnvelope}
                  />
                  <p>
                    {email &&
                      !regexEmail.test(email) &&
                      "올바른 이메일 형식으로 입력해주세요."}
                  </p>
                </SInputContainer>
                <SInputContainer>
                  <SPasswordInput
                    className={password ? "active__input" : ""}
                    value={password}
                    onChange={onChangePassword}
                    type="password"
                    placeholder="비밀번호를 입력해주세요"
                    onKeyUp={handleInputCorrectCheck}
                  />
                  <SEmailFontAwesomeIcon
                    className={password ? "active__icon" : ""}
                    icon={faLock}
                  />
                  <p>
                    {password &&
                      !regExpPassword.test(password) &&
                      "9~16자 영문, 숫자, 특수문자를 사용하세요."}
                  </p>
                </SInputContainer>
                <SInputContainer>
                  <SPasswordCheckInput
                    className={passwordCheck ? "active__input" : ""}
                    value={passwordCheck}
                    onChange={onChangePasswordCheck}
                    type="password"
                    placeholder="비밀번호를 다시 입력해주세요"
                    onKeyUp={handleInputCorrectCheck}
                  />
                  <SEmailFontAwesomeIcon
                    className={passwordCheck ? "active__icon" : ""}
                    icon={faUnlock}
                  />
                  {passwordCheck && password !== passwordCheck && (
                    <p>비밀번호가 일치하지 않습니다.</p>
                  )}
                </SInputContainer>
              </>
            )}
            {isNext ? (
              <SSubmitButton type="submit">회원가입 완료</SSubmitButton>
            ) : (
              <SNextButton onClick={handleNextForm}>다음</SNextButton>
            )}
            {isNext ? null : (
              <Link to={"/login"}>
                <SBackToLoginButton>로그인으로 돌아가기</SBackToLoginButton>
              </Link>
            )}
          </SContainer>
        </SForm>
        <SImgSection>
          <h1>Welcome to our WebSite!</h1>
          <div>
            <Lottie options={defaultOptions} />
          </div>
        </SImgSection>
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
              {handleModalText()}
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

export default SignUp;
