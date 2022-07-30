package com.github.dericksm.moviesreviewservice.handler;

import com.github.dericksm.moviesreviewservice.domain.dto.ReviewRequestDTO;
import com.github.dericksm.moviesreviewservice.domain.dto.ReviewResponseDTO;
import com.github.dericksm.moviesreviewservice.domain.dto.ReviewUpdateRequestDTO;
import com.github.dericksm.moviesreviewservice.exception.ResourceNotFoundException;
import com.github.dericksm.moviesreviewservice.infrastructure.mapper.ReviewMapper;
import com.github.dericksm.moviesreviewservice.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ReviewHandler {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ValidatorRequestHandler validatorRequestHandler;

    public ReviewHandler(ReviewRepository reviewRepository, ReviewMapper reviewMapper,
        ValidatorRequestHandler validatorRequestHandler) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.validatorRequestHandler = validatorRequestHandler;
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(ReviewRequestDTO.class)
            .flatMap(validatorRequestHandler::requireValidBody)
            .map(reviewMapper::toReview)
            .flatMap(reviewRepository::save)
            .map(reviewMapper::toReviewResponse)
            .flatMap(ServerResponse.status(HttpStatus.CREATED)::bodyValue);

    }

    public Mono<ServerResponse> findAll() {
        return ServerResponse.ok()
            .body(reviewRepository.findAll()
                    .map(reviewMapper::toReviewResponse), ReviewResponseDTO.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        var id = request.pathVariable("id");
        return reviewRepository.findById(request.pathVariable("id"))
            .switchIfEmpty(Mono.error(new ResourceNotFoundException(id)))
            .map(reviewMapper::toReviewResponse)
            .flatMap(ServerResponse.status(HttpStatus.OK)::bodyValue);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        return reviewRepository.deleteById(request.pathVariable("id"))
            .flatMap(ServerResponse.status(HttpStatus.NO_CONTENT)::bodyValue);
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        var id = request.pathVariable("id");
        return reviewRepository.findById(id)
            .switchIfEmpty(Mono.error(new ResourceNotFoundException(id)))
            .flatMap(
                review -> request.bodyToMono(ReviewUpdateRequestDTO.class)
                    .map(requestReview -> {
                        review.setComment(requestReview.getComment());
                        review.setRating(requestReview.getRating());
                        return review;
                    })
            ).map(reviewMapper::toReviewResponse)
            .flatMap(ServerResponse.status(HttpStatus.OK)::bodyValue);
    }
}
