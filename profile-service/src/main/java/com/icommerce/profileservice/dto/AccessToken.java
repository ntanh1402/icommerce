package com.icommerce.profileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken implements Serializable {

    private static final long serialVersionUID = -1L;

    private AccessTokenType type;

    private String token;

}
