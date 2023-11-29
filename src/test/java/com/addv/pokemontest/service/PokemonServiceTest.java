package com.addv.pokemontest.service;

import com.addv.pokemontest.exception.InvalidDataException;
import com.addv.pokemontest.exception.PokemonNotFoundException;
import com.addv.pokemontest.model.Move;
import com.addv.pokemontest.model.Pokemon;
import com.addv.pokemontest.model.Type;
import com.addv.pokemontest.repository.MoveRepository;
import com.addv.pokemontest.repository.PokemonRepository;
import com.addv.pokemontest.repository.TypeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
class PokemonServiceTest {

    @Mock
    private PokemonRepository pokemonRepository;

    @Mock
    private MoveRepository moveRepository;

    @Mock
    private TypeRepository typeRepository;

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
    public void testSave() throws InvalidDataException {
        Pokemon pokemon = new Pokemon();
        pokemon.setName("Charizard");
        pokemon.setTypes(Arrays.asList(new Type(1L, 1L, "Fire", "url"), new Type(2L, 2L, "Flying", "url")));
        pokemon.setMoves(Arrays.asList(new Move(1L, "Flamethrower", "url"), new Move(2L, "Air Slash", "url")));

        when(pokemonRepository.save(pokemon)).thenReturn(pokemon);
        when(moveRepository.findByName(anyString())).thenReturn(Optional.of(new Move(1L, "Flamethrower", "url")));
        when(typeRepository.findByName(anyString())).thenReturn(Optional.of(new Type(1L, 1L, "Fire", "url")));

        Pokemon savedPokemon = pokemonService.save(pokemon);

        assertEquals(pokemon, savedPokemon);
        verify(moveRepository, times(2)).findByName(anyString());
        verify(typeRepository, times(2)).findByName(anyString());
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

    @Test
    public void testFindByType() {
        String type = "Fire";
        List<Pokemon> expectedPokemons = Arrays.asList(
                new Pokemon(1L, "Charizard", Arrays.asList(new Move(1L, "Flamethrower", "url"), new Move(2L, "Air Slash", "url")), Arrays.asList(new Type(1L, 1L, "Fire", "url"), new Type(2L, 2L, "Flying", "url"))),
                new Pokemon(2L, "Blastoise", Arrays.asList(new Move(3L, "Surf", "url"), new Move(4L, "Hydro Pump", "url")), List.of(new Type(3L, 3L, "Water", "url")))
        );
        when(pokemonRepository.findByTypes_Name(type)).thenReturn(expectedPokemons);

        List<Pokemon> actualPokemons = pokemonService.findByType(type);

        assertEquals(expectedPokemons, actualPokemons);
    }
}