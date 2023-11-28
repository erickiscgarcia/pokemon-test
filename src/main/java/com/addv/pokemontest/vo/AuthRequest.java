package com.addv.pokemontest.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("Auth request.")
public class AuthRequest {

    @ApiModelProperty(example = "user")
    private String username;
    @ApiModelProperty(example = "addv")
    private String password;
}
