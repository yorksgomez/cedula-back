package eci.escuelaing.cedula2.model;

import lombok.*;

@Getter
@Setter
@Builder
public class QR {
    private String _id;
    private String salt;
    private String username; 
}
