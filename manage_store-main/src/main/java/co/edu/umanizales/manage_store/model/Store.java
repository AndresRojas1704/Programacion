package co.edu.umanizales.manage_store.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Store {
    private String code;
    private String name;

    public Store(String code, String name){
        this.code = code;
        this.name = name;
    }

    public Store() {

    }
}
