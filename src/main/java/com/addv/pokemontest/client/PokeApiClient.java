package com.addv.pokemontest.client;

import com.addv.pokemontest.client.dto.PokemonServiceResponse;
import com.addv.pokemontest.exception.ApiEndpointNotFoundException;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class PokeApiClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${pokemon.api.url}")
    private String apiUrl;

    @Value("${pokemon.api.path}")
    private String path;

    private final LoadingCache<String, PokemonServiceResponse> cache = Caffeine.newBuilder()
            .maximumSize(100)
            .build(key -> {
                try {
                    return restTemplate.getForObject(apiUrl + path + key, PokemonServiceResponse.class);
                } catch (HttpClientErrorException ex) {
                    throw new ApiEndpointNotFoundException(String.format("API endpoint not found for name: %s Cause: %s",key, ex.getCause()));
                }
            });

    /**
     * Method in charge of obtaining the information of a pokemon by the name in the external API.
     *
     * @param name The pokemon name.
     * @return An PokemonServiceResponse with the pokemon information.
     */
    public PokemonServiceResponse getPokemonByName(final String name){
            return cache.get(name);
    }
}


