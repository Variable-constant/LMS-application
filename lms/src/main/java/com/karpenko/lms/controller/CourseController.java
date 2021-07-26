package com.karpenko.lms.controller;

import com.karpenko.lms.controller.exceptions.NotFoundException;
import com.karpenko.lms.domain.Course;
import com.karpenko.lms.domain.User;
import com.karpenko.lms.service.CourseLister;
import com.karpenko.lms.service.StatisticsCounter;
import com.karpenko.lms.service.UserLister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {
    private final CourseLister courseLister;
    private final StatisticsCounter statisticsCounter;
    private final UserLister userLister;

    @Autowired
    public CourseController(CourseLister courseLister, UserLister userLister, StatisticsCounter statisticsCounter) {
        this.courseLister = courseLister;
        this.userLister = userLister;
        this.statisticsCounter = statisticsCounter;
    }

    @GetMapping
    public String courseTable(Model model, @RequestParam(name = "titlePrefix", required = false) String titlePrefix) {
        model.addAttribute("courses", courseLister.findByTitleWithPrefix(titlePrefix == null ? "" : titlePrefix));
        model.addAttribute("activePage", "courses");
        return "course_list";
    }

    @GetMapping("/{id}")
    public String courseForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("course", courseLister.findById(id)
                .orElseThrow(NotFoundException::new));
        return "create_course";
    }

    @GetMapping("/new")
    public String courseForm(Model model) {
        model.addAttribute("course", new Course());
        return "create_course";
    }

    @PostMapping
    public String submitCourseForm(@Valid Course course, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create_course";
        }
        courseLister.save(course);
        return "redirect:/course";
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseLister.delete(id);
        return "redirect:/course";
    }

    @GetMapping("/{id}/assign")
    public String getAssignCourse(Model model, @PathVariable("id") Long id) {
        model.addAttribute("courseId", id);
        model.addAttribute("users", userLister.findAll());
        return "assign_course";
    }

    @PostMapping("/{courseId}/assign")
    public String assignUserForm(@PathVariable("courseId") Long courseId,
                                 @RequestParam("userId") Long userId) {
        User user = userLister.findById(userId).orElseThrow(NotFoundException::new);
        Course course = courseLister.findById(courseId).orElseThrow(NotFoundException::new);
        course.getUsers().add(user);
        user.getCourses().add(course);
        courseLister.save(course);
        return String.format("redirect:/course/%d", courseId);
    }

    @DeleteMapping("/{courseId}/unassign")
    public String unassignUser(@PathVariable("courseId") Long courseId,
                               @RequestParam("userId") Long userId) {
        User user = userLister.findById(userId)
                .orElseThrow(NotFoundException::new);
        Course course = courseLister.findById(courseId)
                .orElseThrow(NotFoundException::new);
        user.getCourses().remove(course);
        course.getUsers().remove(user);
        courseLister.save(course);
        return String.format("redirect:/course/%d", courseId);
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
