package com.karpenko.lms.controller;

import com.karpenko.lms.domain.Course;
import com.karpenko.lms.service.CourseLister;
import com.karpenko.lms.service.StatisticsCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    private CourseLister courseLister;
    private StatisticsCounter statisticsCounter;

    @Autowired
    public CourseController(CourseLister courseLister, StatisticsCounter statisticsCounter) {
        this.courseLister = courseLister;
        this.statisticsCounter = statisticsCounter;
    }

    @GetMapping("/interesting")
    public List<Course> getInterestingCourses() {
        statisticsCounter.countHandlerCall();
        // У нас есть бизнес инсайт, что все интересные курсы написал Вася
        return courseLister.coursesByAuthor("Вася");
    }
}
