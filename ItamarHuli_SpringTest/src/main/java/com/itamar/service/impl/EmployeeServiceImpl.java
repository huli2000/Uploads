package com.itamar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itamar.entities.Employee;
import com.itamar.repository.EmployeeRepo;
import com.itamar.rest.controller.ex.EmployeeBadRequestException;
import com.itamar.service.EmployeeService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public Optional<Employee> insertEmployee(Employee employee) throws EmployeeBadRequestException {
        if (employee != null) {
            List<Employee> employees = employeeRepo.findAll();
            if (employees.stream().noneMatch(e -> e.getSerialNumber().equals(employee.getSerialNumber()))) {
                return Optional.of(employeeRepo.save(employee));
            } else {
                throw new EmployeeBadRequestException("Employee already in data-source");
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Employee> getEmployee(Long id) {
        if (id > 0) {
            return employeeRepo.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public List<Employee> getEmployees() {
        return employeeRepo.findAll();
    }

    @Override
    public Employee getEmployeeBySerialNumber(String number) {
        return employeeRepo.findBySerialNumber(number);
    }

    @Override
    public Employee getEmployeeBySerialNumberEndsWith(String subNumber) {
        return employeeRepo.findBySerialNumberEndingWith(subNumber);
    }

    @Override
    public List<Employee> getVeteranEmployees() {
        return employeeRepo.findByStartDateBefore(LocalDate.now().minusYears(10));
    }
}
