package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Table(name = "harbour")
@Entity
public class Harbour {
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

   @OneToMany(mappedBy = "harbour", cascade = CascadeType.PERSIST)
private List<Boat> boatList;

    @Column(name = "address")
    private String address;

    @Column(name = "name")
    private String name;

    @Column(name = "capacity")
    private int capacity;

    public Harbour(String address, String name, int capacity) {
        this.address = address;
        this.name = name;
        this.capacity = capacity;
        this.boatList = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Boat> getBoatList() {
        return boatList;
    }

    public void addBoat(Boat boat) {
        this.boatList.add(boat);
        if (boat != null){
            boat.setHarbour(this);
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Harbour() {
    }
}