package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AllInfoDTO;
import dtos.BoatDTO;
import dtos.OwnerDTO;
import facades.AdminFacade;
import facades.UserFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * @author lam@cphbusiness.dk
 */
@Path("admin")
public class AdminResource {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    private EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private final UserFacade userFacade = UserFacade.getUserFacade(EMF);
    private final AdminFacade adminFacade = AdminFacade.getAdminFacade(EMF);
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String getAllOwners() {
        List<OwnerDTO> ownerDTOS = userFacade.getAllOwners();
        return gson.toJson(ownerDTOS);
    }


    //US4 - TODO: Admin roles
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/createBoat")
    public String createBoat(String JSONboat){
        try {
            BoatDTO boatDTO = adminFacade.createBoat(JSONboat);
            return gson.toJson(boatDTO);
        }catch(WebApplicationException e){
            throw new WebApplicationException(e.getMessage());
        }
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAllBoats")
    public String getAllBoats() {
        List<BoatDTO> boats = adminFacade.getAllBoats();
        return gson.toJson(boats);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAllInfoByBoatId/{boatId}")
    public String getAllInfoByBoatId(@PathParam("boatId") String boatId){
        AllInfoDTO allInfoDTO = adminFacade.getAllInfoByBoatId(boatId);
        return gson.toJson(allInfoDTO);
    }

    //US 5
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/connectBoatToHarbour/{harbourId}")
    public String connectBoatToHarbour(@PathParam("harbourId") String harbourId, String boatId){
    adminFacade.connectBoatToHarbour(harbourId,boatId);
    return "";
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }
}