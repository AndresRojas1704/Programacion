package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.DTO.PetDTO;
import co.edu.umanizales.tads.controller.DTO.ResponseDTO;
import co.edu.umanizales.tads.exception.ListDECircularException;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.model.ListDECircular;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.service.ListDECircularService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/listdecircular")
public class ListDECircularController {
    @Autowired
    private ListDECircularService listDECircularService;
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getPets() {
        return new ResponseEntity<>(new ResponseDTO(200, listDECircularService.getPets(), null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addPet(@RequestBody PetDTO petDTO) {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(404, "La ubicación no existe", null), HttpStatus.OK);
        }
        listDECircularService.getPets().addPet(new Pet(petDTO.getName(),petDTO.getGender(),petDTO.getAge(),petDTO.getIdentification(), location, petDTO.isDirty()));
        return new ResponseEntity<>(new ResponseDTO(200,"Se ha adicionado el pet",null),HttpStatus.OK
        );
    }
    @GetMapping(path = "/addtostart")
    public ResponseEntity<ResponseDTO>addToStart(Pet pet) {
        listDECircularService.getPets().addToStart(pet);
        return new ResponseEntity<>(new ResponseDTO(200,"Se ha adicionado al inicio el pet", null),HttpStatus.OK);
    }
}
