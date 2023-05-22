package co.edu.umanizales.tads.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class ListDECircular {
    private NodeDE head;
    private int size;



    //Metodo para agregar en circular
    /*
    1. miramos si la cabeza es diferente de null
    2.si es asi creamos una cabeza y metemos un pet
    3.despues comprobamos que la cabeza sea el siguiente y el anterior
    4. sino creamos un temporal que sea el anterior de la cabeza
    5. despues creamos un nodo para insertar un nuevo pet
    6. ponemos al temp a ponerlo en el siguiente para agregar el pet a la lista
    7. despues comprobamos los enlaces de los pets en el siguiente y el previo
    8. y por ultimo pasamos la cabeza al anterior
     */
    public void addPet(Pet pet) {
        if (pet == null) {
            return;
        }

        NodeDE newNode = new NodeDE(pet);

        if (head == null) {
            head = newNode;
            head.setNext(newNode);
            head.setPrevious(newNode);
        } else {
            NodeDE lastNode = head.getPrevious();
            newNode.setNext(head);
            newNode.setPrevious(lastNode);
            lastNode.setNext(newNode);
            head.setPrevious(newNode);
        }

        size++;
    }

    public List<Pet> getPets(){
        List<Pet> pets = new ArrayList<>();
        NodeDE temp =head;
        if(head!=null){
            while(temp.getNext() != head && temp.getNext() !=null) {
                pets.add(temp.getData());
                temp = temp.getNext();
            }
        }
        pets.add(temp.getData());
        return pets;
    }

    //Metodo para agregar al inicio en circular
    /*
    1. miramos si la cabeza es diferente de null
    2. si es asi creamos una cabeza y metemos un pet
    3. comprobamos que los enlaces esten bien el anterior y el siguiente
    4. si no creamos un temporal y lo ponemos en el anterior de la cabeza
    5. ademas creamos un nodo insertar para meter la nueva mascota
    6. ponemos el temp en el anterior del temp
    7. ademas  ponemos despues de la cabeza
    8. miramos si la cabeza es el nuevo pet
    9. y por ultimo le decimos que lac cabeza sea el previo
    10. y asi se adiciona un pet al incio de la lista

     */
    public void addToStart(Pet pet) {
        if (head == null) {
            addPet(pet);
        }else{
            NodeDE newNode = new NodeDE(pet);
            NodeDE temp = head.getPrevious();
            temp.setNext(newNode);
            newNode.setPrevious(temp);
            newNode.setNext(head);
            head.setPrevious(newNode);
            head=newNode;
            size++;
        }
    }
//Metodo para agregar al final
    /*
    1. primero comprobamos que la cabeza sea diferente a null
    2. si es diferente creamos un nodo para meter un pet
    3. ponemos que el siguiente sea la cabeza
    4. miramos al anterior y ponemos la cabeza en el anterior
    5. entonces obtenemos el anterior de la cabeza y el siguiente del nuevo nodo
    6. para que el previo de la cabeza sea el nuevo nodo
    7. sino creamos un nueva cabeza con un pet
    8. y que el siguiente agarre la cabeza
    9  y el anterior agarre a la cabeza
     */
    public void addToFinal(Pet pet) {
        if (head == null) {
        addPet(pet);
        } else {
        NodeDE newNode = new NodeDE(pet);
        NodeDE lastNode = head.getPrevious();
        lastNode.setNext(newNode);
        newNode.setPrevious(lastNode);
        newNode.setNext(head);
        head.setPrevious(newNode);
        size++;
    }
}

    //metodo para agregar x posicion circular
    /*
    para agregar en posicion necesitamos que la cabeza no sea null
    despues creamos un nuevo nodo y metemos un pet
    si la posicion menor o igual a 1
    entre y agarre el seguiente de la cabeza y el el anterior, sea el anterior de la cabeza
    cuando este e la cabeza agarre el anterior de la siguiente y vuelvalo el del nuevo nodo
    asi la cabeza sera el nuevo nodo
    sino cree un temporar que se pare en la cabeza
    y cree un contador que empiece en 1
    entonces mientras el contador sea menor a la posicion - 1 y temporal sea el siguiente tiene que ser diferente a la cabeza
    temp sera el siguiente
    y el contador aumentara
    ademas que el nuevo agarre el siguiente del temp siguiente
    y del anterior de temp
    para que temp pase al siguiente y al anterior del nuevo nodo
    y el siguiente sea del nuevo nodo
    si no cree un pet
    y este sea el siguiente de la cabeza
    y el anterior de la cabeza

     */
    public void addXPositionPet(Pet pet, int pos) {
        if (pos == 1) {
            addToStart(pet);
        } else {
            NodeDE temp = head;
            int count = 1;

            while (count < pos -1) {
                temp = temp.getNext();
                count++;
            }
            NodeDE newNode = new NodeDE(pet);
            newNode.setNext(temp.getNext());
            newNode.setPrevious(temp);
            temp.getNext().setPrevious(newNode);
            temp.setNext(newNode);
            size++;
        }
    }


    // Metodo para bañar perro
    /*
    Primero creamos una variable inicio la cual va a ser de tipo character para poder tomar al perro que se quiera bañar
    despues creamos un temporal el cual vaya a la cabeza
    si temporal es igual null retorno 0 y esto significa que no hay perros
    si inicio es diferente a derecha y si inicio es diferente a izquierda entonces retorno 0 esto lo hacemos para revisar la lista de derecha a izquierda
    despues creamos un random el cual nos va a servir para moverse a cualquier posicion de la lista despues creamos un num el cual nos va a mirar cuando el tamaño aumente en 1
    si el num es igual a 1
    empiece a la derecha
    y mientras el contador sea diferente al numero
    el temporal pase al siguiente
    y el contador aumente en 1
    sino mientras el contador sea diferente al num
    temporal vaya al previo
    y el contador aumente
    y por ultimo si el perro esta sucio o no para poder bañarlo
    y sino retorne 0

     */
    public int takeShowerPet(char letter) {
        char start = Character.toLowerCase(letter);
        NodeDE temp = head;

        if (temp == null) {
            return 0;
        }

        if (start != 'd' && start != 'i') {
            return 0;
        }

        Random rand = new Random();
        int num = rand.nextInt(size) + 1;
        if (num == 1) {
            if (temp.getData().isDirty()) {
                temp.getData().setDirty(false);
            } else {

                return 0;
            }
        } else {
            int count = 1;
            if (start == 'd') {
                while (count != num) {
                    temp = temp.getNext();
                    count++;
                }
            } else {
                while (count != num) {
                    temp = temp.getPrevious();
                    count++;
                }
            }

            if (temp.getData().isDirty()) {
                temp.getData().setDirty(false);
            } else {

                return 0;
            }
        }

        return num;
    }

}
