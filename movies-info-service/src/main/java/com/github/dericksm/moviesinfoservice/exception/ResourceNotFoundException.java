package com.github.dericksm.moviesinfoservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final String MSG_MOVIE_INFO_NOT_FOUND = "Movie info with id: %d wasn't found";

    public ResourceNotFoundException(String id) {
        super(String.format(MSG_MOVIE_INFO_NOT_FOUND, id));
    }
}