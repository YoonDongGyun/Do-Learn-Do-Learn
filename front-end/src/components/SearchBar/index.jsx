import React, { useState, useContext } from "react";
import SearchIcon from "@mui/icons-material/Search";
import Input from "@mui/joy/Input";
import { SSearchContainer, SWarning } from "./styles";
import { searchAPI } from "../../utils/api/boardAPI";
import { BoardDataContext } from "../../App";

const SearchBar = () => {
  const { setList } = useContext(BoardDataContext);
  const [isEmpty, setIsEmpty] = useState(false); // 검색 결과가 있는지 확인

  // 검색 수행
  const doSearch = () => {
    searchAPI(search, setList, setIsEmpty);
  };

  // 검색 input값
  const [search, setSearch] = useState("");

  // input에 입력된 값을 search에 저장
  const onChange = (e) => {
    setSearch(e.currentTarget.value);
  };

  // Enter 키를 눌렀을 때의 작업 처리
  const handleSearch = (e) => {
    e.preventDefault();
    doSearch();
    // setSearch("");
  };

  return (
    <SSearchContainer>
      <form className="search-bar" onSubmit={(e) => handleSearch(e)}>
        <Input
          className="input-box"
          onChange={onChange}
          value={search}
          placeholder="원하는 강의를 검색하세요"
          aria-label="Search"
          size="sm"
          endDecorator={
            <SearchIcon
              onClick={(e) => handleSearch(e)}
              style={{ cursor: "pointer" }}
            />
          }
        />
      </form>
      {isEmpty ? <SWarning>검색 결과를 찾을 수 없습니다.</SWarning> : ""}
    </SSearchContainer>
  );
};

export default SearchBar;
