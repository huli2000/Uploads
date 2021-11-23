package com.itamar.service.login.ex;

/**
 * This file is a part of employeeAPI project.
 *
 
 * @version 1.0.0
 */
public class InvalidLoginException extends Exception {

    public InvalidLoginException() {
        super("Login attempt with invalid credentials!");
    }
}
