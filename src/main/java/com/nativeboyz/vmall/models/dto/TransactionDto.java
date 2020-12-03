package com.nativeboyz.vmall.models.dto;

public class TransactionDto {

    protected String message;

    public TransactionDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "message='" + message + '\'' +
                '}';
    }
}
