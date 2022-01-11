package dtos;

import entities.Boat;
import entities.Harbour;
import entities.Owner;

import java.util.ArrayList;
import java.util.List;

public class AllInfoDTO {
    private BoatDTO boat;
    private List<OwnerDTO> owners = new ArrayList<>();
    private HarbourDTO harbour;

 /*   private int boatId;
    private String boatBrand;
    private String boatMake;
    private String boatName;


    private int ownerId;
    private String ownerName;
    private String ownerAddress;
    private int ownerPhone;

    private int harbourId;
    private String harbourName;
    private String harbourAddress;
    private int harbourCapacity;*/



    public AllInfoDTO(BoatDTO boat, List<OwnerDTO> owners, HarbourDTO harbour) {
      this.boat = boat;
      this.harbour = harbour;

       this.owners = owners;

    }


}
