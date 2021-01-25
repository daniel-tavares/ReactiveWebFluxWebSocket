package com.socket.processor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponse {

    @JsonProperty("action")
    private String action;

    @JsonProperty("code")
    private String code;

    @JsonProperty("authorization_code")
    private String authorizationCode;
}