package com.example.course_service.controller;

import com.example.course_service.service.ReindexService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/reindex")
@RequiredArgsConstructor
class ReindexController {
    private final ReindexService reindexService;

    @PostMapping("/courses")
    public Map<String,Object> reindexCourses(@RequestParam(defaultValue="1000") int pageSize) {
        long n = reindexService.reindexAllCourses(pageSize);
        return Map.of("indexed", n);
    }
}

