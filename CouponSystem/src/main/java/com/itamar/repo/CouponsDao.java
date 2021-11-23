package com.itamar.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itamar.entities.Coupon;

import java.time.LocalDate;
import java.util.List;

public interface CouponsDao extends JpaRepository<Coupon, Long> {

    /**
     * find all coupons for the company in the data-source
     *
     * @param id The id of the company
     * @return collection of coupons
     */
    @Query("select c from Coupon c where c.company.id= :companyId")
    List<Coupon> findCompanyCoupons(@Param("companyId") Long id);

    /**
     * find all the coupons in the data-source for the given customer
     *
     * @param id The id of the customer
     * @return collection of coupons
     */
    @Query("select e from Coupon e inner join e.customers c where c.id=:customerId")
    List<Coupon> findCustomerCoupons(@Param("customerId") Long id);


    /**
     * find in the data-source all coupons that don't belong to a given customer
     *
     * @param id The id of the customer
     * @return collection of coupons
     */

    @Query(value = "SELECT * FROM coupon as c WHERE c.id NOT IN (SELECT coupon_id FROM customer_coupon where customer_id = :customerId)"
            , nativeQuery = true)
    List<Coupon> findCustomerCouponsToPurchase(@Param("customerId") Long id);

    /**
     * find all coupons for the customer in the data-source
     * with expiration date is before the given date
     *
     * @param date The date limit
     * @param id   The id of the company
     * @return collection of coupons
     */
    @Query("select e from Coupon e inner join e.customers c where c.id=:customerId and e.endDate<:date")
    List<Coupon> findCustomerCouponsBeforeDate(@Param("customerId") Long id, @Param("date") LocalDate date);

    /**
     * get all the coupons in the data-source that's expired
     *
     * @return collection of coupons
     */
    @Query("from Coupon where endDate < CURRENT_DATE")
    List<Coupon> findExpiredCoupons();
}
