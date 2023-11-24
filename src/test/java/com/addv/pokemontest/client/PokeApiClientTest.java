package com.addv.pokemontest.client;

import com.addv.pokemontest.client.dto.PokemonServiceResponse;
import com.addv.pokemontest.exception.ApiEndpointNotFoundException;
import com.addv.pokemontest.exception.ApiFetchDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
public class PokeApiClientTest {

    @Value("${pokemon.api.url}")
    private String apiUrl;

    @Value("${pokemon.api.path}")
    private String path;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PokeApiClient pokeApiClient;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(pokeApiClient, "apiUrl", apiUrl);
        ReflectionTestUtils.setField(pokeApiClient, "path", path);
    }

    @Test
    public void testGetPokemonInformationByName_Successful() throws Exception {
        String name = "Pikachu";
        String url = apiUrl + path + name;
        PokemonServiceResponse expectedResponse = new PokemonServiceResponse(1, "Pikachu", Collections.emptyList(), Collections.emptyList());
        when(restTemplate.getForObject(url, PokemonServiceResponse.class))
                .thenReturn(expectedResponse);
        PokemonServiceResponse actualResponse = pokeApiClient.getPokemonByName(name);
        assertEquals(expectedResponse, actualResponse);
        verify(restTemplate, times(1)).getForObject(url, PokemonServiceResponse.class);
    }

    @Test
    public void testGetPokemonInformationByName_NotFound() {
        String name = "MissingPokemon";
        String url = apiUrl + path + name;
        when(restTemplate.getForObject(url, PokemonServiceResponse.class))
                .thenThrow(HttpClientErrorException.NotFound.class);
        assertThrows(ApiEndpointNotFoundException.class, () -> {
            pokeApiClient.getPokemonByName(name);
        });
        verify(restTemplate, times(1)).getForObject(url, PokemonServiceResponse.class);
    }

    @Test
    public void testGetPokemonInformationByName_FetchDataException() {
        String name = "Pikachu";
        String url = apiUrl + path + name;
        doThrow(new RestClientException("mock exception")).when(restTemplate).getForObject(url, PokemonServiceResponse.class);
        assertThrows(ApiFetchDataException.class, () -> {
            pokeApiClient.getPokemonByName(name);
        });
        verify(restTemplate, times(1)).getForObject(url, PokemonServiceResponse.class);
    }

}

