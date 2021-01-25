package com.socket;

import java.math.BigDecimal;
import java.net.URI;
import java.time.Duration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.socket.processor.TransactionRequest;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import reactor.core.publisher.Mono;

public class ReactiveJavaClientWebSocket {
    public static void main(String[] args) throws InterruptedException {

        WebSocketClient client = new ReactorNettyWebSocketClient();
        client.execute(
                URI.create("ws://localhost:8080/event-emitter"),
                session -> session.send(
                        Mono.just(session.textMessage(mock())))
                        .thenMany(session.receive()
                                .map(WebSocketMessage::getPayloadAsText)
                                //.flatMap(this::processMessage)
                                .log())
                        .then())
                .block(Duration.ofSeconds(10L));
    }

    private static String mock() {
        return "{\"action\": \"withdraw\", \"cardnumber\": \"1234567890123456\", \"amount\":\"1,10\"}";
    }

}