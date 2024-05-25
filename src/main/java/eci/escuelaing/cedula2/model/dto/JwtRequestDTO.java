package eci.escuelaing.cedula2.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class JwtRequestDTO {
    private String username;
    private String password;
}
