package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.controller.DTO.ReportKidsLocationGenderDTO;
import co.edu.umanizales.tads.exception.ListDEException;
import lombok.Data;

@Data
public class ListDE {
private NodeDE head;
private int size;

//metodo para agregar pet
    public void addPet(Pet pet) throws ListDEException
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
    //metodo para agregar al pet al inicio
    public void addToStartPet(Pet pet) {
        NodeDE temp = null;
        if (head != null) {
            head = new NodeDE(pet);

        } else {
            temp = new NodeDE(pet);
            temp.setNext(head);
        }
        head.setPrevious(temp);
        head = temp;
    }
// metodo para agregar en posicion al pet
    public void addPetXPos(Pet pet, int pos) {
        if (head != null) {
            NodeDE temp = head;
            int contador = 0;
            while (contador < pos-2) {
                temp = temp.getNext();
                contador = contador+1;
            }
            NodeDE newNode = new NodeDE(pet);
            newNode.setNext(temp.getNext());
            temp.setNext(newNode);
        } else {
            head = new NodeDE(pet);
        }
    }
    // metodo para remover x id
    public void removePetById(String id) {
        if (head != null) {
            NodeDE temp = head;
            if (head.getData().getIdentification().equals(id)) {
                head = head.getNext();
                head.setPrevious(null);
            } else {
                while (!temp.getNext().getData().getIdentification().equals(id)) {
                    temp = temp.getNext();
                }
                temp.getNext().getNext().setPrevious(temp);
                temp.setNext(temp.getNext().getNext());
            }
        }
        size--;
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
                if(temp.getData().getIdentification().equals(id)) {
                    Pet pet = new Pet(temp.getData().getIdentification(), temp.getData().getGender(), temp.getData().getAge(), temp.getData().getIdentification(), temp.getData().getLocation());
                    return pet;
                }
                temp.getData();
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
            while(temp.getNext() != null) {
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
            while(current.getNext() !=null){
                current.getNext().setPrevious(current);
                current  = current.getNext();
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
    public void removePetByAge(int age) {
        if (head != null) {
            NodeDE temp = head;
            while (temp != null) {
                if (temp.getData().getAge() == age) {
                    if(temp.getPrevious() == null ){
                        head = temp.getNext();
                        if(head !=null){
                            head.setPrevious(null);
                        }
                    }else {
                        temp.getPrevious().setNext(temp.getNext());
                        if(temp.getNext() !=null) {
                            temp.getNext().setPrevious(temp.getPrevious());
                        }
                    }
                }
                temp = temp.getNext();
            }
        }
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
    public void gainPosition(String id, int gain) throws ListDEException {
        NodeDE temp = head;
        gain = 0;
        int sum = 0;
        ListDE listDECp = new ListDE();
        if (head != null) {
            while (temp != null) {
                if (!temp.getData().getIdentification().equals(id)) {
                    listDECp.addPet(temp.getData());
                    temp.getNext();
                } else {
                    temp = temp.getNext();
                }
            }
        }
        sum = getPostbyId(id) - gain;
        listDECp.addPetXPos(getPetById(id), sum);
        this.head = listDECp.getHead();
    }

    //Metodo para perder posiciones
    public void losePosition(String id, int lose) throws ListDEException {
        NodeDE temp = head;
        lose = 0;
        int sum = 0;
        ListDE listDECp = new ListDE();
        if (head != null) {
            while (temp != null) {
                if (!temp.getData().getIdentification().equals(id)) {
                    listDECp.addPet(temp.getData());
                    temp.getNext();
                } else {
                    temp = temp.getNext();
                }
            }
        }
        sum = getPostbyId(id) + lose;
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
    public void orderByFirstLetter(String letter) throws ListDEException {
        ListDE listDE = new ListDE();
        if (head != null) {
            NodeDE temp = head;
            while (temp.getData() != null) {
                if (temp.getData().getName().startsWith(letter)) {
                    listDE.addPet(temp.getData());

                } else {
                    listDE.addToStartPet(temp.getData());
                }
                temp = temp.getNext();
            }
        }
    }

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

    public void getReportPetsByLocationGendersByAge(byte age, ReportKidsLocationGenderDTO report) {
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

}
