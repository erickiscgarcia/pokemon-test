package com.addv.pokemontest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.addv.pokemontest")
public class PokemonTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PokemonTestApplication.class, args);
    }

}
