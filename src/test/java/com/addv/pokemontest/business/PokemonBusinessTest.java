
package com.addv.pokemontest.business;

import com.addv.pokemontest.client.PokeApiClient;
import com.addv.pokemontest.client.dto.PokemonServiceResponse;
import com.addv.pokemontest.exception.ApiEndpointNotFoundException;
import com.addv.pokemontest.exception.ApiFetchDataException;
import com.addv.pokemontest.exception.InvalidDataException;
import com.addv.pokemontest.exception.PokemonNotFoundException;
import com.addv.pokemontest.mapper.PokemonApiMapper;
import com.addv.pokemontest.mapper.PokemonMapper;
import com.addv.pokemontest.model.Pokemon;
import com.addv.pokemontest.service.PokemonService;
import com.addv.pokemontest.vo.PokemonVo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
class PokemonBusinessTest {

    @Mock
    private PokemonService pokemonService;

    @Mock
    private PokeApiClient pokeApiClient;

    @Mock
    private PokemonMapper pokemonMapper;

    @Mock
    private PokemonApiMapper pokemonApiMapper;

    @InjectMocks
    private PokemonBusiness pokemonBusiness;

    @Test
    public void testCreatePokemon() throws ApiFetchDataException, ApiEndpointNotFoundException, InvalidDataException, PokemonNotFoundException {
        String name = "Pikachu";
        PokemonServiceResponse response = new PokemonServiceResponse();
        response.setName(name);
        Pokemon pokemon = new Pokemon();
        pokemon.setId(1L);
        when(pokeApiClient.getPokemonByName(name)).thenReturn(response);
        when(pokemonApiMapper.mapPokemon(response)).thenReturn(pokemon);
        when(pokemonService.findById(1L)).thenThrow(new PokemonNotFoundException("Mock Exception"));
        PokemonVo expected = new PokemonVo();
        when(pokemonMapper.entityToVo(any(Pokemon.class))).thenReturn(expected);
        when(pokemonService.save(any(Pokemon.class))).thenReturn(pokemon);

        PokemonVo actual = pokemonBusiness.createPokemon(name);

        assertEquals(expected, actual);
    }

    @Test
    void testCreatePokemonWithValidNameShouldReturnPokemonVo() throws Exception {
        String name = "Pikachu";
        PokemonServiceResponse response = new PokemonServiceResponse();
        response.setName(name);
        response.setId(25);
        Pokemon pokemon = new Pokemon();
        pokemon.setId(25);
        pokemon.setName(name);
        PokemonVo expectedVo = new PokemonVo();
        expectedVo.setId(25);
        expectedVo.setName(name);

        when(pokeApiClient.getPokemonByName(name)).thenReturn(response);
        when(pokemonApiMapper.mapPokemon(response)).thenReturn(pokemon);
        when(pokemonService.findById(25)).thenThrow(new PokemonNotFoundException("Mock Exception"));
        when(pokemonService.save(pokemon)).thenReturn(pokemon);
        when(pokemonMapper.entityToVo(any(Pokemon.class))).thenReturn(expectedVo);


        PokemonVo result = pokemonBusiness.createPokemon(name);

        assertNotNull(result);
        assertEquals(expectedVo.getId(), result.getId());
        assertEquals(expectedVo.getName(), result.getName());

        verify(pokeApiClient, times(1)).getPokemonByName(name);
        verify(pokemonService, times(1)).findById(25);
        verify(pokemonService, times(1)).save(pokemon);
    }

    @Test
    void testCreatePokemonWithExistingPokemonShouldReturnPokemonVo() throws Exception {
        String name = "Pikachu";
        PokemonServiceResponse response = new PokemonServiceResponse();
        response.setName(name);
        response.setId(25);
        Pokemon pokemon = new Pokemon();
        pokemon.setId(25);
        pokemon.setName(name);
        Pokemon pokemonAlreadyExist = new Pokemon();
        pokemonAlreadyExist.setId(25);
        pokemonAlreadyExist.setName(name);
        PokemonVo expectedVo = new PokemonVo();
        expectedVo.setId(25);
        expectedVo.setName(name);

        when(pokeApiClient.getPokemonByName(name)).thenReturn(response);
        when(pokemonApiMapper.mapPokemon(response)).thenReturn(pokemon);
        when(pokemonService.findById(25)).thenReturn(pokemonAlreadyExist);
        when(pokemonMapper.entityToVo(any(Pokemon.class))).thenReturn(expectedVo);


        PokemonVo result = pokemonBusiness.createPokemon(name);

        assertNotNull(result);
        assertEquals(expectedVo.getId(), result.getId());
        assertEquals(expectedVo.getName(), result.getName());

        verify(pokemonService, times(1)).findById(25);
        verify(pokemonService, never()).save(any(Pokemon.class));
    }

    @Test
    void testGetAll() {
        String pikachuName = "Pikachu";
        String charmanderName = "Charmander";
        Pokemon pokemon1 = new Pokemon();
        pokemon1.setId(1);
        pokemon1.setName(pikachuName);
        Pokemon pokemon2 = new Pokemon();
        pokemon2.setId(2);
        pokemon2.setName(charmanderName);

        PokemonVo expectedVo1 = new PokemonVo();
        expectedVo1.setId(25);
        expectedVo1.setName(pikachuName);

        PokemonVo expectedVo2 = new PokemonVo();
        expectedVo2.setId(25);
        expectedVo2.setName(charmanderName);

        when(pokemonService.findAll()).thenReturn(List.of(pokemon1, pokemon2));
        when(pokemonMapper.entityToVo(pokemon1)).thenReturn(expectedVo1);
        when(pokemonMapper.entityToVo(pokemon2)).thenReturn(expectedVo2);

        List<PokemonVo> result = pokemonBusiness.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertNotNull(result.get(0));
        assertNotNull(result.get(1));

        verify(pokemonService, times(1)).findAll();
    }

    @Test
    void testDeletePokemonWithValidIdShouldCallDeleteMethod() throws Exception {
        long id = 1;
        Pokemon pokemon = new Pokemon();
        pokemon.setId(id);

        when(pokemonService.findById(id)).thenReturn(pokemon);

        pokemonBusiness.deletePokemon(id);

        verify(pokemonService, times(1)).findById(id);
        verify(pokemonService, times(1)).delete(pokemon);
    }

    @Test
    void deletePokemonByName_WithValidName_ShouldCallDeleteMethod() throws Exception {
        String name = "Pikachu";
        Pokemon pokemon = new Pokemon();
        pokemon.setId(1);
        pokemon.setName(name);

        when(pokemonService.findByName(name)).thenReturn(pokemon);

        pokemonBusiness.deletePokemonByName(name);

        verify(pokemonService, times(1)).findByName(name);
        verify(pokemonService, times(1)).delete(pokemon);
    }
}