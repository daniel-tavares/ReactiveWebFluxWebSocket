package com.socket.service;

import com.socket.document.Card;
import reactor.core.publisher.Mono;

public interface CardService {

    Mono<Card> findByNumber(String number);
    Mono<Card> save(Card card);
}