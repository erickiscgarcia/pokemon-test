package com.addv.pokemontest.client;

import com.addv.pokemontest.client.dto.PokemonServiceResponse;
import com.addv.pokemontest.exception.ApiEndpointNotFoundException;
import com.addv.pokemontest.exception.ApiFetchDataException;
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


    /**
     * Method in charge of obtaining the information of a pokemon by the name in the external API.
     *
     * @param name The pokemon name.
     * @return An PokemonServiceResponse with the pokemon information.
     * @throws ApiEndpointNotFoundException If the endpoint is not found in the service.
     * @throws ApiFetchDataException        If there is an error transforming the response from the service to the object.
     */
    public PokemonServiceResponse getPokemonByName(final String name) throws ApiEndpointNotFoundException, ApiFetchDataException {
        try {
            String url = this.apiUrl + this.path + name;
            return restTemplate.getForObject(url, PokemonServiceResponse.class);
        } catch (
                HttpClientErrorException.NotFound ex) {
            throw new ApiEndpointNotFoundException("API endpoint not found", ex.getCause());
        } catch (Exception ex) {
            throw new ApiFetchDataException("Error while fetching data from API", ex.getCause());
        }
    }


}
