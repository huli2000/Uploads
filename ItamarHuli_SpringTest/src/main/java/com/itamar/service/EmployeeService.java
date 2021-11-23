package com.itamar.service;

import java.util.List;
import java.util.Optional;

import com.itamar.entities.Employee;
import com.itamar.rest.controller.ex.EmployeeBadRequestException;

public interface EmployeeService {
    /**
     * Add new employee to the data-source if not exist
     *
     * @param employee The employee to add
     * @return The employee with the given id
     */
    Optional<Employee> insertEmployee(Employee employee) throws EmployeeBadRequestException;

    /**
     * Get employee by id
     *
     * @param id The id of the employee
     * @return Employee with the id
     */
    Optional<Employee> getEmployee(Long id);

    /**
     * Get all employees in the data-source
     *
     * @return List of employees
     */
    List<Employee> getEmployees();

    /**
     * Get employee by given serial number
     *
     * @param number the serial number of the employee
     * @return Employee
     */
    Employee getEmployeeBySerialNumber(String number);

    /**
     * Get employee with serial number that's ends with given sub number
     *
     * @param subNumber the sub number that's the serial number ends with
     * @return Employee
     */
    Employee getEmployeeBySerialNumberEndsWith(String subNumber);

    /**
     * Get all employees that's start date is bigger than 10 years from now
     *
     * @return List of Employees
     */
    List<Employee> getVeteranEmployees();
}
