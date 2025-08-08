package com.example.course_service.dto;

import java.time.LocalDateTime;

public record CourseDto(
         Long id,
         String courseName,
         String courseDescription,
         String category,
         String instructor,
        LocalDateTime createdDate
) {
}
