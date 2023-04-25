import React, { useContext, useEffect, useState } from "react";
import { SContainer, SNoBoard, SUniDiv } from "./styles";
import Pagination from "../Pagination";
import UniBoard from "../UniBoard";
import { BoardDataContext } from "../../App";
import { boardListAPI } from "../../utils/api/boardAPI";

const BoardList = () => {
  const { list, setList } = useContext(BoardDataContext);

  // Pagination ===========================================
  const limit = 6; // 페이지 당 게시물 수
  const [page, setPage] = useState(1); // 현재 페이지 번호
  const offset = (page - 1) * limit; // 첫 게시물의 위치
  // ======================================================

  useEffect(() => {
    boardListAPI(setList);
  }, []);

  return (
    <>
      {list.length === 0 ? (
        <SNoBoard>
          🐣 아직 등록된 강의가 없습니다. 첫 강의를 생성해주세요!
        </SNoBoard>
      ) : (
        <SContainer className="container">
          {/* offset으로 slicing해서 limit 만큼만 한 화면에 표시 */}
          {list.length > 0 &&
            list.slice(offset, offset + limit).map((data, i) => {
              return (
                <SUniDiv key={i}>
                  <UniBoard className="uni-board" data={data} />
                </SUniDiv>
              );
            })}
        </SContainer>
      )}
      {list.length < 7 ? null : (
        <Pagination
          total={list.length}
          limit={limit}
          page={page}
          setPage={setPage}
        />
      )}
    </>
  );
};

export default BoardList;
