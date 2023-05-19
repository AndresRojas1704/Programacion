package co.edu.umanizales.tads.model;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class Pet {
    private String name;
    private char gender;
    private int age;
    private String identification;

    private Location location;
    private boolean dirty;
}
