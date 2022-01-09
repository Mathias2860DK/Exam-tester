package facades;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.BoatDTO;
import dtos.OwnerDTO;
import entities.Boat;
import entities.Harbour;
import entities.Owner;
import entities.User;
import security.errorhandling.AuthenticationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
    public String connectBoatToHarbour(String harbourId, String boatIdJSON) {
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

   /*     try {
            em.find(Harbour.class,harbourIdInt);
            em.getTransaction().begin();
            em.persist(boat);
            em.getTransaction().commit();
            return new BoatDTO(boat);
        } catch (RuntimeException e) {
            throw new WebApplicationException(e.getMessage());
        } finally {
            em.close();
        }*/

        return "";
    }
}
