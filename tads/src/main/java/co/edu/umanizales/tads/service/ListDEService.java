package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.model.ListDE;
import co.edu.umanizales.tads.model.Pet;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ListDEService {
    private ListDE pets;

    public ListDEService(){
        pets = new ListDE();
    }


    public void orderByGender() {
        pets.OrdenByGender();
    }
    public void invert(){
        pets.invert();
    }

    public void getIntercalatePets() throws ListDEException {
        pets.getIntercalatePets();
    }

    public void removePetByAge(byte age) throws ListDEException {
        pets.removePetByAge(age);
    }

    public void PromAgesPets() {
        pets.PromAgesPets();
    }
    public void getCountPetByLocationCode(String code) throws ListDEException {
        pets.getCountPetByLocationCode(code);
    }
    public void gainPosition(String id, int gain) throws ListDEException {
        pets.gainPosition(id, gain);
    }
    public void losePosition(String id, int lose) throws ListDEException{
        pets.losePosition(id, lose);
    }
    public void reportPetByAge(int ageMinima, int ageMaxima) throws ListDEException {
        pets.reportPetByAge(ageMinima,ageMaxima);
    }
    public void addByNameAtEnd(String initial) throws ListDEException {
        pets.addByNameAtEnd(initial);
    }
    public void removeKamicase(String id){
    pets.removeKamicase(id);
    }
    public void win(String id, int pos) {
        pets.win(id,pos);
    }
}
