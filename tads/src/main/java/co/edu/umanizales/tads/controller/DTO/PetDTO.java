package co.edu.umanizales.tads.controller.DTO;

import lombok.Data;

@Data
public class PetDTO {
    private String name;
    private char gender;
    private int age;
    private String identification;
    private String codeLocation;
    private boolean dirty;
}
