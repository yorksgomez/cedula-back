package eci.escuelaing.cedula2.model;

import lombok.*;

@Getter
@Setter
@Builder
public class User {
    private String _id;
    private String username;
    private String password;
}
