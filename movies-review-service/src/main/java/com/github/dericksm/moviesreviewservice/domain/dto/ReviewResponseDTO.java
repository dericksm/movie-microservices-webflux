package com.github.dericksm.moviesreviewservice.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewResponseDTO {

    private String id;
    private Long movieInfoId;
    private String comment;
    private Double rating;

}
