package com.socket.service;

import com.socket.document.Card;
import com.socket.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DefaultCardService implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public Mono<Card> findByNumber(String number) {
        return cardRepository.findByNumber(number);
    }

    @Override
    public Mono<Card> save(Card card) {
        return cardRepository.save(card);
    }
}