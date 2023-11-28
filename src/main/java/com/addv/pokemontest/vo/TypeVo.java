package com.addv.pokemontest.vo;

import lombok.Data;

import javax.persistence.*;

@Data
public class TypeVo {
    private int id;
    private long slot;
    private String name;
    private String url;

}
