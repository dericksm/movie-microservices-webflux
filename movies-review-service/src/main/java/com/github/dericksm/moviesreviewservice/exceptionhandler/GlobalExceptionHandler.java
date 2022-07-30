package com.github.dericksm.moviesreviewservice.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.error(ex.getMessage(), ex);
        var error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());

        if (ex instanceof ConstraintViolationException) {
            var status = HttpStatus.BAD_REQUEST;
            exchange.getResponse().setStatusCode(status);
            error = new ErrorResponse(status.value(), ex.getMessage());
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return writeErrorResponse(exchange, error);
    }

    private Mono<Void> writeErrorResponse(ServerWebExchange exchange, ErrorResponse errorResponse) {

        DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();

        try {
            return exchange.getResponse().writeWith(Mono.just(dataBufferFactory.wrap(mapper.writeValueAsBytes(errorResponse))));
        } catch (Exception ex) {
            log.warn("Error writing response", ex);
            return exchange.getResponse().writeWith(Mono.just(dataBufferFactory.wrap(new byte[0])));
        }
    }
}
