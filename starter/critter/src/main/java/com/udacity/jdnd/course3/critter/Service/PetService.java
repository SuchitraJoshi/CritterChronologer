package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.DTO.PetDTO;
import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;
    @Autowired
    CustomerRepository customerRepository;



    public List<Pet> listAllPets() {
        return petRepository.findAll();
    }

    public  List<Pet> listPetsByOwnerId(long ownerId) {

        return petRepository.findPetsByOwnerId(ownerId);
    }

    public  Pet insertOrUpdatePet(Pet pet) {
        Customer customer= pet.getOwner();
      pet = petRepository.saveAndFlush(pet);
            if(customer.getPets()== null){
                List<Pet> pets = new ArrayList<Pet>();
                customer.setPets(pets);
            }

            customer.getPets().add(pet);
            customerRepository.save(customer);
        return pet;
    }

    public Pet getPetById(long petId) {
        return petRepository.findById(petId).orElse(null);
    }
}
