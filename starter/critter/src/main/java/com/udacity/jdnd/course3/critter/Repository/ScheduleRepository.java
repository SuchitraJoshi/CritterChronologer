package com.udacity.jdnd.course3.critter.Repository;

import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Employee;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    List<Schedule> findSchedulesByPets(Pet pet);


    List<Schedule> findSchedulesByEmployees(Employee employee);
    List<Schedule> findSchedulesByPetsIn(List<Pet> pets);
}
