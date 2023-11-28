package com.addv.pokemontest.controller;

import com.addv.pokemontest.business.PokemonBusiness;
import com.addv.pokemontest.exception.*;
import com.addv.pokemontest.vo.PokemonVo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
class PokemonControllerTest {

    @Mock
    private PokemonBusiness pokemonBusiness;

    @InjectMocks
    private PokemonController pokemonController;

    @Test
    void testGetAll() {
        PokemonVo pokemonVo = new PokemonVo();
        pokemonVo.setId(25);
        pokemonVo.setName("Pikachu");

        when(pokemonBusiness.getAll()).thenReturn(List.of(pokemonVo));

        ResponseEntity<?> response = pokemonController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(pokemonVo), response.getBody());
    }


    @Test
    void testCreatePokemon() throws ApiFetchDataException, ApiEndpointNotFoundException, InvalidDataException {
        String pokemonName = "Pikachu";
        PokemonVo pokemonCreated = new PokemonVo();
        when(pokemonBusiness.createPokemon(pokemonName)).thenReturn(pokemonCreated);

        ResponseEntity<?> response = pokemonController.createPokemon(pokemonName);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(pokemonCreated, response.getBody());
    }

    @Test
    void testCreatePokemon_HandleApiFetchDataException() throws Exception {
        String pokemonName = "Pikachu";
        when(pokemonBusiness.createPokemon(pokemonName)).thenThrow(new ApiFetchDataException("Mock Exception"));

        ResponseEntity<?> response = pokemonController.createPokemon(pokemonName);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error al obtener los datos de la API", response.getBody());
    }


    @Test
    void testCreatePokemon_ApiEndpointNotFoundException() throws Exception {
        String pokemonName = "Pikachu";
        when(pokemonBusiness.createPokemon(pokemonName)).thenThrow(new ApiEndpointNotFoundException("Mock exception"));

        ResponseEntity<?> response = pokemonController.createPokemon(pokemonName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No se encontró el endpoint en la API para obtener el pokemon proporcionado.", response.getBody());
    }

    @Test
    void testCreatePokemonApiEndpointInvalidDataException() throws Exception {
        String pokemonName = "Pikachu";
        when(pokemonBusiness.createPokemon(pokemonName)).thenThrow(new InvalidDataException("Mock exception"));

        ResponseEntity<?> response = pokemonController.createPokemon(pokemonName);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("La información proporcionada es incorrecta", response.getBody());
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        long pokemonId = 1L;
        doNothing().when(pokemonBusiness).deletePokemon(pokemonId);

        ResponseEntity<?> response = pokemonController.delete(pokemonId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(pokemonBusiness, times(1)).deletePokemon(pokemonId);
    }

    @Test
    public void testDeletePokemonNotFoundException() throws Exception {
        long pokemonId = 1L;
        PokemonNotFoundException exception = new PokemonNotFoundException("Pokemon not found");
        doThrow(exception).when(pokemonBusiness).deletePokemon(pokemonId);

        ResponseEntity<?> response = pokemonController.delete(pokemonId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(exception.getMessage(), response.getBody());
        verify(pokemonBusiness, times(1)).deletePokemon(pokemonId);
    }

    @Test
    public void testDeleteInvalidDataException() throws Exception {
        long pokemonId = -1L;
        InvalidDataException exception = new InvalidDataException("La información proporcionada es incorrecta");
        doThrow(exception).when(pokemonBusiness).deletePokemon(pokemonId);

        ResponseEntity<?> response = pokemonController.delete(pokemonId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(exception.getMessage(), response.getBody());
        verify(pokemonBusiness, times(1)).deletePokemon(pokemonId);
    }

    @Test
    public void testDeleteByNameSuccess() throws Exception {
        String pokemonName = "pikachu";
        doNothing().when(pokemonBusiness).deletePokemonByName(pokemonName);

        ResponseEntity<?> response = pokemonController.deleteByName(pokemonName);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(pokemonBusiness, times(1)).deletePokemonByName(pokemonName);
    }

    @Test
    public void testDeleteByNamePokemonNotFoundException() throws Exception {
        String pokemonName = "pikachu";
        PokemonNotFoundException exception = new PokemonNotFoundException("Pokemon not found");
        doThrow(exception).when(pokemonBusiness).deletePokemonByName(pokemonName);

        ResponseEntity<?> response = pokemonController.deleteByName(pokemonName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(exception.getMessage(), response.getBody());
        verify(pokemonBusiness, times(1)).deletePokemonByName(pokemonName);
    }

    @Test
    public void testDeleteByNameInvalidDataException() throws Exception {
        String pokemonName = "pikachu";
        InvalidDataException exception = new InvalidDataException("La información proporcionada es incorrecta");
        doThrow(exception).when(pokemonBusiness).deletePokemonByName(pokemonName);

        ResponseEntity<?> response = pokemonController.deleteByName(pokemonName);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(exception.getMessage(), response.getBody());
        verify(pokemonBusiness, times(1)).deletePokemonByName(pokemonName);
    }

}