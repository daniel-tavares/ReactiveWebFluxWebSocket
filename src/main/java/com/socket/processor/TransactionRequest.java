package com.socket.processor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.socket.utils.BigDecimalDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest implements Serializable {

    @JsonProperty("action")
    private String action;

    @JsonProperty("cardnumber")
    private String cardNumber;

    @JsonProperty("amount")
    @JsonDeserialize(using = BigDecimalDeserialize.class)
    private BigDecimal amount;
}