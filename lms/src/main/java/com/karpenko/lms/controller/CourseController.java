package com.karpenko.lms.controller;

import com.karpenko.lms.controller.exceptions.NotFoundException;
import com.karpenko.lms.domain.Course;
import com.karpenko.lms.domain.Role;
import com.karpenko.lms.domain.User;
import com.karpenko.lms.service.CourseService;
import com.karpenko.lms.service.StatisticsCounter;
import com.karpenko.lms.service.UserService;
import com.karpenko.lms.service.impl.StatisticsCounterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;
    private final StatisticsCounter statisticsCounter;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    public CourseController(CourseService courseService, UserService userService, StatisticsCounterImpl statisticsCounter) {
        this.courseService = courseService;
        this.userService = userService;
        this.statisticsCounter = statisticsCounter;
    }

    @GetMapping
    public String courseTable(Model model,
                              @RequestParam(name = "titlePrefix", required = false) String titlePrefix,
                              Principal principal) {
        if (principal != null) {
            logger.info("Request from user '{}'", principal.getName());
        }
        model.addAttribute("courses", courseService.findByTitleWithPrefix(titlePrefix == null ? "" : titlePrefix));
        model.addAttribute("activePage", "courses");
        return "course_list";
    }

    @GetMapping("/{id}")
    public String courseForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("course", courseService.findById(id)
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
        courseService.save(course);
        return "redirect:/course";
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseService.delete(id);
        return "redirect:/course";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/assign")
    public String getAssignCourse(Model model, HttpServletRequest request, @PathVariable("id") Long id) {
        model.addAttribute("courseId", id);
        if (request.isUserInRole("ROLE_ADMIN")) {
            model.addAttribute("users", userService.findUsersNotAssignedToCourse(id));
        } else {
            User user = userService.findUserByUsername(request.getRemoteUser())
                    .orElseThrow(NotFoundException::new);
            model.addAttribute("users", Collections.singletonList(user));
        }
        return "assign_course";
    }

    @PostMapping("/{courseId}/assign")
    public String assignUserForm(@PathVariable("courseId") Long courseId,
                                 @RequestParam("userId") Long userId) {
        courseService.assignUserToCourse(userId, courseId);
        return String.format("redirect:/course/%d", courseId);
    }

    @DeleteMapping("/{courseId}/unassign")
    public String unassignUser(@PathVariable("courseId") Long courseId,
                               @RequestParam("userId") Long userId) {
        courseService.unassignUserFromCourse(userId, courseId);
        return String.format("redirect:/course/%d", courseId);
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
