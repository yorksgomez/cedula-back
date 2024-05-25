package eci.escuelaing.cedula2.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class JwtResponseDTO {
    private String jwtToken;
    private String username;
}
