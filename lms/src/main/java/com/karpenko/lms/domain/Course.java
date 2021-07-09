package com.karpenko.lms.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Course {
    private Long id;
    private String author;
    private String title;
}