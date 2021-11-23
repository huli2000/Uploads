package com.itamar.rest.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.itamar.rest.ErrorResponse;
import com.itamar.rest.controller.ex.EmployeeBadRequestException;
import com.itamar.rest.controller.ex.EmployeeNotFoundException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEmployeeNotFound(EmployeeNotFoundException ex) {
        return ErrorResponse.from(ex);
    }

    @ExceptionHandler(EmployeeBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmployeeBadRequest(EmployeeBadRequestException ex) {
        return ErrorResponse.from(ex);
    }
}
