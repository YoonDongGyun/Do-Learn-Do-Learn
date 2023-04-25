package com.example.dolearn.repository;

import com.example.dolearn.domain.Lecture;

public interface LectureBoardRepository {

    Lecture findByBoardId(Long BoardId);
}
