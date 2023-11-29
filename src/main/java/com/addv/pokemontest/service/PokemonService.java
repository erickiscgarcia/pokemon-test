package com.addv.pokemontest.service;

import com.addv.pokemontest.exception.InvalidDataException;
import com.addv.pokemontest.exception.PokemonNotFoundException;
import com.addv.pokemontest.model.Move;
import com.addv.pokemontest.model.Pokemon;
import com.addv.pokemontest.model.Type;
import com.addv.pokemontest.repository.MoveRepository;
import com.addv.pokemontest.repository.PokemonRepository;
import com.addv.pokemontest.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PokemonService {

    private final PokemonRepository pokemonRepository;
    private final MoveRepository moveRepository;
    private final TypeRepository typeRepository;

    @Autowired
    public PokemonService(PokemonRepository pokemonRepository, MoveRepository moveRepository, TypeRepository typeRepository) {
        this.pokemonRepository = pokemonRepository;
        this.moveRepository = moveRepository;
        this.typeRepository = typeRepository;
    }

    /**
     * Get all pokemons.
     *
     * @return A list with all the pokemons on the db.
     */
    public List<Pokemon> findAll() {
        return this.pokemonRepository.findAll();
    }

    /**
     * Save a pokemon.
     *
     * @param pokemon The entity with the pokemon information.
     * @return The pokemon object with the saved information.
     * @throws InvalidDataException If the pokemon object is null.
     */
    @Transactional
    public Pokemon save(Pokemon pokemon) throws InvalidDataException {
        if (pokemon == null) {
            throw new InvalidDataException("The pokemon object can't be null.");
        }
        for (Move iterator: pokemon.getMoves()) {
            Optional<Move> move = moveRepository.findByName(iterator.getName());
            move.ifPresent(value -> iterator.setId(value.getId()));
        }

        for (Type iterator: pokemon.getTypes()) {
            Optional<Type> type = typeRepository.findByName(iterator.getName());
            type.ifPresent(value -> iterator.setId(value.getId()));
        }

        return this.pokemonRepository.save(pokemon);
    }

    /**
     * Find a pokemon by it's id.
     *
     * @param id The provider identifier.
     * @return A pokemon object with the information.
     * @throws PokemonNotFoundException If there is not a pokemon with that id.
     */
    public Pokemon findById(long id) throws PokemonNotFoundException {
        try {
            Optional<Pokemon> pokemon = this.pokemonRepository.findById(id);
            return pokemon.orElseThrow(() -> new PokemonNotFoundException("There is no record related to the provided id."));
        } catch (Exception e) {
            throw new PokemonNotFoundException("Error while finding the pokemon by id.", e);
        }
    }

    /**
     * Find a pokemon by it's name.
     *
     * @param name The provider name.
     * @return A pokemon object with the information.
     * @throws PokemonNotFoundException If there is not a pokemon with that name.
     */
    public Pokemon findByName(String name) throws PokemonNotFoundException {
        try {
            Optional<Pokemon> pokemon = this.pokemonRepository.findByName(name);
            return pokemon.orElseThrow(() -> new PokemonNotFoundException("There is no record related to the provided name."));
        } catch (Exception e) {
            throw new PokemonNotFoundException("Error while finding the pokemon by name.", e);
        }
    }

    /**
     * Delete a pokemon.
     *
     * @param pokemon The pokemon object information.
     * @throws InvalidDataException If the pokemon object is null.
     */
    @Transactional
    public void delete(Pokemon pokemon) throws InvalidDataException {
        if (pokemon == null) {
            throw new InvalidDataException("The pokemon entity can´t be null.");
        }
        this.pokemonRepository.delete(pokemon);
    }


    /**
     * Find all the pokemon by it's type.
     *
     * @param type The provider type.
     * @return A list of pokemon object related to the type provided
     */
    public List<Pokemon> findByType(String type) {
            return this.pokemonRepository.findByTypes_Name(type);
    }

}



