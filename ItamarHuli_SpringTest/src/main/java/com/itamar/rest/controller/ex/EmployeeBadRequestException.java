package com.itamar.rest.controller.ex;

public class EmployeeBadRequestException extends Exception {
    public EmployeeBadRequestException() {
		super();
	}

	public EmployeeBadRequestException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EmployeeBadRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmployeeBadRequestException(Throwable cause) {
		super(cause);
	}

	public EmployeeBadRequestException(String message) {
        super(message);
    }
}
