package com.github.dericksm.moviesinfoservice.infrastructure.mapper;

import com.github.dericksm.moviesinfoservice.domain.dto.MovieInfoRequestDTO;
import com.github.dericksm.moviesinfoservice.domain.dto.MovieInfoResponseDTO;
import com.github.dericksm.moviesinfoservice.domain.dto.MovieInfoUpdateRequestDTO;
import com.github.dericksm.moviesinfoservice.domain.entity.MovieInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieInfoMapper {

    MovieInfo toMovieInfo(MovieInfoRequestDTO movieInfoRequestDTO);
    MovieInfoResponseDTO toMovieInfoResponse(MovieInfo movieInfo);
    MovieInfo toMovieInfo(MovieInfoUpdateRequestDTO requestDTO);

}
