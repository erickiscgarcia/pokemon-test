package com.addv.pokemontest.client.dto;

import lombok.Data;

@Data
public class TypeServiceResponse {
    private long slot;
    private TypeInfoServiceResponse type;
}
