package com.github.dericksm.moviesreviewservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final String MSG_REVIEW_NOT_FOUND = "Review with id: %s wasn't found";

    public ResourceNotFoundException(String id) {
        super(String.format(MSG_REVIEW_NOT_FOUND, id));
    }
}