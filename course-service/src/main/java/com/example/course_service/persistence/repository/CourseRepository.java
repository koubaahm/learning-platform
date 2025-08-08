package com.example.course_service.persistence.repository;


import com.example.course_service.persistence.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    Optional<Course> findByCourseName(String courseName);
}
