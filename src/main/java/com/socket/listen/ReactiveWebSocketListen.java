package com.socket.listen;

import com.socket.processor.TransactionProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Component("ReactiveWebSocketListen")
public class ReactiveWebSocketListen implements WebSocketHandler {

    @Autowired
    private TransactionProcessor transactionProcessor;

    @Override
    public Mono<Void> handle(WebSocketSession session) {

        return session.send(
                session.receive()
                        .flatMap(webSocketMessage -> {

                            String message = webSocketMessage.getPayloadAsText();

                            Mono<String> response = processMessage(message);

                            return response;
                        })
                        .map(session::textMessage)
        );
    }

    private Mono<String> processMessage(String message) {

        return transactionProcessor.process(message);
    }
}