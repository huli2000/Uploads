package com.itamar.rest.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.itamar.rest.common.ErrorResponse;
import com.itamar.rest.controller.ex.CouponAccessForbiddenException;
import com.itamar.rest.controller.ex.TokenInvalidOrExpiredException;
import com.itamar.service.login.ex.InvalidLoginException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(InvalidLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleInvalidLogin(InvalidLoginException ex) {
        return ErrorResponse.from(ex);
    }

    @ExceptionHandler(TokenInvalidOrExpiredException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleTokenInvalidOrExpired(TokenInvalidOrExpiredException ex) {
        return ErrorResponse.from(ex);
    }

    @ExceptionHandler(CouponAccessForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse CouponAccessForbidden(CouponAccessForbiddenException ex) {
        return ErrorResponse.from(ex);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse MissingParamException(MissingServletRequestParameterException ex) {
        return ErrorResponse.from(ex);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse MissingBodyException(HttpMessageNotReadableException ex) {
        return ErrorResponse.from(ex);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse GlobalException(Exception ex) {
        return ErrorResponse.from(ex);
    }


}
