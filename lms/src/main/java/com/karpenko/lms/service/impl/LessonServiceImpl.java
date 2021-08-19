package com.karpenko.lms.service.impl;

import com.karpenko.lms.dao.LessonRepository;
import com.karpenko.lms.domain.Lesson;
import com.karpenko.lms.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {
    private final LessonRepository repository;

    @Autowired
    public LessonServiceImpl(LessonRepository repository) {
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