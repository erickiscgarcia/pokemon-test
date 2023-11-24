package com.addv.pokemontest.exception;

public class ApiFetchDataException extends Exception {
    public ApiFetchDataException(String message) {
        super(message);
    }

    public ApiFetchDataException(String message, Throwable causa) {
        super(message, causa);
    }
}
