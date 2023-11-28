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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PokemonBusiness {

    private final PokemonService pokemonService;

    private final PokeApiClient pokeApiClient;

    private final PokemonMapper pokemonMapper;

    private final PokemonApiMapper pokemonApiMapper;

    @Autowired
    public PokemonBusiness(PokemonService pokemonService, PokeApiClient pokeApiClient, PokemonMapper pokemonMapper, PokemonApiMapper pokemonApiMapper) {
        this.pokemonService = pokemonService;
        this.pokeApiClient = pokeApiClient;
        this.pokemonMapper = pokemonMapper;
        this.pokemonApiMapper = pokemonApiMapper;
    }

    /**
     * This method makes a query to the external API and then saves it to the database.
     *
     * @param name Pokemon name
     * @return a PokemonVo object with the pokemon information.
     * @throws ApiFetchDataException If there is an error transforming the response from the service to the object.
     * @throws ApiEndpointNotFoundException If the endpoint is not found in the service.
     * @throws InvalidDataException If the pokemon object is null.
     */
    public PokemonVo createPokemon(final String name) throws ApiFetchDataException, ApiEndpointNotFoundException, InvalidDataException {
        PokemonServiceResponse response = pokeApiClient.getPokemonByName(name);
        Pokemon pokemon = pokemonApiMapper.mapPokemon(response);

        Pokemon pokemonAlreadyExist = null;
        try {
            pokemonAlreadyExist = pokemonService.findById(pokemon.getId());
            return pokemonMapper.entityToVo(pokemonAlreadyExist);
        } catch (PokemonNotFoundException e) {
            return pokemonMapper.entityToVo(pokemonService.save(pokemon));

        }
    }

    /**
     * Gets the pokemon in the database.
     * @return A list of PokemonVo items.
     */
    public List<PokemonVo> getAll() {
        return pokemonService.findAll().stream().map(pokemonMapper::entityToVo).collect(Collectors.toList());
    }

    /**
     * Method to eliminate a pokemon by it's identifier.
     *
     * @param id The pokemon identifier.
     *
     * @throws PokemonNotFoundException If there is not a pokemon with that id.
     * @throws InvalidDataException If the pokemon object is null.
     */
    public void deletePokemon(long id) throws PokemonNotFoundException, InvalidDataException {
        Pokemon pokemon = pokemonService.findById(id);
        pokemonService.delete(pokemon);
    }

    /**
     * Method to eliminate a pokemon by it's name.
     *
     * @param name The pokemon identifier.
     *
     * @throws PokemonNotFoundException If there is not a pokemon with that name.
     * @throws InvalidDataException If the pokemon object is null.
     */
    public void deletePokemonByName(String name) throws PokemonNotFoundException, InvalidDataException {
        Pokemon pokemon = pokemonService.findByName(name);
        pokemonService.delete(pokemon);
    }

    /**
     * Method to eliminate the pokemon by it's type.
     *
     * @param type The pokemon type.
     *
     * @throws InvalidDataException If the pokemon list is empty.
     */
    public void deletePokemonByType(String type) throws InvalidDataException {
        List<Pokemon> pokemonToDelete = pokemonService.findByType(type);
        if (pokemonToDelete.isEmpty()){
            throw new InvalidDataException("The pokemon type provided is not related to any pokemon.");
        }
        for(Pokemon pokemon : pokemonToDelete) {
                pokemonService.delete(pokemon);
        }
    }
}
