package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Employee;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Entity.Schedule;
import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.Repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import com.udacity.jdnd.course3.critter.Repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.CustomSQLErrorCodesTranslation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    PetRepository petRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CustomerRepository customerRepository;
    public Schedule saveSchedule(Schedule schedule) {
       return scheduleRepository.saveAndFlush(schedule);
    }

    public List<Schedule> listAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> listSchedulesOfPet(long petId) {
        Pet pet = petRepository.getOne(petId);
        return scheduleRepository.findSchedulesByPets(pet);
    }

    public List<Schedule> listSchedulesOfEmployee(long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        return scheduleRepository.findSchedulesByEmployees(employee);

    }

    public List<Schedule> listSchedulesOfCustomer(long customerId) {
        Customer customer = customerRepository.getOne(customerId);
        List<Schedule> customerSchedule =scheduleRepository.findSchedulesByPetsIn(customer.getPets());
        return customerSchedule;
    }
}
