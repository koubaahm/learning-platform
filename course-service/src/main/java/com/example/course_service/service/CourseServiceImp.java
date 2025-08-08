package com.example.course_service.service;

import com.example.course_service.dto.CourseDto;

import com.example.course_service.mapper.CourseEsMapper;
import com.example.course_service.mapper.CourseMapper;
import com.example.course_service.persistence.entity.Course;
import com.example.course_service.persistence.repository.CourseRepository;
import com.example.course_service.search.document.EsCourse;
import com.example.course_service.search.repository.CourseSearchRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CourseServiceImp implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseSearchRepository searchRepository;
    private final CourseMapper courseMapper;
    private final CourseEsMapper courseEsMapper;

    public CourseServiceImp(CourseRepository courseRepository,
                            CourseSearchRepository searchRepository,
                            CourseMapper courseMapper,
                            CourseEsMapper courseEsMapper) {
        this.courseRepository = courseRepository;
        this.searchRepository = searchRepository;
        this.courseMapper = courseMapper;
        this.courseEsMapper = courseEsMapper;
    }

    @Override
    @Transactional
    public CourseDto create(CourseDto courseDto) {
        Course course = courseMapper.courseDtoToCourse(courseDto);
        course.setCreatedDate(LocalDateTime.now());
        courseRepository.save(course);
        searchRepository.save(courseEsMapper.toEs(course));
        return courseMapper.courseToCourseDto(course);
    }

    @Override
    public CourseDto findById(Long id) {
        return courseRepository.findById(id)
                .map(courseMapper::courseToCourseDto)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id " + id));
    }

    @Override
    public CourseDto findByName(String name) {
        return courseRepository.findByCourseName(name)
                .map(courseMapper::courseToCourseDto)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with name " + name));
    }

    @Override
    @Transactional
    public CourseDto updateCourse(CourseDto courseDto, Long id) {
        Course existedCourse = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id " + id));
        Course updatedCourse = courseMapper.courseDtoToCourse(courseDto);
        updatedCourse.setId(existedCourse.getId());
        // (Optionnel) garder la date de création d’origine :
        // updatedCourse.setCreatedDate(existedCourse.getCreatedDate());
        updatedCourse.setCreatedDate(LocalDateTime.now());
        courseRepository.save(updatedCourse);
        searchRepository.save(courseEsMapper.toEs(updatedCourse));
        return courseMapper.courseToCourseDto(updatedCourse);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        courseRepository.findById(id).ifPresentOrElse(
                course -> {
                    courseRepository.delete(course);
                    searchRepository.deleteById(String.valueOf(id));
                },
                () -> { throw new EntityNotFoundException("Course not found with id: " + id); }
        );
    }

    @Override
    public List<CourseDto> findAll() {
        return courseRepository.findAll().stream()
                .map(courseMapper::courseToCourseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EsCourse> searchCourses(String keyword) {
        return searchRepository.findByCourseNameContaining(keyword);
    }
}
