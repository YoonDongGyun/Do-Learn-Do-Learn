import React, { useState } from "react";
import { useContext } from "react";
import { LoginStateContext, LoginStateHandlerContext } from "../../App";
import {
  updateProfileImgAPI,
  updateUserInfoAPI,
} from "../../utils/api/userAPI";
import ProfileCardBox from "../ProfileCardBox";
import {
  SProfileEditContainer,
  SSubContainerUp,
  SSubContainerDown,
  SSelfIntroduction,
  SInput,
  SBlackButton,
  STextAreaIcon,
  SInputIcon,
} from "./styles";

import defaultProfileImg from "../../assets/images/defaultProfile.png";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faComment, faGear, faLink } from "@fortawesome/free-solid-svg-icons";
import {
  faFacebook,
  faInstagram,
  faYoutube,
} from "@fortawesome/free-brands-svg-icons";

import { BASE_URL } from "../../utils/api/URL";

const ProfileEdit = (props) => {
  // context API에서 유저 정보 가져오기
  const getUserInfo = useContext(LoginStateContext);
  const { handleUserInfo } = useContext(LoginStateHandlerContext);
  // 받아오는 데이터 -> 수정될 데이터
  const [blogLink, setBlogLink] = useState(getUserInfo.userInfo.blog);
  const [youtubeLink, setYoutubeLink] = useState(getUserInfo.userInfo.youtube);
  const [instagram, setInstagram] = useState(getUserInfo.userInfo.instagram);
  const [facebook, setFacebook] = useState(getUserInfo.userInfo.facebook);
  const [selfIntroduction, setSelfIntroduction] = useState(
    getUserInfo.userInfo.info
  );

  // 프로필 이미지 관련 변수
  const [profileImg, setProfileImg] = useState({
    image_file: "default",
    preview_URL: getUserInfo.userInfo.imgUrl
      ? `${BASE_URL}/user${getUserInfo.userInfo.imgUrl}`
      : defaultProfileImg,
  });

  const fileInput = React.useRef(null);

  // 프로필 이미지 수정을 눌렀을 때,
  // 사진 파일 선택할 수 있는 창 띄움
  const handleEditProfileImg = (e) => {
    fileInput.current.click();
  };
  // 사진 파일 선택한 후,
  // 바뀐 파일로 프로필 사진 변경 미리보기
  const handleFileChange = (e) => {
    const file = e.target.files[0];
    let reader = new FileReader();
    if (file) {
      reader.readAsDataURL(file);
    }
    reader.onloadend = () => {
      const previewImgUrl = reader.result;
      if (previewImgUrl) {
        setProfileImg({
          image_file: file,
          preview_URL: previewImgUrl,
        });
      }
    };
  };

  // 기본 이미지로 되돌리기 (defaultProfile로)
  const handleBackToDefaultProfile = () => {
    setProfileImg({
      image_file: null,
      preview_URL: "",
    });
  };

  // 수정완료 버튼을 눌렀을 때 기능
  // 입력받은 데이터를 db에서 수정하도록 PUT 요청
  // 다시 프로필 화면으로 이동
  const handleCompleteEdit = () => {
    // axios 요청 시 필요한 데이터
    const data = {
      id: getUserInfo.userInfo.id,
      info: selfIntroduction,
      blog: blogLink,
      instagram: instagram,
      facebook: facebook,
      youtube: youtubeLink,
    };
    // 기존 이미지에서 프로필 사진 바꼈을 때만 요청
    if (profileImg.image_file !== "default") {
      updateProfileImgAPI(data, profileImg.image_file, handleUserInfo);
    } else {
      updateUserInfoAPI(data, handleUserInfo);
    }
    props.handleProfileEditBtn("edit");
  };

  return (
    <ProfileCardBox>
      <SProfileEditContainer>
        <div className="profileContentContainer">
          <SSubContainerUp>
            {/* 프로필 이미지 */}
            <div className="profile-container">
              <img
                className="profile__img"
                src={
                  profileImg.preview_URL
                    ? profileImg.preview_URL
                    : defaultProfileImg
                }
                alt="defaultProfile"
                onClick={handleEditProfileImg}
              />
              <FontAwesomeIcon
                className="profil-edit__icon"
                icon={faGear}
                onClick={handleEditProfileImg}
              />
              <input
                type="file"
                // 가능한 업로드 파일 형식 제한
                accept="image/jpg, image/jpeg, image/png"
                ref={fileInput}
                onChange={handleFileChange}
                style={{ display: "none" }}
              />
            </div>

            <section>
              <div>
                {/* 이름 */}
                <span>{getUserInfo.userInfo.name}</span>
                {/* 이메일 */}
                <p>{getUserInfo.userInfo.email}</p>
              </div>
              {/* 마일리지 바 */}
              <div>
                <button
                  className="back__btn"
                  onClick={handleBackToDefaultProfile}
                >
                  기본 이미지로 되돌리기
                </button>
              </div>
            </section>
          </SSubContainerUp>

          {/* 선택항목 */}
          <SSubContainerDown>
            <div className="input__container">
              <SInput
                className={blogLink ? "tip active__input" : "tip"}
                value={blogLink}
                onChange={(e) => setBlogLink(e.target.value)}
                type="text"
                placeholder="블로그 링크를 입력해 주세요. ex) https://www.blog.com/"
              />
              <SInputIcon
                className={blogLink ? "active__icon" : ""}
                icon={faLink}
              />
            </div>
            <div className="input__container">
              <SInput
                className={youtubeLink ? "tip active__input" : "tip "}
                value={youtubeLink}
                onChange={(e) => setYoutubeLink(e.target.value)}
                type="text"
                placeholder="유튜브 링크를 입력해 주세요. ex) https://www.youtube.com/"
              />
              <SInputIcon
                className={youtubeLink ? "active__icon" : " "}
                icon={faYoutube}
              />
            </div>
            <div className="input__container">
              <SInput
                className={instagram ? "tip active__input" : "tip"}
                value={instagram}
                onChange={(e) => setInstagram(e.target.value)}
                type="text"
                placeholder="인스타그램 계정을 입력해 주세요. ex) instagram_username"
              />
              <SInputIcon
                className={instagram ? "active__icon" : ""}
                icon={faInstagram}
              />
            </div>
            <div className="input__container">
              <SInput
                className={facebook ? "tip active__input" : "tip"}
                value={facebook}
                onChange={(e) => setFacebook(e.target.value)}
                type="text"
                placeholder="페이스북 계정을 입력해 주세요. ex) facebook_username"
              />
              <SInputIcon
                className={facebook ? "active__icon" : " "}
                icon={faFacebook}
              />
            </div>
            <div className="input__container">
              <SSelfIntroduction
                maxLength={500}
                className={selfIntroduction ? "tip active__input" : "tip"}
                value={selfIntroduction}
                onChange={(e) => setSelfIntroduction(e.target.value)}
                type="text"
                placeholder="자기소개를 입력해 주세요."
              />
              <STextAreaIcon
                className={
                  selfIntroduction
                    ? "self-introduction__img active__icon"
                    : "self-introduction__img"
                }
                icon={faComment}
                flip="horizontal"
              />
            </div>
            <p className="typing-length">{selfIntroduction.length} / 500</p>
            <SBlackButton className="black-button" onClick={handleCompleteEdit}>
              수정 완료
            </SBlackButton>
          </SSubContainerDown>
        </div>
      </SProfileEditContainer>
    </ProfileCardBox>
  );
};

export default ProfileEdit;
