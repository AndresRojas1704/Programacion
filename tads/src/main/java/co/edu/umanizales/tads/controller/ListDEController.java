package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.DTO.*;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.exception.ListSEException;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.ListDE;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.service.ListDEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/listde")
public class ListDEController {
    @Autowired
    private ListDEService listDEService;
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getPets() {
        return new ResponseEntity<>(new ResponseDTO(200, listDEService.getPets(), null), HttpStatus.OK);
    }
    @GetMapping("/invert")
    public ResponseEntity<ResponseDTO> invert() {
        listDEService.invert();
        return new ResponseEntity<>(new ResponseDTO(200, "Se ha invertido la lista", null), HttpStatus.OK);

    }
    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() {
        listDEService.getPets().changeExtremes();
        return new ResponseEntity<>(new ResponseDTO(200, "SE han intercambiado los extremos", null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addPet(@RequestBody PetDTO petDTO) {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(404, "La ubicación no existe", null), HttpStatus.OK);
        }
        try {
            listDEService.getPets().addPet(new Pet(petDTO.getName(), petDTO.getGender(), petDTO.getAge(), petDTO.getIdentification(), location));

        } catch (ListDEException e){
            return new ResponseEntity<>(new ResponseDTO(409,e.getMessage(),null),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(200,"Se ha adicionado el pet",null),HttpStatus.OK
        );
    }
    @GetMapping(path = "/removepet/{id}")
    public ResponseEntity<ResponseDTO>removepetbyage(byte age) {
        listDEService.removePetByAge(age);
        return new ResponseEntity<>(new ResponseDTO(200,"pet eliminado",null),HttpStatus.OK);
    }
    @GetMapping(path = "/removepetbyage/{age}")
    public ResponseEntity<ResponseDTO>losePosition(@PathVariable byte age){
        listDEService.removePetByAge(age);
        return new ResponseEntity<>(new ResponseDTO(200, "Los pets con esa edad se eliminaron",null),HttpStatus.OK);
    }
    @GetMapping (path = "/orderbygender")
    public ResponseEntity<ResponseDTO> orderbygender() throws ListSEException {
        listDEService.orderByGender();
        return new ResponseEntity<>(new ResponseDTO(200,"pets ordenados",null), HttpStatus.OK);
    }
    @PostMapping(path = "/gainposition")
    public ResponseEntity<ResponseDTO> gainposition(@RequestBody Map<String, Object> requestBody) throws ListDEException{
        String id =(String) requestBody.get("id");
        Integer gain = (Integer) requestBody.get("gain");
        listDEService.gainPosition(id,gain);
        return new ResponseEntity<>(new ResponseDTO(200,"Las posiciones se reordenaron",null),HttpStatus.OK);
    }
    @PostMapping(path = "/loseposition")
    public ResponseEntity<ResponseDTO> loseposition(@RequestBody Map<String, Object> requestBody) throws ListDEException{
        String id =(String) requestBody.get("id");
        Integer gain = (Integer) requestBody.get("lose");
        listDEService.losePosition(id,gain);
        return new ResponseEntity<>(new ResponseDTO(200,"Las posiciones se reordenaron",null),HttpStatus.OK);
    }
    @GetMapping(path = "/petsbylocations")
    public ResponseEntity<ResponseDTO> getPetsByLocation() {
        List<PetByLocationDTO> petsByLocationDTOList = new ArrayList<>();
        for (Location loc : locationService.getLocations()) {
            int count = listDEService.getPets().getCountPetsByLocationCode(loc.getCode());
            if (count > 0) {
                petsByLocationDTOList.add(new PetByLocationDTO(loc, count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(200, petsByLocationDTOList, null), HttpStatus.OK);
    }

    @GetMapping(path = "/petsbydepartments")
    public ResponseEntity<ResponseDTO> getPetsByDepartments() {
        List<PetByLocationDTO> petsByLocationDTOList = new ArrayList<>();
        for (Location loc : locationService.getLocationsByCodeSize(5)) {
            int count = listDEService.getPets().getCountPetsByLocationCode(loc.getCode());
            if (count > 0) {
                petsByLocationDTOList.add(new PetByLocationDTO(loc, count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, petsByLocationDTOList, null), HttpStatus.OK);
    }

    @GetMapping(path = "/petsbylocationgenders/{age}")
    public ResponseEntity<ResponseDTO>getReportPetsLocationGenders(@PathVariable byte age){
        ReportPetsLocationGenderDTO report = new ReportPetsLocationGenderDTO(locationService.getLocationsByCodeSize(8));
        listDEService.getPets().getReportPetsByLocationGendersByAge(age,report);
        return new ResponseEntity<>(new ResponseDTO(200,report,null),HttpStatus.OK);
    }

}
