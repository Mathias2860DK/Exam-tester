package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Table(name = "owner")
@Entity
public class Owner {
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private int phone;

  /*  @JoinTable(name = "owner_boats", joinColumns = {
            @JoinColumn(name = "boat_id")}, inverseJoinColumns = {
            @JoinColumn(name = "owner_id")})*/
    @ManyToMany(mappedBy = "owners", cascade = CascadeType.PERSIST)
    private List<Boat> boats;

    public Owner(String name, String address, int phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.boats = new ArrayList<>();
    }
    public void addBoatToOwner(Boat boat){
        if (boat != null){
            this.boats.add(boat);
            boat.getOwners().add(this);
        }
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    //No args construcotr
    public Owner() {
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}