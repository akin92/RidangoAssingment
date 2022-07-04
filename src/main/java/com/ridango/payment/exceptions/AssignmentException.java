package com.ridango.payment.exceptions;

import com.ridango.payment.enums.ErrorCode;

public class AssignmentException extends  Exception{
    private ErrorCode errorCode;

    public AssignmentException(ErrorCode code) {
        super();
        this.errorCode = code;
    }

    public AssignmentException(String errorMessage) {
        super(errorMessage);
    }

    public AssignmentException(String errorMessage, ErrorCode errorCodes) {
        super(errorMessage);
        this.errorCode = errorCodes;
    }

    public AssignmentException(String errorMessage, Throwable cause , ErrorCode errorCodes) {
        super(errorMessage,cause);
        this.errorCode = errorCodes;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}