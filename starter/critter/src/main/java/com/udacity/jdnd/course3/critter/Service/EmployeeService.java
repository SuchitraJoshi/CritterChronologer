package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.Entity.DaysOfWeek;
import com.udacity.jdnd.course3.critter.Entity.Employee;
import com.udacity.jdnd.course3.critter.Entity.EmployeeSkill;
import com.udacity.jdnd.course3.critter.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee save(Employee employee) {

        return employeeRepository.saveAndFlush(employee);
    }

    public Employee findEmployee(long employeeId) {

        return employeeRepository.findById(employeeId).orElse(null);
    }


    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> getAvailableEmployeesForService(LocalDate date, Set<EmployeeSkill> skills) {
        List<Employee> presentEmployees = employeeRepository.findAllByDaysAvailableContains(date.getDayOfWeek());
        return presentEmployees.stream().filter(employee -> employee.getSkills().containsAll(skills)).collect(Collectors.toList());
    }
}
