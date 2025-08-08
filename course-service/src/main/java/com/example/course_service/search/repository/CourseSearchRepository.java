package com.example.course_service.search.repository;



import com.example.course_service.search.document.EsCourse;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseSearchRepository extends ElasticsearchRepository<EsCourse, String> {
    List<EsCourse> findByCourseNameContaining(String keyword);
    List<EsCourse> findByCategory(String category);
    List<EsCourse> findByInstructor(String instructor);
}
