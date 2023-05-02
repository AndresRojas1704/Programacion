package co.edu.umanizales.tads.model;

import lombok.Data;

@Data
public class Node {

    private String head;

    private Kid data;

    private Node next;

    public Node(Kid data) {

        this.data = data;

    }

}
