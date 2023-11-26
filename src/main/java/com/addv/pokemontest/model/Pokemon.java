package com.addv.pokemontest.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "pokemon")
public class Pokemon {
    @Id
    @Column(name = "id")
    private long id;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pokemon_id")
    private List<Move> moves;
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pokemon_id")
    private List<Type> types;
}
