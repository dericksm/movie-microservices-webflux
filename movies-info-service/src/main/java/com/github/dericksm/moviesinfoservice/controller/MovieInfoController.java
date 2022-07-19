package com.github.dericksm.moviesinfoservice.controller;

import com.github.dericksm.moviesinfoservice.domain.dto.MovieInfoRequestDTO;
import com.github.dericksm.moviesinfoservice.domain.dto.MovieInfoResponseDTO;
import com.github.dericksm.moviesinfoservice.domain.dto.MovieInfoUpdateRequestDTO;
import com.github.dericksm.moviesinfoservice.infrastructure.mapper.MovieInfoMapper;
import com.github.dericksm.moviesinfoservice.service.MovieInfoService;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movie-info")
public class MovieInfoController {

    private final MovieInfoService movieInfoService;
    private final MovieInfoMapper movieInfoMapper;

    public MovieInfoController(MovieInfoService movieInfoService, MovieInfoMapper movieInfoMapper) {
        this.movieInfoService = movieInfoService;
        this.movieInfoMapper = movieInfoMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfoResponseDTO> save(@Valid @RequestBody MovieInfoRequestDTO movieInfoRequest) {
        final var movieInfo = movieInfoMapper.toMovieInfo(movieInfoRequest);
        return movieInfoService.save(null).map(movieInfoMapper::toMovieInfoResponse);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<MovieInfoResponseDTO> getAll() {
        return movieInfoService.findAll().map(movieInfoMapper::toMovieInfoResponse);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<MovieInfoResponseDTO> getById(@PathVariable @NotEmpty String id) {
        return movieInfoService.findById(id).map(movieInfoMapper::toMovieInfoResponse);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<MovieInfoResponseDTO> update(@PathVariable @NotEmpty String id,
        @RequestBody @Valid MovieInfoUpdateRequestDTO movieInfoUpdateRequest) {
        final var movieInfo = movieInfoMapper.toMovieInfo(movieInfoUpdateRequest);
        return movieInfoService.update(id, movieInfo).map(movieInfoMapper::toMovieInfoResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable @NotEmpty String id) {
        return movieInfoService.delete(id);
    }


}
