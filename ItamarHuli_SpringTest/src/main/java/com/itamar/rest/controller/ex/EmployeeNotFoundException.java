package com.itamar.rest.controller.ex;

public class EmployeeNotFoundException extends Exception {
    public EmployeeNotFoundException() {
        super("Can't find employee with the given arguments");
    }
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
