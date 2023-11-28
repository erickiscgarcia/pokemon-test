package com.addv.pokemontest.controller;

import com.addv.pokemontest.business.PokemonBusiness;
import com.addv.pokemontest.exception.ApiEndpointNotFoundException;
import com.addv.pokemontest.exception.ApiFetchDataException;
import com.addv.pokemontest.exception.InvalidDataException;
import com.addv.pokemontest.exception.PokemonNotFoundException;
import com.addv.pokemontest.vo.PokemonVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(value = "/pokemon")
@ControllerAdvice
@Api(tags = "Pokemon Controller")
public class PokemonController {
    private final PokemonBusiness pokemonBusiness;

    @Autowired
    public PokemonController(PokemonBusiness pokemonBusiness) {
        this.pokemonBusiness = pokemonBusiness;
    }

    @ApiOperation(value = "Obtener todos los pokemon")
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(pokemonBusiness.getAll());
    }


    @ApiOperation(value = "Create a new pokemon", notes = "Create a new pokemon with the specified name")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Pokemon created successfully", response = PokemonVo.class),
            @ApiResponse(code = 400, message = "Invalid data"), @ApiResponse(code = 404, message = "Endpoint not found"),
            @ApiResponse(code = 500, message = "Error fetching data from API") })
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

    @ApiOperation(value = "Delete a pokemon by its ID")
    @DeleteMapping @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Pokemon deleted successfully"),
            @ApiResponse(code = 404, message = "Pokemon not found"),
            @ApiResponse(code = 400, message = "Invalid data") })
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

    @ApiOperation(value = "Delete a pokemon by its name")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Pokemon deleted successfully"),
            @ApiResponse(code = 404, message = "Pokemon not found"),
            @ApiResponse(code = 400, message = "Invalid data") })
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
        String errorMessage = "Error fetching data from API.";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(ApiEndpointNotFoundException.class)
    public ResponseEntity<String> handleApiEndpointNotFoundException(ApiEndpointNotFoundException ex) {
        String errorMessage = "Endpoint for retrieving the provided pokemon was not found in the API.";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<String> handleInvalidDataException(InvalidDataException ex) {
        String errorMessage = "The provided information is incorrect.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(PokemonNotFoundException.class)
    public ResponseEntity<String> handlePokemonNotFoundException(PokemonNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
