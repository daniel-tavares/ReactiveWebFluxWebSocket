package com.socket.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.socket.processor.TransactionRequest;
import com.socket.processor.TransactionResponse;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    private static final ObjectMapper json = new ObjectMapper();

    public TransactionRequest toObject(String message) {

        try {
            return json.readValue(message, TransactionRequest.class);
        } catch (JsonProcessingException e) {
            //TODO lancar excecao de erro
            return null;
        }
    }

    public String toString(TransactionResponse response) {

        try {
            return json.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            //TODO lancar excecao de erro
           return null;
        }
    }
}