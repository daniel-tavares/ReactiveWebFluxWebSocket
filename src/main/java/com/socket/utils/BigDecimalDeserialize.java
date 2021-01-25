package com.socket.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Slf4j
public class BigDecimalDeserialize extends JsonDeserializer<BigDecimal> {

    DecimalFormat nf = new DecimalFormat ("#,##0.##");

    @Override
    public BigDecimal deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        BigDecimal bd = null;

        try {
            nf.setParseBigDecimal (true);
            bd = (BigDecimal) nf.parse(jp.getValueAsString());
        } catch (Exception e) {
            log.error("Erro ao deseralizar bigdecimal", e);
        }

        return bd;
    }
}