package eci.escuelaing.cedula2.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class RegisterDTO {
    private String username;
    private String password;
}
