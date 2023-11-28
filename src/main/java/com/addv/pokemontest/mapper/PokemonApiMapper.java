package com.addv.pokemontest.mapper;

import com.addv.pokemontest.client.dto.MoveServiceResponse;
import com.addv.pokemontest.client.dto.PokemonServiceResponse;
import com.addv.pokemontest.client.dto.TypeServiceResponse;
import com.addv.pokemontest.model.Move;
import com.addv.pokemontest.model.Pokemon;
import com.addv.pokemontest.model.Type;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PokemonApiMapper {
    public Pokemon mapPokemon(PokemonServiceResponse response) {
        Pokemon pokemon = new Pokemon();
        pokemon.setId(response.getId());
        pokemon.setName(response.getName());
        pokemon.setMoves(mapMoves(response.getMoves()));
        pokemon.setTypes(mapTypes(response.getTypes()));
        return pokemon;
    }

    private static List<Move> mapMoves(List<MoveServiceResponse> moves) {
        List<Move> moveList = new ArrayList<>();
        for (MoveServiceResponse move : moves) {
            Move moveDto = new Move();
            moveDto.setName(move.getMove().getName());
            moveDto.setUrl(move.getMove().getUrl());
            moveList.add(moveDto);
        }
        return moveList;
    }

    private static List<Type> mapTypes(List<TypeServiceResponse> types) {
        List<Type> typeList = new ArrayList<>();
        for (TypeServiceResponse type : types) {
            Type typeDto = new Type();
            typeDto.setName(type.getType().getName());
            typeDto.setUrl(type.getType().getUrl());
            typeDto.setSlot(type.getSlot());
            typeList.add(typeDto);
        }
        return typeList;
    }
}