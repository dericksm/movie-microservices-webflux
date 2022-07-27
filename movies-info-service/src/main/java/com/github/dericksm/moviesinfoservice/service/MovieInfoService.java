package com.github.dericksm.moviesinfoservice.service;

import com.github.dericksm.moviesinfoservice.domain.entity.MovieInfo;
import com.github.dericksm.moviesinfoservice.exception.ResourceNotFoundException;
import com.github.dericksm.moviesinfoservice.repository.MovieInfoRepository;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Validated
@Service
public class MovieInfoService {

    private final MovieInfoRepository movieInfoRepository;

    public MovieInfoService(MovieInfoRepository movieInfoRepository) {
        this.movieInfoRepository = movieInfoRepository;
    }

    public Mono<MovieInfo> save(@NotNull MovieInfo movieInfo) {
        return movieInfoRepository.save(movieInfo);
    }

    public Flux<MovieInfo> findAll() {
        return movieInfoRepository.findAll();
    }

    public Mono<MovieInfo> findById(@NotBlank String id) {
        return movieInfoRepository.findById(id)
            .switchIfEmpty(Mono.error(new ResourceNotFoundException(id)));
    }

    public Mono<MovieInfo> update(@NotEmpty String id, @Valid MovieInfo movieInfo) {
        return this.findById(id).flatMap(movieInfoSaved -> {
            movieInfo.setId(movieInfoSaved.getId());
            return movieInfoRepository.save(movieInfo);
        });
    }

    public Mono<Void> delete(@NotEmpty String id){
        return this.findById(id)
            .flatMap(movieInfo -> movieInfoRepository.deleteById(movieInfo.getId()))
            .then(Mono.empty());
    }
}
