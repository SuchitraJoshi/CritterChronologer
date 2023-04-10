package com.udacity.jdnd.course3.critter.Controller;

import com.udacity.jdnd.course3.critter.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.DTO.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Employee;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Service.CustomerService;
import com.udacity.jdnd.course3.critter.Service.EmployeeService;
import com.udacity.jdnd.course3.critter.Service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
       Customer customer =  customerService.save(convertCustomerDTOToEntity(customerDTO));
       return convertEntityToCustomerDTO(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.listAllCustomers();
        return customers.stream().map(this::convertEntityToCustomerDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.findOwnerOfPet(petId);
        return convertEntityToCustomerDTO(customer);
    }

    //----------------------------------------------------------Employees-----------------------
    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee =  employeeService.save(convertEmployeeDTOToEntity(employeeDTO));
        return convertEntityToEmployeeDTO(employee);

    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.findEmployee(employeeId);
        return convertEntityToEmployeeDTO(employee);

    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employees = employeeService.getAvailableEmployeesForService(employeeDTO.getDate(),employeeDTO.getSkills());
        List<EmployeeDTO> availableEmployees = employees.stream().map(this::convertEntityToEmployeeDTO).collect(Collectors.toList());
        return availableEmployees;
    }

//-----------------------------------------------------------------------------
    private CustomerDTO convertEntityToCustomerDTO(Customer customer){
        List<Long> petIds = null;
        if (customer.getPets()!=null) {
             petIds = customer.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        }
        CustomerDTO customerDTO = new CustomerDTO(customer.getId(), customer.getName(), customer.getPhoneNumber(), customer.getNotes(), petIds);
        return customerDTO;
    }

    private Customer convertCustomerDTOToEntity(CustomerDTO customerDTO){
        List<Pet> pets = null;
        if (customerDTO.getPetIds()!= null) {
            pets = customerDTO.getPetIds().stream().map(petService::getPetById).collect(Collectors.toList());
        }
        Customer customer = new Customer(customerDTO.getName(), customerDTO.getPhoneNumber(), customerDTO.getNotes(), pets);
        return customer;
    }
    private EmployeeDTO convertEntityToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee,employeeDTO);
        return employeeDTO;
    }
    private Employee convertEmployeeDTOToEntity(EmployeeDTO employeeDTO){
        Employee employee = new Employee(employeeDTO.getName(), employeeDTO.getSkills(),employeeDTO.getDaysAvailable());
        return employee;
    }
}
