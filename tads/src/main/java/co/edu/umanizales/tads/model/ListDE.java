package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.exception.ListSEException;
import lombok.Data;

@Data
public class ListDE {
private NodeDE head;
private int size;


    public void addPet(Pet pet) throws ListSEException
    {
        if(head!=null)
        {
            NodeDE temp = head;
            while(temp.getNext()!=null)
            {
                temp= temp.getNext();
            }
            //Parado en el último
            temp.setNext(new NodeDE(pet));
            temp.getNext().setPrevious(temp);
        }
        else
        {
            head = new NodeDE(pet);
        }
    }

}
