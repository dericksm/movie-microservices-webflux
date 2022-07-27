package com.github.dericksm.moviesreviewservice.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.github.dericksm.moviesreviewservice.handler.ReviewHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ReviewRouter {

    @Bean
    public RouterFunction<ServerResponse> reviewsRoute(ReviewHandler reviewHandler) {
        return route()
            .nest(path("/v1/reviews"), builder ->
                builder
                    .POST("", request -> reviewHandler.save(request))
                    .GET("", request -> reviewHandler.findAll())
                    .PUT("/{id}", request -> reviewHandler.update(request))
                    .DELETE("/{id}", request -> reviewHandler.delete(request))
                    .GET("/{id}", request -> reviewHandler.findById(request))
            )
            .build();
    }
}
