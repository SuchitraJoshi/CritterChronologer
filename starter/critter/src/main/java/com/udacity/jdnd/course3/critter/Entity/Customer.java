package com.udacity.jdnd.course3.critter.Entity;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "customer")
@PrimaryKeyJoinColumn(name ="customerId")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Access(AccessType.PROPERTY)
    @Column(name = "customerId")
    private long id;
    @Nationalized
    @Column(name = "customerName", nullable = false)
    private String name;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(length = 2000)
    private String notes;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "customer_pet", joinColumns = @JoinColumn(name="customerId"),inverseJoinColumns = @JoinColumn(name="petId") )
    private List<Pet> pets = new ArrayList<>();

    public Customer(){}

    public Customer(String name, String phoneNumber, String notes, List<Pet> pets) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        this.pets = pets;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }



}
