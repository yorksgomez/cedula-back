package eci.escuelaing.cedula2.model;

import lombok.*;

@Getter
@Setter
@Builder
public class History {
    private String _id;
    private String username;
    private String information;
    private boolean diabetes;
    private boolean hypertension;
    private boolean cardiacIssues;
}
