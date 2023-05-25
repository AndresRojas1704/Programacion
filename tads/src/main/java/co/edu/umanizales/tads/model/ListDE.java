package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.controller.DTO.ReportPetsLocationGenderDTO;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.exception.ListSEException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListDE {
    private NodeDE head;
    private int size;

    //metodo para agregar pet
    public void addPet(Pet pet) throws ListDEException {
        if (head != null) {
            NodeDE temp = head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            //Parado en el último
            temp.setNext(new NodeDE(pet));
            temp.getNext().setPrevious(temp);
        } else {
            head = new NodeDE(pet);
        }
        size++;
    }

    //metodo para agregar al pet al inicio
    public void addToStartPet(Pet pet) {
        NodeDE newNode = new NodeDE(pet);
        if (this.head != null) {
            this.head.setPrevious(newNode);
            newNode.setNext(this.head);
        }
        this.head = newNode;
        size++;
    }

    // metodo para agregar en posicion al pet
    private void addPetXPos(Pet pet, int position) {
        if (head == null) {
            head = new NodeDE(pet);
        } else {
            NodeDE newNode = new NodeDE(pet);
            if (position == 1) {
                newNode.setNext(head);
                head.setPrevious(newNode);
                head = newNode;
            } else {
                NodeDE temp = head;
                int currentPosition = 1;
                while (temp.getNext() != null && currentPosition < position - 1) {
                    temp = temp.getNext();
                    currentPosition++;
                }
                newNode.setNext(temp.getNext());
                if (temp.getNext() != null) {
                    temp.getNext().setPrevious(newNode);
                }
                temp.setNext(newNode);
                newNode.setPrevious(temp);
            }
        }
    }

    // metodo para remover x id
    public void removePetById(String id) {
        if (head != null) {
            NodeDE temp = head;
            if (head.getData().getIdentification().equals(id)) {
                if (head.getNext() != null) {
                    temp.getNext().setPrevious(null);
                    head = head.getNext();
                } else {
                    head = null;
                }
            } else {
                while (temp != null) {
                    if (temp.getData().getIdentification().equals(id)) {
                        if (temp.getNext() != null) {
                            temp.getPrevious().setNext(temp.getNext());
                            temp.getNext().setPrevious(temp.getPrevious());
                        } else {
                            temp.getPrevious().setNext(null);
                            temp.setPrevious(null);
                        }
                    }
                    temp = temp.getNext();
                }
            }
        }
        size--;
    }

    public List<Pet> getPetss() {
        List<Pet> pets = new ArrayList<>();
        NodeDE temp = head;
        if (head != null) {
            while (temp != null) {
                pets.add(temp.getData());
                temp = temp.getNext();
            }
        }
        return pets;
    }

    //metodo para ordenar por genero
    public void OrdenByGender() {
        ListDE listDE1 = new ListDE();
        int sum = 0;
        NodeDE temp = head;
        while (temp != null && temp.getNext().getData().getGender() == 'F') {
            listDE1.addToStartPet(temp.getData());
            temp.getNext();
        }
        while (temp != null && temp.getNext().getData().getGender() == 'M') {
            sum = sum + 2;
            listDE1.addPetXPos(temp.getData(), sum);
            temp.getNext();
        }
        this.head = listDE1.getHead();
    }

    //metodo para obtener pet x id
    public Pet getPetById(String id) {
        NodeDE temp = head;
        if (head != null) {
            while (temp != null) {
                if (temp.getData().getIdentification().equals(id)) {
                    return temp.getData();
                }
                temp = temp.getNext();
            }

        }
        return null;
    }

    // metodo para post x id
    public int getPostbyId(String id) {
        NodeDE temp = head;
        int acum = 0;
        if (head != null) {
            while (temp != null && !temp.getData().getIdentification().equals(id)) {
                acum = acum + 1;
                temp.getNext();
                return acum;
            }
        }
        return acum;
    }

    // metodo para cambiar extremos
    public void changeExtremes() {
        if (this.head != null && this.head.getNext() != null) {
            NodeDE temp = this.head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            //temp esta en el ultimo
            Pet copy = this.head.getData();
            this.head.setData(temp.getData());
            temp.setData(copy);
        }
    }

    // metodo para ordenar pet por genenro
    public void OrdenPetByGender() {
        ListDE listDE1 = new ListDE();

        int sum = 0;
        NodeDE temp = head;
        while (temp != null && temp.getNext().getData().getGender() == 'F') {
            listDE1.addToStartPet(temp.getData());
            temp.getNext();
        }
        while (temp != null && temp.getNext().getData().getGender() == 'M') {
            sum = sum + 2;
            listDE1.addPetXPos(temp.getData(), sum);
            temp.getNext();
        }
        this.head = listDE1.getHead();
    }

    // metodo para invertir la lista
    public void invert() {
        if (this.head != null) {
            ListDE listCp = new ListDE();
            NodeDE temp = this.head;
            while (temp != null) {
                listCp.addToStartPet(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
            this.head.setPrevious(null);
            NodeDE current = this.head;
            while (current.getNext() != null) {
                current.getNext().setPrevious(current);
                current = current.getNext();
            }
        }
    }

    //metodo para intercalar pets
    public void getIntercalatePets() throws ListDEException {
        if (head != null) {
            ListDE listCpFemale = new ListDE();
            ListDE listCpMale = new ListDE();
            ListDE listCpFinal = new ListDE();
            NodeDE temp = head;
            while (temp != null) {
                if (temp.getData().getGender() == 'F') {
                    listCpFemale.addPet(temp.getData());
                } else {
                    listCpFemale.addPet(temp.getData());
                }
                temp = temp.getNext();
            }
            NodeDE tempM = listCpMale.getHead();
            NodeDE tempF = listCpFemale.getHead();
            while (tempM != null || tempF != null) {
                if (tempM != null) {
                    listCpFinal.addPet(tempM.getData());
                    tempM = tempM.getNext();
                }
                if (tempF != null) {
                    listCpFinal.addPet(tempF.getData());
                    tempF = tempF.getNext();
                }
            }
            head = listCpFinal.getHead();
        }
    }

    // metodo para remover pet x edad
    public void removePetByAge(byte age) throws ListDEException {
        if (this.head == null) {
            throw new ListDEException("No se puede borrar de la lista");
        }
        NodeDE temp = this.head;
        ListDE listDECp = new ListDE();
        while (temp != null) {
            if (temp.getData().getAge() != age) {
                listDECp.addToStartPet(temp.getData());
            }
            temp = temp.getNext();
        }
        this.head = listDECp.getHead();
    }

    // metodo para obtener el promedio de los pets
    public float PromAgesPets() {
        int sumAges = 0;
        int count = 0;
        if (head != null) {
            NodeDE temp = head;
            while (temp != null) {
                sumAges += temp.getData().getAge();
                count++;
                temp = temp.getNext();
            }
            return sumAges / (float) count;
        }
        return 0;
    }

    // Metodo para generar un reporte que me diga cuantos niños hay de cada ciudad
    public int getCountPetByLocationCode(String code) throws ListDEException {
        if (code == null || code.isEmpty()) {
            throw new ListDEException("El codigo de la ubicacion no puede ser nulo o vacio");

        }
        int count = 0;
        if (this.head != null) {
            NodeDE temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
        } else {
            return 0;
        }
        return count;
    }

    //Metodo para adelantar posiciones
    public void gainPosition(String id, int pos) throws ListDEException {

        NodeDE temp = head;
        int posList = 1;
        if (head != null) {
            while (temp != null && !temp.getData().getIdentification().equals(id)) {
                temp = temp.getNext();
                posList++;
            }
            // temp estar en el que hay adelantar

            if (temp == head) {
                throw new ListDEException("la cabeza no puede avanzar posiciones");
            } else {

                int posFinal = posList - pos;
                Pet petcop;
                if (posFinal >= 1) {
                    petcop = temp.getData();
                    removePetById(id);
                    addPetXPos(petcop, posFinal);
                    // correguir este if

                    //correguir el if de arriba
                } else if (posFinal == 1) {
                    Pet headCop = head.getData();
                    petcop = temp.getData();
                    removePetById(id);
                    addToStartPet(petcop);
                    addPetXPos(headCop, 2);
                } else {

                    throw new ListDEException("no puede avanzar");
                }
            }
        }
    }

    public void win(String id, int pos) {
        ListDE listCp = new ListDE();
        NodeDE temp = this.head;
        int cont = 1;
        if (this.head != null) {
            while (temp.getNext() != null) {
                if (temp.getNext().getData().getIdentification().equals(id)) {
                    listCp.addToStartPet(temp.getNext().getData());
                    if (temp.getNext().getNext() != null) {
                        temp.getNext().getNext().setPrevious(temp);
                    }
                    temp.setNext(temp.getNext().getNext());
                    cont++;
                    break;
                }
                temp = temp.getNext();
                cont++;
            }
            int pos2 = cont - pos;
            NodeDE temp2 = listCp.getHead();
            if (pos2 == 1 || pos2 < 0) {
                addToStartPet(temp2.getData());
            } else {
                addPetXPos(temp2.getData(), pos2);
            }
        }
    }

    public NodeDE getNodeAtPosition(int position) {
        NodeDE currentNode = head;
        int currentPosition = 1;
        while (currentNode != null && currentPosition < position) {
            currentNode = currentNode.getNext();
            currentPosition++;
        }
        return currentNode;
    }


    //Metodo para perder posiciones
    public void losePosition(String id, int lose) throws ListDEException {
        NodeDE temp = this.head;
        int sum = 0;
        ListDE listDECp = new ListDE();
        if (head != null) ;
        while (temp != null) {
            if (!temp.getData().getIdentification().equals(id)) {
                listDECp.addPet(temp.getData());
                temp = temp.getNext();
            } else {
                temp = temp.getNext();
            }

        }
        sum = lose + getPostbyId(id);
        listDECp.addPetXPos(getPetById(id), sum);
        this.head = listDECp.getHead();

    }

    //Metodo para obtener un reporte de los pets x edad
    public void reportPetByAge(int ageMinima, int ageMaxima) throws ListDEException {
        if (head != null) {
            NodeDE temp = head;
            ListDE newList = new ListDE();
            while (temp != null) {
                if (ageMinima >= temp.getData().getAge() && ageMaxima <= temp.getData().getAge()) {
                    newList.addPet(temp.getData());
                }
                temp = temp.getNext();
            }
        }
    }

    //metodo para enviar al final de la lista a los niños que su nombre inicie con una letra dada
    public void addByNameAtEnd(String initial) throws ListDEException {
        ListDE newListDE = new ListDE();
        if (head != null) {
            NodeDE temp = head;
            while (temp != null) {
                if (temp.getData().getName().startsWith(initial)) {
                    newListDE.addPet(temp.getData());

                } else {
                    newListDE.addToStartPet(temp.getData());
                }
                temp = temp.getNext();
            }
            head = newListDE.head;
        }
    }

    //metodo para contar pets por la location code
    public int getCountPetsByLocationCode(String code) {
        int count = 0;
        if (this.head != null) {
            NodeDE temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    //metodo para obtener la location por departamento
    public int getPetsDepartmentsByLocationCode(String code) {
        int count = 0;
        if (this.head != null) {
            NodeDE temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().contains(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    //metodo para obtener un reporte de pets por location genero x age
    public void getReportPetsByLocationGendersByAge(byte age, ReportPetsLocationGenderDTO report) {
        if (head != null) {
            NodeDE temp = this.head;
            while (temp != null) {
                if (temp.getData().getAge() > age) {
                    report.updateQuantity(temp.getData().getLocation().getName(), temp.getData().getGender());
                }
                temp = temp.getNext();
            }
        }
    }

    /*
    metodo para remover x id
    1. recorremos la lista
    3. creamos un temporal
    2. llamamos a un temporal para que nos ayude a recorrer cada integrante de la lista
    3. entonces el temporal debe pararse en el que va a agarrar y con un set.previous que agarre el de atras y un get.next.get.next para que no tenga de donde agarrarse
    5. ahi ya estaria eliminando a lo que tenga, ya que el objeto en la lista tendria que agarrar el previo y los 2 siguientes pero el se eliminaria ya q no podria agarrarse del siguiente.
    6. para saber si el niño no esta en el ultimo tendremos que decir si temp. siguiente es igual igual a null entre y temp agarre el previous en y despues de estar en el previous coloqueme en el siguiente y despues la cabeza va a a ser el siguiente
     sino el temp agarre el previous y coloquemelo en el siguiente dentro de este llame al temp . siguiente y despues temp obtenga el siguiente, y despues de estar coloqueme en el anterior y dentro de este llamo al temp . obtenga el anterior
    7. entonces asi ya si el niño esta en el ultimo este se eliminaria

     */

    public void removeKamicase(String id) {
        if (head != null) {
            NodeDE temp = head;
            if (head.getData().getIdentification().equals(id)) {
                temp.getNext().setPrevious(null);
                head = head.getNext();
            } else {
                while (!temp.getData().getIdentification().equals(id)) {
                    temp = temp.getNext();
                }
                if (temp.getNext() == null) {
                    temp.getPrevious().setNext(null);
                    head = head.getNext();
                } else {
                    temp.getPrevious().setNext(temp.getNext());
                    temp.getNext().setPrevious(temp.getPrevious());
                }
            }
        }
        size--;
    }

}
