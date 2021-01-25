package com.socket.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.socket.utils.BigDecimalSerialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Transaction {

    @JsonIgnore
    private String action;

    @JsonIgnore
    private String code;

    @JsonIgnore
    private String authorizationCode;

    @JsonProperty(value = "amount")
    @JsonSerialize(using = BigDecimalSerialize.class)
    private BigDecimal amount;

    @JsonProperty(value = "date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime date;
}