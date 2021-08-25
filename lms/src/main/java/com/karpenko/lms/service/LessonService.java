package com.karpenko.lms.service;

import com.karpenko.lms.dao.LessonRepository;
import com.karpenko.lms.domain.Lesson;

import java.util.List;
import java.util.Optional;

public interface LessonService {
    List<Lesson> findAll();

    Optional<Lesson> findById(Long id);

    void save(Lesson lesson);

    void delete(Long id);
}
