package com.addv.pokemontest.exception;
public class PokemonNotFoundException extends Exception {
    public PokemonNotFoundException(String message) {
        super(message);
    }

    public PokemonNotFoundException(String message, Throwable causa) {
        super(message, causa);
    }
}
