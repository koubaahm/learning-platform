package com.example.course_service.service;


import com.example.course_service.dto.CourseDto;
import com.example.course_service.search.document.EsCourse;


import java.util.List;

public interface CourseService {
    CourseDto create(CourseDto  courseDto);
    CourseDto findById(Long id);
    CourseDto findByName(String name);
    CourseDto updateCourse(CourseDto courseDto,  Long id);
    void deleteById(Long id);
    List<CourseDto> findAll();
    List<EsCourse> searchCourses(String keyword);

}
