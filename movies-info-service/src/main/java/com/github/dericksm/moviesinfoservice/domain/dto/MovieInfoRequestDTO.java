package com.github.dericksm.moviesinfoservice.domain.dto;

import java.time.OffsetDateTime;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieInfoRequestDTO {

    @NotEmpty
    private String name;
    @Positive
    private Integer year;
    private List<String> cast;
    private OffsetDateTime releaseDate;

}
