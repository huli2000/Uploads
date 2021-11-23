package com.itamar.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.itamar.entities.Employee;
import com.itamar.rest.controller.ex.EmployeeBadRequestException;
import com.itamar.rest.controller.ex.EmployeeNotFoundException;
import com.itamar.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) throws EmployeeBadRequestException {
        if (employee != null) {
            Optional<Employee> optEmployee = employeeService.insertEmployee(employee);
            if (optEmployee.isPresent()) {
                return ResponseEntity.ok(optEmployee.get());
            }
        }
        throw new EmployeeBadRequestException("Wrong arguments for Employee in body");
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) throws EmployeeNotFoundException {
        if (id > 0) {
            Optional<Employee> optEmployee = employeeService.getEmployee(id);
            if (optEmployee.isPresent()) {
                return ResponseEntity.ok(optEmployee.get());
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees() throws EmployeeNotFoundException{
        List<Employee> employees = employeeService.getEmployees();
        if (!employees.isEmpty()) {
            return ResponseEntity.ok(employees);
        }
       throw new EmployeeNotFoundException("Can't find any employees");
    }

    @GetMapping("/employee/serial-number/{serialNumber}")
    public ResponseEntity<Employee> getEmployeeBySerialNumber(@PathVariable String serialNumber)
            throws EmployeeNotFoundException{
        Employee employee = employeeService.getEmployeeBySerialNumber(serialNumber);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        }
        throw new EmployeeNotFoundException();
    }

    @GetMapping("/employee/serial-number/end-with/{subNumber}")
    public ResponseEntity<Employee> getEmployeeBySerialNumberEndsWith(@PathVariable String subNumber)
            throws EmployeeNotFoundException{
        Employee employee = employeeService.getEmployeeBySerialNumberEndsWith(subNumber);

        if (employee != null) {
            return ResponseEntity.ok(employee);
        }
        throw new EmployeeNotFoundException();
    }

    @GetMapping("/employee/veteran")
    public ResponseEntity<List<Employee>> getVeteranEmployees() throws EmployeeNotFoundException{
        List<Employee> veteranEmployees = employeeService.getVeteranEmployees();
        if (!veteranEmployees.isEmpty()) {
            return ResponseEntity.ok(veteranEmployees);
        }
        throw new EmployeeNotFoundException("Can't find any veteran employees");
    }
}
