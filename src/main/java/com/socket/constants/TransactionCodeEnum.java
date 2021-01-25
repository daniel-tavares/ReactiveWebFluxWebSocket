package com.socket.constants;

public enum TransactionCodeEnum {

    APPROVED("00"),
    NO_BALANCE("51"),
    NO_ACCOUNT("14"),
    ERROR("96");

    private String code;

    TransactionCodeEnum(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}