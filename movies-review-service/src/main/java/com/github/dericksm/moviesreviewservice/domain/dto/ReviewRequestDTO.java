package com.github.dericksm.moviesreviewservice.domain.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewRequestDTO {

    @NotBlank
    private Long movieInfoId;

    @NotBlank
    private String comment;

    @Min(1)
    @Max(5)
    private Double rating;

}
