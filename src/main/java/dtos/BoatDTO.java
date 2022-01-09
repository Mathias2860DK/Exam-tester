/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Boat;
import entities.RenameMe;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tha
 */
public class BoatDTO {

    private Integer id;
    private String brand;

    private String make;

    private String name;

    private String image;

    public static List<BoatDTO> getDtos(List<Boat> boats){
        List<BoatDTO> boatDTOS = new ArrayList();
        boats.forEach(boat->boatDTOS.add(new BoatDTO(boat)));
        return boatDTOS;
    }


    public BoatDTO(Boat boat) {
        if(boat.getId() != null)
            this.id = boat.getId();
    this.name = boat.getName();
    this.brand = boat.getBrand();
    this.image = boat.getImage();
    this.make = boat.getMake();
    }

    public Integer getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getMake() {
        return make;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
