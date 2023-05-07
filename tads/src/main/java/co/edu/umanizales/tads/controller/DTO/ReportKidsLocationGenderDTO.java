package co.edu.umanizales.tads.controller.DTO;

import co.edu.umanizales.tads.model.Location;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class ReportKidsLocationGenderDTO {
    private List<LocationGenderQuantityDTO> locationGenderQuantityDTOSS;

    public ReportKidsLocationGenderDTO(List<Location> cities) {
        locationGenderQuantityDTOSS = new ArrayList<>();
        for(Location location: cities) {
            locationGenderQuantityDTOSS.add(new LocationGenderQuantityDTO(location.getName()));
        }
    } // metodo actualizar


    public void updateQuantity(String city, char gender) {
        for(LocationGenderQuantityDTO loc:locationGenderQuantityDTOSS) {
            if(loc.getCity().equals(city)){
                for(GenderQuantityDTO genderDTO: loc.getGenders()){
                    if(genderDTO.getGender()==gender){
                        genderDTO.setQuantity(genderDTO.getQuantity()+1);
                        loc.setTotal(loc.getTotal()+1);
                        return;
                    }
                }
            }
        }
    }

}
