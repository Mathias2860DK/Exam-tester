package utils;


import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestUsers {

  public static void main(String[] args) {

  }
  public static void addBoatToOwner() {
    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
    Boat boat = new Boat("Volvo","MODEL 5","Speed boat","wwww.img.com");
    Owner owner1 = new Owner("I own a boat","test1",1111);
    Owner owner2 = new Owner("I also own a boat","test2",2222);
    owner1.addBoatToOwner(boat);
    owner2.addBoatToOwner(boat);
    em.getTransaction().begin();

    em.persist(owner1);
    em.persist(owner2);
    em.getTransaction().commit();
  }


  public static void setupTestOwners(){
    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();

    Owner owner1 = new Owner("test1","test1",1111);
    Owner owner2 = new Owner("test2","test2",2222);
    Owner owner3 = new Owner("test3","test3",3333);
    em.getTransaction().begin();
    em.persist(owner1);
    em.persist(owner2);
    em.persist(owner3);
    em.getTransaction().commit();
  }
  public static void populateTestUsers(){
    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();

    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
    // CHANGE the three passwords below, before you uncomment and execute the code below
    // Also, either delete this file, when users are created or rename and add to .gitignore
    // Whatever you do DO NOT COMMIT and PUSH with the real passwords

    User user = new User("user", "test1");
    User admin = new User("admin", "test2");
    User both = new User("user_admin", "test3");


    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
      throw new UnsupportedOperationException("You have not changed the passwords");

    em.getTransaction().begin();
    Role userRole = new Role("user");
    Role adminRole = new Role("admin");
    user.addRole(userRole);
    admin.addRole(adminRole);
    both.addRole(userRole);
    both.addRole(adminRole);
    em.persist(userRole);
    em.persist(adminRole);
    em.persist(user);
    em.persist(admin);
    em.persist(both);
    em.getTransaction().commit();
    System.out.println("PW: " + user.getUserPass());
    System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
    System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
    System.out.println("Created TEST Users");

    //Adding harbours
    Harbour harbour1 = new Harbour("Vedbæk vej","Vedbæk havn",300);
    Harbour harbour2 = new Harbour("Amager vej","Amger havn",500);
    Harbour harbour3= new Harbour("Sønderborg vej","Sønderborg havn",1000);

    //adding boats
    Boat boat1 = new Boat("Volvo","make","speedboat","www.img.com");
    Boat boat2 = new Boat("Volvo","make","speedboat","www.img.com");

    //Connecting boat to harbour
    harbour1.addBoat(boat1);
    em.getTransaction().begin();
    em.persist(harbour1);
    em.persist(harbour2);
    em.persist(harbour3);
    em.getTransaction().commit();

  }

}
