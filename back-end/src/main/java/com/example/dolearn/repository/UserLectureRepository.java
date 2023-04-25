package com.example.dolearn.repository;

import com.example.dolearn.domain.User;
import com.example.dolearn.domain.UserLecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserLectureRepository extends JpaRepository<UserLecture,Long> {

    List<UserLecture> findByUser(User user);

    @Query(value = "select * from member_lecture where lid=:lid and member_type='학생'", nativeQuery = true)
    List<UserLecture> findByLectureId(@Param("lid") Long lid);

    @Query(value = "select * from member_lecture where lid=:lid", nativeQuery = true)
    List<UserLecture> searchLecture(@Param("lid") Long lid);

    @Query(value = "select * from member_lecture where lid=:lid and uid=:uid", nativeQuery = true)
    UserLecture searchLectureMember(@Param("lid") Long lid, @Param("uid") Long uid);

    @Modifying
    @Query(value = "delete from member_lecture where lid=:lid and uid=:uid", nativeQuery = true)
    int deleteLectureMember(@Param("lid") Long lid, @Param("uid") Long uid);

    @Modifying
    @Query(value = "delete from member_lecture where lid=:lid", nativeQuery = true)
    int deleteByLid(@Param("lid") Long lid);
}
