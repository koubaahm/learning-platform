package com.example.course_service.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.course_service.search.repository")
public class ElasticsearchConfig {
}
