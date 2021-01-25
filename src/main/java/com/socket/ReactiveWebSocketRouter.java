package com.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ReactiveWebSocketRouter {

    @Bean
    public RouterFunction<ServerResponse> router(ReactiveWebSocketHandler handler){
        return RouterFunctions
                .route(GET("/cards/{number}"), handler::getCard);
    }
}
