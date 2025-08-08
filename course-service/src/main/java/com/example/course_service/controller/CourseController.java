package com.example.course_service.controller;

import com.example.course_service.dto.CourseDto;

import com.example.course_service.search.document.EsCourse;
import com.example.course_service.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/courses")
public class CourseController {
    public final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public CourseDto addCourse(@RequestBody CourseDto courseDto) {
        return courseService.create(courseDto);
    }

    @PutMapping("/{id}")
    public CourseDto updateCourse(@RequestBody CourseDto courseDto, @PathVariable Long id) {
        return courseService.updateCourse(courseDto, id);
    }

    @GetMapping("/all")
    public List<CourseDto> findAll() {
        return courseService.findAll();
    }

    @GetMapping("/{id}")
    public CourseDto findById(@PathVariable Long id) {
        return courseService.findById(id);

    }

    @GetMapping("/name")
    public CourseDto findByCourseName(@RequestParam String courseName) {
        return courseService.findByName(courseName);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        courseService.deleteById(id);
    }

    @GetMapping("/search")
    public List<EsCourse> search(@RequestParam String keyword) {
        return courseService.searchCourses(keyword);
    }


}
