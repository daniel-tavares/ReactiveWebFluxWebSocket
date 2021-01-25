package com.socket.processor;

import com.socket.constants.TransactionCodeEnum;
import com.socket.constants.TransactionTypeEnum;
import com.socket.document.Card;
import com.socket.document.Transaction;
import com.socket.mapper.TransactionMapper;
import com.socket.service.CardService;
import com.socket.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DefaultTransactionProcessor implements TransactionProcessor {

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private CardService cardService;

    @Override
    public Mono<String> process(String message) {

        TransactionRequest request = transactionMapper.toObject(message);

        Mono<Card> cardMono = cardService.findByNumber(request.getCardNumber());

        return cardMono
                .flatMap(card -> drawOut(card, request))
                .switchIfEmpty(noAccount())
                .doOnError(this::onError);
    }

    private Mono<String> drawOut(Card card, TransactionRequest request) {

        if (isBalance(card.getBalance(), request.getAmount())) {

            return approved(card, request);
        }

        return noBalance(card, request);
    }

    private boolean isBalance(BigDecimal balance, BigDecimal amount) {
        return balance.compareTo(amount) >= 0;
    }

    private Mono<String> approved(Card card, TransactionRequest request) {

        Transaction transaction = createTransaction(request, TransactionCodeEnum.APPROVED);

        //debit
        card.setBalance(card.getBalance().subtract(transaction.getAmount()));

        card.getTransactions().add(transaction);

        return cardService.save(card).flatMap(c -> Mono.just(toJson(toResponse(transaction))));
    }

    private Mono<String> noBalance(Card card, TransactionRequest request) {

        Transaction transaction = createTransaction(request, TransactionCodeEnum.NO_BALANCE);

        card.getTransactions().add(transaction);

        return cardService.save(card).flatMap(c -> Mono.just(toJson(toResponse(transaction))));
    }

    private Mono<String> noAccount() {

        TransactionResponse response = toResponse(TransactionCodeEnum.NO_ACCOUNT.getCode());

        return Mono.just(toJson(response));
    }

    private Mono<String> onError(Throwable throwable) {

        TransactionResponse response = toResponse(TransactionCodeEnum.ERROR.getCode());

        return Mono.just(toJson(response));
    }

    private Transaction createTransaction(TransactionRequest request, TransactionCodeEnum transactionCode) {

        Transaction transaction = new Transaction();
        transaction.setAction(request.getAction());
        transaction.setCode(transactionCode.getCode());
        transaction.setAuthorizationCode(NumberUtils.getRandom());
        transaction.setAmount(request.getAmount());
        transaction.setDate(LocalDateTime.now());

        return transaction;
    }

    private TransactionResponse toResponse(Transaction transaction) {

        TransactionResponse response = new TransactionResponse();
        response.setAction(transaction.getAction());
        response.setCode(transaction.getCode());
        response.setAuthorizationCode(transaction.getAuthorizationCode());

        return response;
    }

    private TransactionResponse toResponse(String code) {

        TransactionResponse response = new TransactionResponse();
        response.setAction(TransactionTypeEnum.WITHDRAW.name().toLowerCase());
        response.setCode(code);
        response.setAuthorizationCode(NumberUtils.getRandom());

        return response;
    }

    private String toJson(TransactionResponse response) {
        return transactionMapper.toString(response);
    }
}