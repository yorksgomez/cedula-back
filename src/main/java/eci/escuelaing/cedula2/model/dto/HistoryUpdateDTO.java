package eci.escuelaing.cedula2.model.dto;


import lombok.*;

@Getter
@Setter
@Builder
public class HistoryUpdateDTO {
    private String _id;
    private String information;
    private boolean diabetes;
    private boolean hypertension;
    private boolean cardiacIssues;
}
