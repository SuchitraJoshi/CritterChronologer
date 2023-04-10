package com.udacity.jdnd.course3.critter.Controller;

import com.udacity.jdnd.course3.critter.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.DTO.ScheduleDTO;
import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Employee;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Entity.Schedule;
import com.udacity.jdnd.course3.critter.Service.CustomerService;
import com.udacity.jdnd.course3.critter.Service.EmployeeService;
import com.udacity.jdnd.course3.critter.Service.PetService;
import com.udacity.jdnd.course3.critter.Service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private PetService petService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule =  scheduleService.saveSchedule(convertScheduleDTOToEntity(scheduleDTO));
        return convertEntityToScheduleDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.listAllSchedules();
        return schedules.stream().map(this::convertEntityToScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {

        List<Schedule> petSchedules = scheduleService.listSchedulesOfPet(petId);
        return petSchedules.stream().map(this::convertEntityToScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> employeeSchedules = scheduleService.listSchedulesOfEmployee(employeeId);
        return employeeSchedules.stream().map(this::convertEntityToScheduleDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> customerSchedules = scheduleService.listSchedulesOfCustomer(customerId);
        return customerSchedules.stream().map(this::convertEntityToScheduleDTO).collect(Collectors.toList());
    }

    //-----------------------------------------------------------------------------
    private ScheduleDTO convertEntityToScheduleDTO(Schedule schedule){
        List<Long> petIds = null;
        List<Long> employeeIds = null;
        if (schedule.getPets()!=null) {
            petIds = schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        }
        if (schedule.getEmployees()!=null) {
            employeeIds = schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList());
        }
         ScheduleDTO scheduleDTO = new ScheduleDTO(schedule.getId(),employeeIds,petIds,schedule.getDate(),schedule.getActivities());
        return scheduleDTO;
    }

    private Schedule convertScheduleDTOToEntity(ScheduleDTO scheduleDTO){
        List<Pet> pets = null;
        List<Employee> employees = null;
        if (scheduleDTO.getPetIds()!= null) {
            pets = scheduleDTO.getPetIds().stream().map(petService::getPetById).collect(Collectors.toList());
        }
        if (scheduleDTO.getEmployeeIds()!= null) {
            employees = scheduleDTO.getEmployeeIds().stream().map(employeeService::findEmployee).collect(Collectors.toList());
        }
        Schedule schedule = new Schedule(employees,pets,scheduleDTO.getDate(),scheduleDTO.getActivities());
        return schedule;
    }
}
