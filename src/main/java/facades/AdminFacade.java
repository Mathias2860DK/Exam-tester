package facades;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.*;
import entities.*;
import security.errorhandling.AuthenticationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lam@cphbusiness.dk
 */
public class AdminFacade {

    private static EntityManagerFactory emf;
    private static AdminFacade instance;

    private AdminFacade() {
    }

    /**
     * @param _emf
     * @return the instance of this facade.
     */
    public static AdminFacade getAdminFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new AdminFacade();
        }
        return instance;
    }

    public BoatDTO editBoat(String boatId, BoatDTO boatDTO){
        EntityManager em = emf.createEntityManager();
        int boatIdInt = Integer.parseInt(boatId);
        Boat boat = em.find(Boat.class,boatIdInt);

        boat.setBrand(boatDTO.getBrand());
        boat.setImage(boatDTO.getImage());
        boat.setMake(boatDTO.getMake());
        boat.setName(boatDTO.getName());



        em.getTransaction().begin();
        em.merge(boat);
        em.getTransaction().commit();

        return new BoatDTO(boat);
    }
    public List<BoatDTO> getAllBoats() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Boat> query = em.createQuery("SELECT b FROM Boat b", Boat.class);
        List<Boat> boats = query.getResultList();
        return BoatDTO.getDtos(boats);
    }
    public AllInfoDTO getAllInfoByBoatId(String boatId) {
        EntityManager em = emf.createEntityManager();
        int boatIdInt = Integer.parseInt(boatId);
        Boat boat = em.find(Boat.class,boatIdInt);
        List<Owner> owners = boat.getOwners();
        Harbour harbour = boat.getHarbour();
        BoatDTO boatDTO = new BoatDTO(boat);
        List<OwnerDTO> ownerDTOS = new ArrayList<>();
        for (Owner owner: owners) {
            ownerDTOS.add(new OwnerDTO(owner));
        }
        HarbourDTO harbourDTO = new HarbourDTO(harbour);


      //  TypedQuery<Harbour> query = em.createQuery("SELECT h FROM Harbour h WHERE h.name = :name", Harbour.class);


        AllInfoDTO allInfoDTO = new AllInfoDTO(boatDTO,ownerDTOS,harbourDTO);

        return allInfoDTO;
    }


    //US4
    public BoatDTO createBoat(String boatJSON) {
        EntityManager em = emf.createEntityManager();
        String brand;
        String make;
        String name;
        String image;
        try {
            JsonObject json = JsonParser.parseString(boatJSON).getAsJsonObject();
            brand = json.get("brand").getAsString();
            make = json.get("make").getAsString();
            name = json.get("name").getAsString();
            image = json.get("image").getAsString();
        } catch (Exception e) {
            // throw new WebApplicationException("Malformed JSON Suplied", 400);
            throw new WebApplicationException(e.getMessage(), 400);
        }
        try {
            Boat boat = new Boat(brand, make, name, image);
            em.getTransaction().begin();
            em.persist(boat);
            em.getTransaction().commit();
            return new BoatDTO(boat);
        } catch (RuntimeException e) {
            throw new WebApplicationException(e.getMessage());
        } finally {
            em.close();
        }
    }

    //US 5
    public BoatDTO connectBoatToHarbour(String harbourId, String boatIdJSON) {
        EntityManager em = emf.createEntityManager();
        System.out.println(harbourId);

        int harbourIdInt;
        int boatIdInt;

        try {
            harbourIdInt = Integer.parseInt(harbourId);
            JsonObject json = JsonParser.parseString(boatIdJSON).getAsJsonObject();
            boatIdInt = json.get("boatId").getAsInt();
        } catch (Exception e) {
        // throw new WebApplicationException("Malformed JSON Suplied", 400);
        throw new WebApplicationException(e.getMessage(), 400);
    }

        try {
            Harbour harbour = em.find(Harbour.class,harbourIdInt);
            Boat boat = em.find(Boat.class,boatIdInt);
            harbour.addBoat(boat);
            em.getTransaction().begin();
            em.persist(harbour);
            em.getTransaction().commit();
            return new BoatDTO(boat);
        } catch (RuntimeException e) {
            throw new WebApplicationException(e.getMessage());
        } finally {
            em.close();
        }

    }
}
