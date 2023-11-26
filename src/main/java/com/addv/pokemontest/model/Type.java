package com.addv.pokemontest.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "type")
@Data
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "slot")
    private long slot;
    @Column(name = "name")
    private String name;
    @Column(name = "url")
    private String url;
}
