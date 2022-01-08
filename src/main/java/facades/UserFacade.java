package facades;

import dtos.OwnerDTO;
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
