package com.github.dericksm.moviesreviewservice.domain.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
public class Review {
    @Id
    private String id;
    private Long movieInfoId;
    private String comment;
    private Double rating;
}
