package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.exception.ListSEException;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.ListSE;
import co.edu.umanizales.tads.model.Node;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ListSEService {
    private ListSE kids;

    public ListSEService() {
        kids = new ListSE();
    }

    public void orderByGender() {
        kids.OrdenByGender();
    }
    public void invert(){

        kids.invert();
    }
public void orderBoysToStart() throws ListSEException {
        kids.orderBoysToStart();
}
    public void addToStart(Kid kid){
        kids.addToStart(kid);
    }

    public void getIntercalateKids() throws ListSEException {
        kids.getIntercalateKids();
    }
    public void PromAgesKids(){
        kids.PromAgesKids();
    }

    public void getCountKidByLocationCode(String code) throws ListSEException {
        kids.getCountKidByLocationCode(code);
    }
    public  void gainPosition(String id, int gain) throws ListSEException {
        kids.gainPosition(id, gain);
    }
    public void losePosition(String id, int lose) throws ListSEException {
        kids.losePosition(id, lose);
}
    public void reportKidsByAge(int ageMinima, int ageMaxima) throws ListSEException {
        kids.reportKidByAge(ageMinima, ageMaxima);
    }
    public void addByNameAtEnd(String initial) throws ListSEException {
        kids.addByNameAtEnd(initial);
    }

}
