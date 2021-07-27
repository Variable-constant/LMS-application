package com.karpenko.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO {
    private Long id;
    private String title;
    private String text;
    private Long courseId;

    public LessonDTO(Long courseId) {
        this.courseId = courseId;
    }
}
