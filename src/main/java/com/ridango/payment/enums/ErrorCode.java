package com.ridango.payment.enums;

public enum ErrorCode {
    DATABASE(0, "A database error has occurred."),
    INSUFFICENT_BALANCE(1, "Account does not have enough balance..."),
    ACCOUNT_DOES_NOT_EXIST(2, "Account does not exist !!!"),
    PAYMENT_DOES_NOT_EXIST(3, "Payment does not exist !!!"),
    FAILED_PAYMENT_PROCESS(4, "Failed Payment Process..."),
    NEGATIVE_AMOUNT(5, "Amount Does Not Be Negative..."),;


    private final int code;
    private final String description;

    private ErrorCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}
