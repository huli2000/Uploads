package com.itamar.service.Company;

import org.springframework.http.ResponseEntity;

import com.itamar.entities.Company;
import com.itamar.entities.Coupon;
import com.itamar.rest.controller.ex.CouponAccessForbiddenException;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    /**
     * get company by id
     *
     * @param id id of the company
     * @return company
     */
    Optional<Company> getCompany(Long id);

    /**
     * get all coupons for the company
     *
     * @param id The id of the company
     * @return collection of coupons
     */
    List<Coupon> getAllCoupons(Long id);

    /**
     * Delete coupon from the company
     *  @param companyId the company id to delete from
     * @param couponId  the coupon id to delete
     * @return
     */
    ResponseEntity deleteCoupon(Long companyId, Long couponId) throws CouponAccessForbiddenException;

    /**
     * Create coupon for the company
     * @param companyId the company id to create for
     * @param coupon the coupon to create
     * @return The created coupon
     */
    Optional<Coupon> addCoupon(Long companyId, Coupon coupon);

    /**
     * Update coupon for the company
     * @param companyId the company id to update for
     * @param coupon the coupon to update
     * @return The updated coupon
     */
    Optional<Coupon> updateCoupon(Long companyId, Coupon coupon) throws CouponAccessForbiddenException;

}
