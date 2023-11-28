package com.addv.pokemontest.vo;

import lombok.Data;

import java.util.List;

@Data
public class PokemonVo{
    private long id;
    private String name;

    private List<MoveVo> moves;
    private List<TypeVo> types;
}
