package com.github.dericksm.moviesinfoservice.repository;

import com.github.dericksm.moviesinfoservice.domain.model.MovieInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MovieInfoRepository extends ReactiveMongoRepository<MovieInfo, String> {

}
