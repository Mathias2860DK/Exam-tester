package dtos;

import entities.Owner;

import javax.persistence.Column;

public class OwnerDTO {

   // private Integer id;
    private String name;
    private String address;
    private int phone;

    public OwnerDTO(String name, String address, int phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
    public OwnerDTO(Owner owner) {
        this.name = owner.getName();
        this.address = owner.getAddress();
        this.phone = owner.getPhone();
    }

}
