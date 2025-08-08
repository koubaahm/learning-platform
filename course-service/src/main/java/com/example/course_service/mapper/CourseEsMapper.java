package com.example.course_service.mapper;


import com.example.course_service.persistence.entity.Course;
import com.example.course_service.search.document.EsCourse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring")
public interface CourseEsMapper {

    @Mapping(target = "id",           expression = "java(String.valueOf(src.getId()))")
    @Mapping(target = "createdDate",  source = "createdDate", qualifiedByName = "ldtToInstant")
    EsCourse toEs(Course src);

    // utile si tu veux convertir une réponse ES vers JPA/DTO
    // @Mapping(target = "id", ignore = true) // JPA gère l'ID
    // @Mapping(target = "createdDate", source = "createdDate", qualifiedByName = "instantToLdt")
    // Course toJpa(EsCourse src);

    @Named("ldtToInstant")
    static Instant ldtToInstant(LocalDateTime ldt) {
        return ldt == null ? null : ldt.atZone(ZoneId.systemDefault()).toInstant();
    }

    @Named("instantToLdt")
    static LocalDateTime instantToLdt(Instant instant) {
        return instant == null ? null : LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
