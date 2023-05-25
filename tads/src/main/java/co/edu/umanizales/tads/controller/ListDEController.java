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
        return new ResponseEntity<>(new ResponseDTO(200, listDEService.getPets().getPetss(), null), HttpStatus.OK);
    }

    @GetMapping("/invert")
    public ResponseEntity<ResponseDTO> invert() {
        listDEService.invert();
        return new ResponseEntity<>(new ResponseDTO(200, "Se ha invertido la lista", null), HttpStatus.OK);

    }

    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() {
        listDEService.getPets().changeExtremes();
        return new ResponseEntity<>(new ResponseDTO(200, "Se han intercambiado los extremos", null), HttpStatus.OK);
    }

    @PostMapping(path = "/addtostartpet")
    public ResponseEntity<ResponseDTO> addToStartPet(@RequestBody Pet pet) {
        listDEService.getPets().addToStartPet(pet);
        return new ResponseEntity<>(new ResponseDTO(200, "Se agregaron los pets al incio", null), HttpStatus.OK);
    }

    @GetMapping(path = "/intercalatepets")
    public ResponseEntity<ResponseDTO> IntercalatePets() throws ListDEException {
        listDEService.getIntercalatePets();
        return new ResponseEntity<>(new ResponseDTO(200, "Se han intercalado los pets", null), HttpStatus.OK);
    }

    @GetMapping(path = "/promagespets")
    public ResponseEntity<ResponseDTO> promAgesPets() {
        listDEService.PromAgesPets();
        return new ResponseEntity<>(new ResponseDTO(200, "El promedio de los pets", null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addPet(@RequestBody PetDTO petDTO) {
        Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(404, "La ubicación no existe", null), HttpStatus.OK);
        }
        try {
            listDEService.getPets().addPet(new Pet(petDTO.getName(), petDTO.getGender(), petDTO.getAge(), petDTO.getIdentification(), location, petDTO.isDirty()));

        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(409, e.getMessage(), null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(200, "Se ha adicionado el pet", null), HttpStatus.OK
        );
    }

    @GetMapping(path = "/removepet/{age}")
    public ResponseEntity<ResponseDTO> removepetbyage(@PathVariable byte age) {
        try {
            listDEService.removePetByAge(age);

        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(200, e.getMessage(), null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(200, "pet eliminada", null), HttpStatus.OK);
    }

    @GetMapping(path = "/getcountkidbylocationcode")
    public ResponseEntity<ResponseDTO> getcountkidbylocationcode(String code) throws ListDEException {
        listDEService.getCountPetByLocationCode(code);
        return new ResponseEntity<>(new ResponseDTO(200, "Se obtuvo la localizacion por el codigo", null), HttpStatus.OK);
    }

    @GetMapping(path = "/orderbygender")
    public ResponseEntity<ResponseDTO> orderbygender() throws ListSEException {
        listDEService.orderByGender();
        return new ResponseEntity<>(new ResponseDTO(200, "pets ordenados", null), HttpStatus.OK);
    }

    @PostMapping(path = "/gainposition")
    public ResponseEntity<ResponseDTO> gainposition(@RequestBody Map<String, Object> requestBody) {
        String id = (String) requestBody.get("id");
        Integer gain = (Integer) requestBody.get("gain");
        try {
            listDEService.gainPosition(id, gain);
            return new ResponseEntity<>(new ResponseDTO(200, "El pet a ganado posiciones", null), HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(409, e.getMessage(), null), HttpStatus.CONFLICT);

        }

    }

    @PostMapping(path = "/loseposition")
    public ResponseEntity<ResponseDTO> loseposition(@RequestBody Map<String, Object> requestBody){
        String id =(String) requestBody.get("id");
        Integer gain = (Integer) requestBody.get("lose");
        try {
            listDEService.losePosition(id,gain);
            return new ResponseEntity<>(new ResponseDTO(200,"El pet a perdido posiciones",null),HttpStatus.OK);
        } catch (ListDEException e) {
            return new ResponseEntity<>(new ResponseDTO(409,e.getMessage(),null),HttpStatus.CONFLICT);
        }

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
    @GetMapping(path = "/removeKamicase/{id}")
    public ResponseEntity<ResponseDTO> removeKamicase(String id) {
        listDEService.removeKamicase(id);
        return new ResponseEntity<>(new ResponseDTO(200,"Se ha eliminado el pet",null),HttpStatus.OK);
    }

}
