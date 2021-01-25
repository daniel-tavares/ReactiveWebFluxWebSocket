package com.socket.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.socket.utils.BigDecimalSerialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(value = "cards")
public class Card {

    @Id
    @JsonProperty(value = "cardnumber")
    private String number;

    @JsonProperty(value = "availableAmount")
    @JsonSerialize(using = BigDecimalSerialize.class)
    private BigDecimal balance;

    @JsonProperty(value = "transactions")
    private List<Transaction> transactions = new ArrayList<>();
}
