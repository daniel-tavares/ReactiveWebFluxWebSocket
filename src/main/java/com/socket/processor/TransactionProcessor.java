package com.socket.processor;

import reactor.core.publisher.Mono;

public interface TransactionProcessor {
    Mono<String> process(String request);
}