package facades;

import dtos.OwnerDTO;
import entities.Boat;
import entities.Owner;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.WebApplicationException;

import security.errorhandling.AuthenticationException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    //US3
    public List<OwnerDTO> getAllOwnersById(String id){
        int idInt = Integer.parseInt(id);
        EntityManager em = emf.createEntityManager();
        Boat boat;
        try {
            boat = em.find(Boat.class, idInt);
            if (boat == null) {
                throw new WebApplicationException("No boats found");
            }
        } finally {
            em.close();
        }
        List<Owner> owners = boat.getOwners();
        List<OwnerDTO> ownerDTOS = new ArrayList<>();
        for (Owner owner: owners) {
            OwnerDTO ownerDTO = new OwnerDTO(owner);
            ownerDTOS.add(ownerDTO);
        }
return ownerDTOS;
    }
    public List<OwnerDTO> getAllOwners(){
        EntityManager em = emf.createEntityManager();
        List<Owner> owners = em.createQuery("select o FROM Owner o",Owner.class).getResultList();
        if (owners == null){
            throw new WebApplicationException("No owners where found",404);
        }
        List<OwnerDTO> ownerDTOS = new ArrayList<>();
        for (Owner owner : owners) {
            ownerDTOS.add(new OwnerDTO(owner));
        }
    return ownerDTOS;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

}
