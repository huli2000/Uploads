package com.itamar.rest.controller.ex;

public class CouponAccessForbiddenException extends Exception{

    public CouponAccessForbiddenException() {
        super("The access to this coupon is forbidden");
    }

    public CouponAccessForbiddenException(String message) {
        super(message);
    }
}
