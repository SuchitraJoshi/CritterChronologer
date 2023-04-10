package com.udacity.jdnd.course3.critter.Entity;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "pet")
@PrimaryKeyJoinColumn(name ="petId")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Access(AccessType.PROPERTY)
    @Column(name = "petId")
    private long id;
    @Column(name = "petType")
    @Enumerated(EnumType.STRING)
    private PetType type;
    @Nationalized
    @Column(name = "petName")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Customer.class, optional = false)
    @JoinColumn(name = "ownerId", referencedColumnName = "customerId")
    private Customer owner;

    @Column(name = "birthDate")
    private LocalDate birthDate;
    @Column(length = 2000)
    private String notes;

    public Pet(){

    }
    public Pet(PetType type, String name, Customer owner, LocalDate birthDate, String notes) {
        this.type = type;
        this.name = name;
        this.owner = owner;
        this.birthDate = birthDate;
        this.notes = notes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
