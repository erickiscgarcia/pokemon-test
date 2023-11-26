package com.addv.pokemontest.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "move")
@Data
public class Move {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "url")
    private String url;
}
