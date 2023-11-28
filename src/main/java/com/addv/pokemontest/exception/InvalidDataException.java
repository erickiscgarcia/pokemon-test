package com.addv.pokemontest.exception;
public class InvalidDataException extends Exception {
    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable causa) {
        super(message, causa);
    }
}
