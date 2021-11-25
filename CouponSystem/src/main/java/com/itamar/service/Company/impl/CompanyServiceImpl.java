package com.itamar.service.Company.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itamar.entities.Company;
import com.itamar.entities.Coupon;
import com.itamar.repo.CompanyRepo;
import com.itamar.repo.CouponsRepo;
import com.itamar.rest.controller.ex.CouponAccessForbiddenException;
import com.itamar.service.Company.CompanyService;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CouponsRepo couponsDao;
    private final CompanyRepo companyDao;

    @Autowired
    public CompanyServiceImpl(CouponsRepo couponsDao, CompanyRepo companyDao) {
        this.couponsDao = couponsDao;
        this.companyDao = companyDao;
    }

    @Override
    public Optional<Company> getCompany(Long id) {
        return companyDao.findById(id);
    }

    @Override
    public List<Coupon> getAllCoupons(Long id) {
        return couponsDao.findCompanyCoupons(id);
    }

    @Override
    public ResponseEntity deleteCoupon(Long companyId, Long couponId) throws CouponAccessForbiddenException {
        Optional<Coupon> optCoupon = couponsDao.findById(couponId);

        if (optCoupon.isEmpty()) {
            return ResponseEntity.notFound().build();

        } else if (optCoupon.get().getCompany().getId().equals(companyId)) {
            couponsDao.deleteById(couponId);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } else {
            throw new CouponAccessForbiddenException("The access to this coupon is forbidden, "
                                                     + "this coupon doesn't belong to this company");
        }
    }

    @Override
    public Optional<Coupon> addCoupon(Long companyId, Coupon coupon) {
        Optional<Company> optCompany = companyDao.findById(companyId);

        if (optCompany.isPresent() && coupon != null) {
            coupon.setCompany(optCompany.get());
            coupon.setId(0L);
            return Optional.of(couponsDao.save(coupon));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Coupon> updateCoupon(Long companyId, Coupon coupon) throws CouponAccessForbiddenException {
        if (companyId > 0 && coupon != null) {

            Optional<Company> optCompany = companyDao.findById(companyId);
            Optional<Coupon> optCoupon = couponsDao.findById(coupon.getId());

            if (optCompany.isPresent() && optCoupon.isPresent()) {

                if (optCoupon.get().getCompany().getId().equals(companyId)) {
                    return Optional.of(couponsDao.save(coupon));

                } else {
                    throw new CouponAccessForbiddenException("The access to this coupon is forbidden, "
                                                             + "this coupon doesn't belong to this company");
                }
            }

        }
        return Optional.empty();
    }
}
