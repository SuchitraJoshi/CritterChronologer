package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PetRepository petRepository;

    public Customer save(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

    public List<Customer> listAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer findOwnerOfPet(long petId) {

        return petRepository.getOne(petId).getOwner();
    }
    public Customer getCustomerById (long customerId) {

        return customerRepository.getOne(customerId);
    }
}
