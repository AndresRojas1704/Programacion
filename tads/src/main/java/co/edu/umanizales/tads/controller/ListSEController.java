package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.exception.ListSEException;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.controller.DTO.*;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static co.edu.umanizales.tads.service.ListSEService.*;

@RestController
@RequestMapping(path = "/listse")
public class ListSEController {
    @Autowired
    private ListSEService listSEService;
    @Autowired
    private LocationService locationService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getKids() {
        return new ResponseEntity<>(new ResponseDTO(200, listSEService.getKids(), null), HttpStatus.OK);
    }

    @GetMapping(path =  "/invert")
    public ResponseEntity<ResponseDTO> invert() {
        listSEService.invert();
        return new ResponseEntity<>(new ResponseDTO(200, "Se ha invertido la lista", null), HttpStatus.OK);

    }

    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() {
        listSEService.getKids().changeExtremes();
        return new ResponseEntity<>(new ResponseDTO(200, "SE han intercambiado los extremos", null), HttpStatus.OK);
    }
@GetMapping(path ="/intercalatekids")
public ResponseEntity<ResponseDTO>IntercalateKids() throws ListSEException {
        listSEService.getIntercalateKids();
        return new ResponseEntity<>(new ResponseDTO(200,"Se ha intercalado los niños", null),HttpStatus.OK);
}
    @PostMapping
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO) {
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
        if (location == null) {
            return new ResponseEntity<>(new ResponseDTO(404, "La ubicación no existe", null), HttpStatus.OK);
        }
        try {
            listSEService.getKids().add(new Kid(kidDTO.getIdentification(), kidDTO.getName(), kidDTO.getAge(), kidDTO.getGender(), location));

        } catch (ListSEException e){
            return new ResponseEntity<>(new ResponseDTO(409,e.getMessage(),null),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(200,"Se ha adicionado el petacon",null),HttpStatus.OK);
    }

    @GetMapping(path = "/removekidbyage/{age}")
    public ResponseEntity<ResponseDTO>removeKidByAge(int age) {
        listSEService.getKids().removeKidByAge(age);
        return new ResponseEntity<>(new ResponseDTO(200,"Niño eliminado",null),HttpStatus.OK);
    }

@GetMapping (path = "/orderbygender")
    public ResponseEntity<ResponseDTO> orderbygender() throws ListSEException {
        listSEService.orderByGender();
        return new ResponseEntity<>(new ResponseDTO(200,"niños ordenados",null), HttpStatus.OK);
    }

    @GetMapping(path= "/kidtostart")
    public ResponseEntity<ResponseDTO> orderboystostart() throws ListSEException {
        listSEService.orderBoysToStart();
        return new ResponseEntity<>(new ResponseDTO(200, "Los niños al inicio y las niñas al final",null),HttpStatus.OK);
    }
    @GetMapping(path="/promageskids")
    public ResponseEntity<ResponseDTO> promAgesKids(){
        listSEService.PromAgesKids();
        return new ResponseEntity<>(new ResponseDTO(200, "El promedio de los niños", null),HttpStatus.OK);
    }

    @PostMapping(path = "/gainposition")
    public ResponseEntity<ResponseDTO> gainposition(@RequestBody Map<String, Object> requestBody) throws ListSEException{
        String id =(String) requestBody.get("id");
        Integer gain = (Integer) requestBody.get("gain");
        listSEService.gainPosition(id,gain);
        return new ResponseEntity<>(new ResponseDTO(200,"El niño gano posiciones",null),HttpStatus.OK);
    }
    @PostMapping(path = "/loseposition")
    public ResponseEntity<ResponseDTO> loseposition(@RequestBody Map<String, Object> requestBody) throws ListSEException{
        String id =(String) requestBody.get("id");
        Integer gain = (Integer) requestBody.get("lose");
        listSEService.losePosition(id,gain);
        return new ResponseEntity<>(new ResponseDTO(200,"El niño perdio posiciones",null),HttpStatus.OK);
    }

    @GetMapping(path = "/addbynameatend")
    public ResponseEntity<ResponseDTO>addByNameAtEnd(String initial) throws ListSEException {
        listSEService.addByNameAtEnd(initial);
        return new ResponseEntity<>(new ResponseDTO(200,"Ordenar por la primera letra", null),HttpStatus.OK);
    }
    @GetMapping(path="/removekidbyid")
    public ResponseEntity<ResponseDTO>removeKidById(String id) {
        listSEService.getKids().removeKidById(id);
        return new ResponseEntity<>(new ResponseDTO(200,"Se removio por id", null),HttpStatus.OK);
    }

    @GetMapping(path ="/reportkidbyage")
    public ResponseEntity<ResponseDTO>reportKidByAge(int ageMinima, int ageMaxima) throws ListSEException {
        listSEService.reportKidsByAge(ageMinima,ageMaxima);
        return new ResponseEntity<>(new ResponseDTO(200, "Reporte de niños por edad", null),HttpStatus.OK);
    }
    @GetMapping(path ="/getcountkidbylocationcode")
    public ResponseEntity<ResponseDTO>getcountkidbylocationcode(String code) throws ListSEException {
        listSEService.getCountKidByLocationCode(code);
        return new ResponseEntity<>(new ResponseDTO(200,"Se obtuvo la localizacion por el codigo",null),HttpStatus.OK);
    }
    @GetMapping(path ="/orderboystostart")
    public ResponseEntity<ResponseDTO>orderBoysToStart() throws ListSEException {
        listSEService.orderBoysToStart();
        return new ResponseEntity<>(new ResponseDTO(200,"Se ordenaron los niños al inicio",null),HttpStatus.OK);
    }

    @GetMapping(path = "/kidsbylocations")
    public ResponseEntity<ResponseDTO> getKidsByLocation() {
        List<KidByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for (Location loc : locationService.getLocations()) {
            int count = listSEService.getKids().getCountKidsByLocationCode(loc.getCode());
            if (count > 0) {
                kidsByLocationDTOList.add(new KidByLocationDTO(loc, count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(200, kidsByLocationDTOList, null), HttpStatus.OK);
    }

    @GetMapping(path = "/kidsbydepartments")
    public ResponseEntity<ResponseDTO> getKidsByDepartments() {
        List<KidByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for (Location loc : locationService.getLocationsByCodeSize(5)) {
            int count = listSEService.getKids().getDepartmentsByLocationCode(loc.getCode());
            if (count > 0) {
                kidsByLocationDTOList.add(new KidByLocationDTO(loc, count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200, kidsByLocationDTOList, null), HttpStatus.OK);
    }


    @GetMapping(path = "/kidsbylocationgenders/{age}")
public ResponseEntity<ResponseDTO>getReportKidsLocationGenders(@PathVariable byte age){
ReportKidsLocationGenderDTO report = new ReportKidsLocationGenderDTO(locationService.getLocationsByCodeSize(8));
listSEService.getKids().getReportKidsByLocationGendersByAge(age,report);
return new ResponseEntity<>(new ResponseDTO(200,report,null),HttpStatus.OK);
    }


}
