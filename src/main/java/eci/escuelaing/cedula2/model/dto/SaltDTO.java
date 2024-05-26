package eci.escuelaing.cedula2.model.dto;


import lombok.*;

@Getter
@Setter
@Builder
public class SaltDTO {
    private String salt;

    public SaltDTO() {
        this.salt="";
    }

    public SaltDTO(String salt) {
        this.salt=salt;
    }

}
