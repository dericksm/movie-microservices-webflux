package com.github.dericksm.moviesreviewservice.infrastructure.mapper;

import com.github.dericksm.moviesreviewservice.domain.dto.ReviewRequestDTO;
import com.github.dericksm.moviesreviewservice.domain.dto.ReviewResponseDTO;
import com.github.dericksm.moviesreviewservice.domain.entity.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review toReview(ReviewRequestDTO reviewRequestDTO);
    ReviewResponseDTO toReviewResponse(Review review);

}
