package com.github.dericksm.moviesinfoservice.domain.dto;

import java.time.OffsetDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieInfoResponseDTO {

    private String id;
    private String name;
    private Integer year;
    private List<String> cast;
    private OffsetDateTime releaseDate;

}
