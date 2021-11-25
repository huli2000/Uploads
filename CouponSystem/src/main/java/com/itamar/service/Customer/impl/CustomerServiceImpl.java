package com.itamar.service.Customer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itamar.entities.Coupon;
import com.itamar.entities.Customer;
import com.itamar.repo.CouponsRepo;
import com.itamar.repo.CustomerRepo;
import com.itamar.service.Customer.CustomerService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CouponsRepo couponsDao;
    private final CustomerRepo customerDao;


    @Autowired
    public CustomerServiceImpl(CouponsRepo couponsDao, CustomerRepo customerDao) {
        this.couponsDao = couponsDao;
        this.customerDao = customerDao;
    }

    @Override
    public Optional<Customer> getCustomer(Long id) {
        return customerDao.findById(id);
    }

    @Override
    public List<Coupon> getAllCoupons(Long id) {
        return couponsDao.findCustomerCoupons(id);
    }

    @Override
    public List<Coupon> findCustomerCouponsToPurchase(long id) {
        return couponsDao.findCustomerCouponsToPurchase(id);
    }


    @Override
    public List<Coupon> getAllCouponsBeforeDate(Long id, LocalDate date) {
        return couponsDao.findCustomerCouponsBeforeDate(id, date);
    }

    @Override
    public Optional<Customer> addCoupon(Long customerId, Coupon coupon) {
        Optional<Customer> customer = customerDao.findById(customerId);

        if (coupon != null && customer.isPresent()) {
            customer.get().getCoupons().add(coupon);
            customerDao.save(customer.get());

            //Decrement coupon amount
            Optional<Coupon> optCoupon = couponsDao.findById(coupon.getId());
            if (optCoupon.isPresent()) {
                int couponAmount = optCoupon.get().getAmount();

                if (couponAmount > 0) {
                    optCoupon.get().setAmount(couponAmount - 1);
                    couponsDao.save(optCoupon.get());
                }
            }

            return customer;
        }
        return Optional.empty();
    }

}
