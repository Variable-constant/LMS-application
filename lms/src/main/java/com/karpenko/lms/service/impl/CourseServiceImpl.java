package com.karpenko.lms.service.impl;

import com.karpenko.lms.controller.exceptions.NotFoundException;
import com.karpenko.lms.dao.CourseRepository;
import com.karpenko.lms.dao.UserRepository;
import com.karpenko.lms.domain.Course;
import com.karpenko.lms.domain.User;
import com.karpenko.lms.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    public void save(Course course) {
        courseRepository.save(course);
    }

    public void delete(Long id) {
        courseRepository.delete(courseRepository.getById(id));
    }

    public List<Course> coursesByAuthor(String name) {
        List<Course> allCourses = courseRepository.findAll();
        return allCourses.stream().filter(course -> course.getAuthor().equals(name)).collect(Collectors.toList());
    }

    public List<Course> findByTitleWithPrefix(String prefix) {
        return courseRepository.findByTitleLike(prefix.toLowerCase() + "%");
    }

    public void assignUserToCourse(long userId, long courseId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        Course course = courseRepository.findById(courseId).orElseThrow(NotFoundException::new);
        course.getUsers().add(user);
        user.getCourses().add(course);
        courseRepository.save(course);
    }

    public void unassignUserFromCourse(long userId, long courseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(NotFoundException::new);
        user.getCourses().remove(course);
        course.getUsers().remove(user);
        courseRepository.save(course);
    }
}