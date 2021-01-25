package com.socket;

import com.socket.document.Card;
import com.socket.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class DummyData implements CommandLineRunner {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public void run(String... args) throws Exception {

        cardRepository.deleteAll()
                .thenMany(mock().flatMap(cardRepository::save))
                        .subscribe(System.out::println);
    }

    private Flux<Card> mock() {

        List<Card> cards = new ArrayList<>();

        Card card1 = new Card();
        card1.setNumber("1234567890123456");
        card1.setBalance(BigDecimal.valueOf(1000));

        cards.add(card1);

        Card card2 = new Card();
        card2.setNumber("1234567890123457");
        card2.setBalance(BigDecimal.valueOf(1000));

        cards.add(card2);

        return Flux.fromStream(cards.stream());
    }
}
