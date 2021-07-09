package com.karpenko.lms.dao.impl;

import com.karpenko.lms.dao.CourseRepository;
import com.karpenko.lms.domain.Course;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class MemoryBasedCourseRepository implements CourseRepository {

    private final Map<Long, Course> courseMap = new ConcurrentHashMap<>();
    private final AtomicLong identity = new AtomicLong();

    @PostConstruct
    public void init() {
        save(new Course(null, "Вася", "Основы программирования на джава"));
        save(new Course(null, "Вася", "Базы данных для самых маленьких"));
        save(new Course(null, "Петя", "Скучный маркетинг"));
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<>(courseMap.values());
    }

    @Override
    public Optional<Course> findById(long id) {
        return Optional.ofNullable(courseMap.get(id));
    }

    @Override
    public void save(Course course) {
        if (course.getId() == null) {
            course.setId(identity.incrementAndGet());
        }
        courseMap.put(course.getId(), course);
    }

    @Override
    public void delete(long id) {
        courseMap.remove(id);
    }

    @Override
    public List<Course> findByTitleWithPrefix(String prefix) {
        return courseMap.values()
                .stream().filter(course -> course.getTitle().startsWith(prefix))
                .collect(Collectors.toList());
    }
}