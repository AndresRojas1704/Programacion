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
    @PostMapping(path = "/addtostart")
    public ResponseEntity<ResponseDTO> addToStart(@RequestBody PetDTO petDTO) {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        listDECircularService.getPets().addToStart(new Pet(petDTO.getName(),petDTO.getGender(), petDTO.getAge(),petDTO.getIdentification(), location, petDTO.isDirty() ));
        return new ResponseEntity<>(new ResponseDTO(200, "la mascota se adiciono al inicio", null),
                HttpStatus.OK);
    }

    @PostMapping(path= "/addtofinal")
    public ResponseEntity<ResponseDTO> addToFinal(@RequestBody PetDTO petDTO) {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        listDECircularService.getPets().addToFinal(new  Pet(petDTO.getName(),petDTO.getGender(), petDTO.getAge(),petDTO.getIdentification(), location, petDTO.isDirty()));
        return new ResponseEntity<>(new ResponseDTO(200, "La mascota se adiciono al final", null), HttpStatus.OK);
    }
    @PostMapping(path = "/addxpositionpet{pos}")
    public ResponseEntity<ResponseDTO> addXPositionPet(@RequestBody PetDTO petDTO,@PathVariable int pos){
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        listDECircularService.getPets().addXPositionPet(new  Pet(petDTO.getName(),petDTO.getGender(), petDTO.getAge(),petDTO.getIdentification(), location, petDTO.isDirty()),pos);
        return new ResponseEntity<>(new ResponseDTO(200, "La mascota se adiciono en posicion: " + pos, null), HttpStatus.OK);
    }
    @GetMapping(path = "/takeshower/{letter}")
    public ResponseEntity<ResponseDTO> takeShower(@PathVariable char letter) {
        int num;
        char letterLower = Character.toLowerCase(letter);

        num = listDECircularService.getPets().takeShowerPet(letter);

        if (num == 0) {
            return new ResponseEntity<>(new ResponseDTO(
                    409, "ERROR: No hay perros para bañar o letra incorrecta", null), HttpStatus.OK);
        }

        if (num == 1) {
            return new ResponseEntity<>(new ResponseDTO(
                    200, "Se bañó la primera mascota de la lista", null), HttpStatus.OK);
        } else {
            if (letterLower == 'd') {
                return new ResponseEntity<>(new ResponseDTO(
                        200, "Se bañó la mascota número " + num + " dirigiendose a la derecha", null), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ResponseDTO(
                        200, "Se bañó la mascota número " + num + " dirigiendose a la izquierda", null), HttpStatus.OK);
            }
        }
    }
}
