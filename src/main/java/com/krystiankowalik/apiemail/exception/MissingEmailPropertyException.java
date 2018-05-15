package com.krystiankowalik.apiemail.exception;

public class MissingEmailPropertyException extends RuntimeException{

    public MissingEmailPropertyException(String message) {
        super(message);
    }
}
