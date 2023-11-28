package com.addv.pokemontest.controller;

import com.addv.pokemontest.business.PokemonBusiness;
import com.addv.pokemontest.exception.ApiEndpointNotFoundException;
import com.addv.pokemontest.exception.ApiFetchDataException;
import com.addv.pokemontest.exception.InvalidDataException;
import com.addv.pokemontest.exception.PokemonNotFoundException;
import com.addv.pokemontest.vo.PokemonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(value = "/pokemon")
@ControllerAdvice
public class PokemonController {
    private final PokemonBusiness pokemonBusiness;

    @Autowired
    public PokemonController(PokemonBusiness pokemonBusiness) {
        this.pokemonBusiness = pokemonBusiness;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(pokemonBusiness.getAll());
    }

    @GetMapping("/new")
    public ResponseEntity<?> createPokemon(final @NotNull @RequestParam("name") String pokemonName) {
        PokemonVo pokemonCreated = null;
        try {
            pokemonCreated = pokemonBusiness.createPokemon(pokemonName);
        } catch (ApiFetchDataException e) {
            return handleApiFetchDataException(e);
        } catch (ApiEndpointNotFoundException e) {
            return handleApiEndpointNotFoundException(e);
        } catch (InvalidDataException e) {
            return handleInvalidDataException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(pokemonCreated);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(final @Positive @RequestParam("id") long pokemonId) {
        try {
            pokemonBusiness.deletePokemon(pokemonId);
        } catch (PokemonNotFoundException e) {
            return handlePokemonNotFoundException(e);
        } catch (InvalidDataException e) {
            return handleInvalidDataException(e);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/by-name")
    public ResponseEntity<?> deleteByName(final @NotNull @RequestParam("name") String pokemonName) {
        try {
            pokemonBusiness.deletePokemonByName(pokemonName);
        } catch (PokemonNotFoundException e) {
            return handlePokemonNotFoundException(e);
        } catch (InvalidDataException e) {
            return handleInvalidDataException(e);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(ApiFetchDataException.class)
    public ResponseEntity<String> handleApiFetchDataException(ApiFetchDataException ex) {
        String errorMessage = "Error al obtener los datos de la API";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(ApiEndpointNotFoundException.class)
    public ResponseEntity<String> handleApiEndpointNotFoundException(ApiEndpointNotFoundException ex) {
        String errorMessage = "No se encontró el endpoint en la API para obtener el pokemon proporcionado.";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<String> handleInvalidDataException(InvalidDataException ex) {
        String errorMessage = "La información proporcionada es incorrecta";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(PokemonNotFoundException.class)
    public ResponseEntity<String> handlePokemonNotFoundException(PokemonNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
