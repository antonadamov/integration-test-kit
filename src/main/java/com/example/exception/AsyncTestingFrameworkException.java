package com.example.exception;

public class AsyncTestingFrameworkException extends RuntimeException{

    public AsyncTestingFrameworkException(String message) {
        super(message);
    }

    public AsyncTestingFrameworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
