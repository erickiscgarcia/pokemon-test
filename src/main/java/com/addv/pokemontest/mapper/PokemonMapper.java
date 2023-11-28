package com.addv.pokemontest.mapper;

import com.addv.pokemontest.model.Move;
import com.addv.pokemontest.model.Pokemon;
import com.addv.pokemontest.model.Type;
import com.addv.pokemontest.vo.MoveVo;
import com.addv.pokemontest.vo.PokemonVo;
import com.addv.pokemontest.vo.TypeVo;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PokemonMapper {

    default PokemonVo entityToVo(Pokemon pokemon) {
        PokemonVo pokemonVo = new PokemonVo();
        pokemonVo.setId(pokemon.getId());
        pokemonVo.setName(pokemon.getName());

        List<MoveVo> moveVoList = pokemon.getMoves().stream()
                .map(this::entityToVo)
                .collect(Collectors.toList());
        pokemonVo.setMoves(moveVoList);

        List<TypeVo> typeVoList = pokemon.getTypes().stream()
                .map(this::entityToVo)
                .collect(Collectors.toList());
        pokemonVo.setTypes(typeVoList);

        return pokemonVo;
    }

    default Pokemon entityToVo(PokemonVo pokemonVo) {
        Pokemon pokemon = new Pokemon();
        pokemonVo.setId(pokemon.getId());
        pokemonVo.setName(pokemon.getName());

        List<Move> moveList = pokemonVo.getMoves().stream()
                .map(this::voToEntity)
                .collect(Collectors.toList());
        pokemon.setMoves(moveList);

        List<Type> typeList = pokemonVo.getTypes().stream()
                .map(this::voToEntity)
                .collect(Collectors.toList());
        pokemon.setTypes(typeList);

        return pokemon;
    }

    MoveVo entityToVo(Move move);

    Move voToEntity(MoveVo moveVo);

    TypeVo entityToVo(Type type);

    Type voToEntity(TypeVo typeVo);

}
