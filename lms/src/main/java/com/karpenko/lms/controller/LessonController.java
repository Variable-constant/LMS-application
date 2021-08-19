package com.karpenko.lms.controller;

import com.karpenko.lms.controller.exceptions.NotFoundException;
import com.karpenko.lms.domain.Course;
import com.karpenko.lms.domain.Lesson;
import com.karpenko.lms.dto.LessonDTO;
import com.karpenko.lms.service.CourseService;
import com.karpenko.lms.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lesson")
public class LessonController {
    private final LessonService lessonService;
    private final CourseService courseService;

    @Autowired
    public LessonController(LessonService lessonService, CourseService courseService) {
        this.lessonService = lessonService;
        this.courseService = courseService;
    }

    @GetMapping("/new")
    public String lessonForm(Model model, @RequestParam("course_id") long courseId) {
        model.addAttribute("courseId", courseId);
        model.addAttribute("lesson", new LessonDTO(courseId));
        return "create_lesson";
    }

    @PostMapping
    public String submitLessonForm(LessonDTO lessonDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create_lesson";
        }
        Course course = courseService.findById(lessonDTO.getCourseId()).orElseThrow(NotFoundException::new);
        Lesson lesson = new Lesson(lessonDTO.getId(), lessonDTO.getTitle(), lessonDTO.getText(), course);
        lessonService.save(lesson);
        return String.format("redirect:/course/%d", course.getId());
    }

    @GetMapping("/{id}")
    public String lessonForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("lesson", lessonService.findById(id)
                .map(l -> new LessonDTO(l.getId(), l.getTitle(), l.getText(), l.getCourse().getId()))
                .orElseThrow(NotFoundException::new));
        return "create_lesson";
    }

    @DeleteMapping("/{id}")
    public String deleteLesson(@PathVariable("id") Long id, @RequestParam("course_id") Long courseId) {
        lessonService.delete(id);
        return String.format("redirect:/course/%d", courseId);
    }
}
