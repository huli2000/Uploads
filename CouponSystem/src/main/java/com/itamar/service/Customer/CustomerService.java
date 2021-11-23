package com.itamar.service.Customer;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.itamar.entities.Coupon;
import com.itamar.entities.Customer;

public interface CustomerService {

    /**
     * get customer by id
     *
     * @param id id of the customer
     * @return customer
     */
    Optional<Customer> getCustomer(Long id);

    /**
     * get all coupons for the customer
     *
     * @param id The id of the customer
     * @return collection of coupons
     */
    List<Coupon> getAllCoupons(Long id);

    /**
     * get all coupons that don't belong to a given customer
     *
     * @param id The id of the customer
     * @return collection of coupons
     */
    List<Coupon> findCustomerCouponsToPurchase(long id);

    /**
     * find all coupons for the customer with expiration date is before the given date
     *
     * @param date The date limit
     * @param id   The id of the customer
     * @return collection of coupons
     */
    List<Coupon> getAllCouponsBeforeDate(Long id, LocalDate date);


    /**
     * add coupon to customer
     *
     * @param customerId the customer id
     * @param coupon     the coupon to insert
     * @return the coupon that's bought
     */
    Optional<Customer> addCoupon(Long customerId, Coupon coupon);

}
