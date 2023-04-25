package com.example.dolearn.repository;

import com.example.dolearn.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture,Long>,LectureBoardRepository {
}
