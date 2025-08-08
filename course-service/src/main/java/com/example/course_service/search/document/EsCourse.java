package com.example.course_service.search.document;

import org.springframework.data.elasticsearch.annotations.*;

import java.time.Instant;


@Document(indexName = "courses")
@Mapping(mappingPath = "/elasticsearch/courses-mappings.json")
@Setting(settingPath = "/elasticsearch/courses-settings.json")
public class EsCourse {
    @org.springframework.data.annotation.Id
    private String id;

    @Field(type = FieldType.Text)     private String courseName;
    @Field(type = FieldType.Text)     private String courseDescription;
    @Field(type = FieldType.Keyword)  private String category;
    @Field(type = FieldType.Keyword)  private String instructor;


    @Field(type = FieldType.Date,
            format = DateFormat.strict_date_optional_time,
            pattern = "strict_date_optional_time||epoch_millis")
    private Instant createdDate;

    public EsCourse(String id, String courseName, String courseDescription, String category, String instructor, Instant createdDate) {
        this.id = id;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.category = category;
        this.instructor = instructor;
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}

