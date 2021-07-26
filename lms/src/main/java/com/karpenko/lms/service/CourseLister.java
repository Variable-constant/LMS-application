package com.karpenko.lms.service;

import com.karpenko.lms.dao.CourseRepository;
import com.karpenko.lms.domain.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseLister {
    private final CourseRepository repository;

    @Autowired
    public CourseLister(CourseRepository repository) {
        this.repository = repository;
    }

    public List<Course> findAll() {
        return repository.findAll();
    }

    public Optional<Course> findById(Long id) {
        return repository.findById(id);
    }

    public void save(Course course) {
        repository.save(course);
    }

    public void delete(Long id) {
        repository.delete(repository.getById(id));
    }

    public List<Course> coursesByAuthor(String name) {
        List<Course> allCourses = repository.findAll();
        return allCourses.stream().filter(course -> course.getAuthor().equals(name)).collect(Collectors.toList());
    }

    public List<Course> findByTitleWithPrefix(String prefix) {
        return repository.findByTitleLike(prefix.toLowerCase() + "%");
    }
}