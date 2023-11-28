package com.addv.pokemontest.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MoveVo {
    private int id;
    private String name;
    private String url;
}
