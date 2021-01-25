package com.socket;

import com.socket.document.Card;
import com.socket.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ReactiveWebSocketHandler {

    @Autowired
    private CardService cardService;

    public Mono<ServerResponse> getCard(ServerRequest request) {

        String cardNumber = request.pathVariable("number");

        return ServerResponse.ok().body(cardService.findByNumber(cardNumber), Card.class);
    }
}
