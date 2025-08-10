package com.example.course_service.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.course_service.search.repository")
public class ElasticsearchConfig {
    @Value("${spring.elasticsearch.uris}")
    private String uris;


    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(uris.replace("http://","").replace("https://",""))
                .build();
    }
}
