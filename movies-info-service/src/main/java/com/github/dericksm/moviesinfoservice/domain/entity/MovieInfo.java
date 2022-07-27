package com.github.dericksm.moviesinfoservice.domain.entity;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
public class MovieInfo {

    @Id
    private String id;
    private String name;
    private Integer year;
    private List<String> cast;
    private OffsetDateTime releaseDate;

}
