package com.udacity.jdnd.course3.critter.Repository;

import com.udacity.jdnd.course3.critter.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.List;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    List<Employee> findAllByDaysAvailableContains(DayOfWeek dayOfWeek);

}
