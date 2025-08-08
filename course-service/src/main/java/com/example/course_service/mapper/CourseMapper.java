package com.example.course_service.mapper;

import com.example.course_service.dto.CourseDto;

import com.example.course_service.persistence.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);
    CourseDto courseToCourseDto(Course course);
    Course courseDtoToCourse(CourseDto courseDto);

}
