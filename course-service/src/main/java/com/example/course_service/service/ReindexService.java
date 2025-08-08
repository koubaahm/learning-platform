package com.example.course_service.service;


import com.example.course_service.mapper.CourseEsMapper;
import com.example.course_service.persistence.entity.Course;
import com.example.course_service.persistence.repository.CourseRepository;
import com.example.course_service.search.document.EsCourse;
import com.example.course_service.search.repository.CourseSearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReindexService {

    private final CourseRepository courseRepository;
    private final CourseEsMapper courseEsMapper;
    private final CourseSearchRepository courseSearchRepo;
    private final ElasticsearchOperations operations;

    public ReindexService(CourseRepository courseRepository,
                          CourseEsMapper courseEsMapper,
                          CourseSearchRepository courseSearchRepo,
                          ElasticsearchOperations operations) {
        this.courseRepository = courseRepository;
        this.courseEsMapper = courseEsMapper;
        this.courseSearchRepo = courseSearchRepo;
        this.operations = operations;
    }

    @Transactional(readOnly = true)
    public long reindexAllCourses(int pageSize) {
        long totalIndexed = 0L;
        int page = 0;
        IndexCoordinates index = operations.getIndexCoordinatesFor(EsCourse.class);

        Page<Course> p;
        do {
            p = courseRepository.findAll(PageRequest.of(page, pageSize, Sort.by("id")));
            List<EsCourse> docs = p.getContent().stream()
                    .map(courseEsMapper::toEs)
                    .toList();

            List<IndexQuery> indexQueries = docs.stream()
                    .map(es -> new IndexQueryBuilder()
                            .withId(es.getId())
                            .withObject(es)
                            .build())
                    .toList();

            operations.bulkIndex(indexQueries, index);
            totalIndexed += docs.size();
            page++;
        } while (!p.isLast());

        operations.indexOps(index).refresh();
        return totalIndexed;
    }
}
