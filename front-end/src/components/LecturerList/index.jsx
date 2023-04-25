import React, { useContext, useEffect, useState } from "react";
import { LoginStateContext } from "../../App";
import { lecturerNameAPI } from "../../utils/api/boardAPI";
import { SBox, SContent, SList, SListBox } from "./styles";

const LecturerList = ({ data, setLuid }) => {
  const { isLogined } = useContext(LoginStateContext);
  const [nameList, setNameList] = useState([]);
  // 강사 ID를 parameter에 담아 새 창으로 이동
  const handleProfile = (lid) => {
    window.open(`/board/profile/${lid}`);
  };

  useEffect(() => {
    lecturerNameAPI(data.id, setNameList);
  }, []);

  return (
    <>
      {isLogined && (
        <SBox>
          {nameList.length ? (
            <div className="title">신청 강사 현황 ({nameList.length}명)</div>
          ) : (
            <div className="title">신청 강사 현황</div>
          )}

          <SContent>
            {nameList.length ? (
              <SListBox>
                {nameList.map((item, i) => {
                  return (
                    <SList key={i}>
                      <div
                        className="full-list"
                        onChange={(e) => setLuid(e.target.value)}
                      >
                        {/* 신청한 강사의 uid를 value로 지정해 나중에 api로 서버에 확정 전송 시 이 value를 담아서 보냄 */}
                        <input type="radio" name="lecturer" value={item.uid} />
                        {/* 강사의 이름(user.name)을 순서대로 출력 */}
                        <span
                          className="instructor"
                          onClick={(e) => handleProfile(item.uid)}
                        >
                          {item.user.name}
                        </span>
                      </div>
                    </SList>
                  );
                })}
              </SListBox>
            ) : (
              <div className="empty-list">⏳모집 중입니다..!</div>
            )}
          </SContent>
        </SBox>
      )}
    </>
  );
};

export default LecturerList;
