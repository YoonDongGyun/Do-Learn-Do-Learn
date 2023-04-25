package com.example.dolearn.repository;

import com.example.dolearn.domain.UserBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserBoardRepository extends JpaRepository <UserBoard, Long> {

    List<UserBoard> findByUid(Long uid);

    @Query(value = "select * from member_board where bid=:bid and member_type='강사'", nativeQuery = true)
    List<UserBoard> findInstructors(@Param("bid") Long bid);

    @Query(value = "select * from member_board where bid=:bid and member_type='학생'", nativeQuery = true)
    List<UserBoard> findStudents(@Param("bid") Long bid);

    @Modifying
    @Query(value = "delete from member_board where uid=:uid and bid=:bid")
    int delete(@Param("uid") Long uid, @Param("bid") Long bid);

    @Modifying
    @Query(value = "delete from member_board where bid=:bid")
    int deleteByBid(@Param("bid") Long bid);
}
