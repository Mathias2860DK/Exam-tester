package dtos;

import entities.Harbour;

import javax.persistence.Column;

public class HarbourDTO {

    private String address;
    private String name;
    private int capacity;

    public HarbourDTO(Harbour harbour) {
        this.address = harbour.getAddress();
        this.name = harbour.getName();
        this.capacity = harbour.getCapacity();
    }
}
