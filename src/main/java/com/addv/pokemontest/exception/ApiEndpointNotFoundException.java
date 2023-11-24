package com.addv.pokemontest.exception;

public class ApiEndpointNotFoundException extends Exception {
    public ApiEndpointNotFoundException(String message) {
        super(message);
    }

    public ApiEndpointNotFoundException(String message, Throwable causa) {
        super(message, causa);
    }
}
