package com.addv.pokemontest.service;

import com.addv.pokemontest.exception.InvalidDataException;
import com.addv.pokemontest.exception.PokemonNotFoundException;
import com.addv.pokemontest.model.Pokemon;
import com.addv.pokemontest.repository.PokemonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
class PokemonServiceTest {

    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private PokemonService pokemonService;


    @Test
    void testFindAll() {
        List<Pokemon> pokemonList = new ArrayList<>();
        when(pokemonRepository.findAll()).thenReturn(pokemonList);

        List<Pokemon> currentPokemonList = pokemonService.findAll();

        assertSame(pokemonList, currentPokemonList);
        verify(pokemonRepository, times(1)).findAll();
    }

    @Test
    void testSaveValidPokemonReturnsSavedPokemon() throws Exception {
        Pokemon pokemon = new Pokemon();
        when(pokemonRepository.save(pokemon)).thenReturn(pokemon);

        Pokemon savedPokemon = pokemonService.save(pokemon);

        assertSame(pokemon, savedPokemon);
        verify(pokemonRepository, times(1)).save(pokemon);
    }

    @Test
    void testSaveNullPokemonThrowsInvalidDataException() {
        assertThrows(InvalidDataException.class, () -> {
            pokemonService.save(null);
        });
        verify(pokemonRepository, never()).save(any());
    }

    @Test
    void testFindByIdExistingIdReturnsCorrectPokemon() throws Exception {
        long id = 1;
        Pokemon expectedPokemon = new Pokemon();
        Optional<Pokemon> optionalPokemon = Optional.of(expectedPokemon);
        when(pokemonRepository.findById(id)).thenReturn(optionalPokemon);

        Pokemon actualPokemon = pokemonService.findById(id);

        assertSame(expectedPokemon, actualPokemon);
        verify(pokemonRepository, times(1)).findById(id);
    }

    @Test
    void testFindByIdNonExistingIdThrowsPokemonNotFoundException() {
        long id = 1;
        when(pokemonRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PokemonNotFoundException.class, () -> {
            pokemonService.findById(id);
        });
        verify(pokemonRepository, times(1)).findById(id);
    }

    @Test
    void testFindByNameExistingNameReturnsCorrectPokemon() throws Exception {
        String name = "Pikachu";
        Pokemon expectedPokemon = new Pokemon();
        Optional<Pokemon> optionalPokemon = Optional.of(expectedPokemon);
        when(pokemonRepository.findByName(name)).thenReturn(optionalPokemon);

        Pokemon actualPokemon = pokemonService.findByName(name);

        assertSame(expectedPokemon, actualPokemon);
        verify(pokemonRepository, times(1)).findByName(name);
    }

    @Test
    void testFindByNameNonExistingNameThrowsPokemonNotFoundException() {
        String name = "Pikachu";
        when(pokemonRepository.findByName(name)).thenReturn(Optional.empty());

        assertThrows(PokemonNotFoundException.class, () -> {
            pokemonService.findByName(name);
        });
        verify(pokemonRepository, times(1)).findByName(name);
    }

    @Test
    void testDeleteValidPokemonPokemonRepositoryDeleteCalled() throws Exception {
        Pokemon pokemon = new Pokemon();

        pokemonService.delete(pokemon);

        verify(pokemonRepository, times(1)).delete(pokemon);
    }

    @Test
    void testDeleteNullPokemonThrowsInvalidDataException() {
        assertThrows(InvalidDataException.class, () -> {
            pokemonService.delete(null);
        });
        verify(pokemonRepository, never()).delete(any());
    }
}