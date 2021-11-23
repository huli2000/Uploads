package com.itamar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itamar.entities.Employee;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Employee findBySerialNumber(String serialNumber);

    Employee findBySerialNumberEndingWith(String serialNumber);

    List<Employee> findByStartDateBefore(LocalDate date);
}
