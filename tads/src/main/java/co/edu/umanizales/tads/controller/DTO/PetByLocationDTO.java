package co.edu.umanizales.tads.controller.DTO;

import co.edu.umanizales.tads.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PetByLocationDTO {
    private Location location;
    private int quantity;
}
