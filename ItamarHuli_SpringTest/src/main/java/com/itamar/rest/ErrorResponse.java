package com.itamar.rest;

public class ErrorResponse {
    private final String message;
    private final long timestamp;
    private final String exceptionType;

    public ErrorResponse(String message, long timestamp, String exceptionType) {
        this.message = message;
        this.timestamp = timestamp;
        this.exceptionType = exceptionType;
    }

    public static ErrorResponse from(Exception ex) {
        return new ErrorResponse(ex.getMessage(), System.currentTimeMillis(), ex.getClass().getCanonicalName());
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
