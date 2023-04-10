package com.udacity.jdnd.course3.critter.Controller;

import com.udacity.jdnd.course3.critter.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.DTO.PetDTO;
import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Service.CustomerService;
import com.udacity.jdnd.course3.critter.Service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet =  petService.insertOrUpdatePet(convertPetDTOToEntity(petDTO));
        return convertEntityToPetDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        return convertEntityToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets() {
        List<Pet> pets = petService.listAllPets();
        return pets.stream().map(this::convertEntityToPetDTO).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.listPetsByOwnerId(ownerId);
        return pets.stream().map(this::convertEntityToPetDTO).collect(Collectors.toList());
    }

    private PetDTO convertEntityToPetDTO(Pet pet){
        long ownerId = pet.getOwner().getId();
        PetDTO petDTO = new PetDTO(pet.getId(),pet.getType(),pet.getName(),ownerId,pet.getBirthDate(), pet.getNotes());
        return petDTO;
    }

    private Pet convertPetDTOToEntity(PetDTO petDTO){
        Customer owner = customerService.getCustomerById(petDTO.getOwnerId());
        Pet pet = new Pet(petDTO.getType(),petDTO.getName(),owner,petDTO.getBirthDate(),petDTO.getNotes());
//        pet.setType(petDTO.getType());
//        pet.setName(petDTO.getName());
//        pet.setOwner(owner);
//        pet.setBirthDate(petDTO.getBirthDate());
//        pet.setNotes(petDTO.getNotes());
        return pet;
    }

}