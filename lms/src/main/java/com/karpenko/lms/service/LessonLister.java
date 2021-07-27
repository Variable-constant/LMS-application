package com.karpenko.lms.service;

import com.karpenko.lms.dao.CourseRepository;
import com.karpenko.lms.dao.LessonRepository;
import com.karpenko.lms.domain.Course;
import com.karpenko.lms.domain.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LessonLister {
    private final LessonRepository repository;

    @Autowired
    public LessonLister(LessonRepository repository) {
        this.repository = repository;
    }

    public List<Lesson> findAll() {
        return repository.findAll();
    }

    public Optional<Lesson> findById(Long id) {
        return repository.findById(id);
    }

    public void save(Lesson lesson) {
        repository.save(lesson);
    }

    public void delete(Long id) {
        repository.delete(repository.getById(id));
    }
}