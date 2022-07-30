package com.github.dericksm.moviesreviewservice.domain.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
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

    @Positive
    private Long movieInfoId;

    @NotBlank
    private String comment;

    @Min(1)
    @Max(5)
    private Double rating;
}
