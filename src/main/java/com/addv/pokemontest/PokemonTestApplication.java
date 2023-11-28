package com.addv.pokemontest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-${spring.profiles.active}.properties")
@ComponentScan("com.addv.pokemontest")
public class PokemonTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PokemonTestApplication.class, args);
    }

}
