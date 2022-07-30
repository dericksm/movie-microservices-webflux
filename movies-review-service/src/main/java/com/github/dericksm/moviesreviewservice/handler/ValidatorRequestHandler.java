package com.github.dericksm.moviesreviewservice.handler;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Mono;

@Component
public class ValidatorRequestHandler {

    private final Validator validator;

    public ValidatorRequestHandler(Validator validator) {
        this.validator = validator;
    }

    public <T> Mono<T> requireValidBody(T object) {

        if (ObjectUtils.isEmpty(object)) {
            return Mono.error(new IllegalArgumentException("Body can't be null"));
        }

        Set<ConstraintViolation<T>> constraintViolations = Optional.of(validator.validate(object))
                                                                   .orElseGet(Collections::emptySet);
        if (constraintViolations.isEmpty()) {
            return Mono.just(object);
        } else {
            return Mono.error(new ConstraintViolationException(constraintViolations));
        }


    }
}