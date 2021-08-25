package com.karpenko.lms.service;

import com.karpenko.lms.domain.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findAll();
    Optional<Course> findById(Long id);
    void save(Course course);
    void delete(Long id);
    List<Course> coursesByAuthor(String name);
    List<Course> findByTitleWithPrefix(String prefix);
    void assignUserToCourse(long userId, long courseId);
    void unassignUserFromCourse(long userId, long courseId);
}
