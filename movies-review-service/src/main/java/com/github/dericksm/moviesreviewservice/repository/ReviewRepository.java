package com.github.dericksm.moviesreviewservice.repository;

import com.github.dericksm.moviesreviewservice.domain.entity.Review;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends ReactiveMongoRepository<Review, String> {

}
