package com.addv.pokemontest.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PokemonServiceResponse {
    private long id;
    private String name;
    private List<MoveServiceResponse> moves;
    private List<TypeServiceResponse> types;

    /**
     * The get method is written to get only the first 4 moves.
     *
     * @return A list with MoveServiceResponse information.
     */
    public List<MoveServiceResponse> getMoves() {
        if (moves != null && moves.size() > 4) {
            return moves.subList(0, 4);
        }
        return moves;
    }
}
