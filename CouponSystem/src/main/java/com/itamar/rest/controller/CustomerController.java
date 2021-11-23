package com.itamar.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.itamar.entities.Coupon;
import com.itamar.entities.Customer;
import com.itamar.rest.common.ClientSession;
import com.itamar.rest.controller.ex.TokenInvalidOrExpiredException;
import com.itamar.service.TokensManager;
import com.itamar.service.Customer.CustomerService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api")
public class CustomerController {
    private final CustomerService customerService;
    private final TokensManager tokensManager;
    private static final String CUSTOMER_ROLE = "customer";

    @Autowired
    public CustomerController(CustomerService customerService, TokensManager tokensManager) {
        this.customerService = customerService;
        this.tokensManager = tokensManager;
    }

    @GetMapping("/customers")
    public ResponseEntity<Customer> getCustomer(@RequestParam String token) throws TokenInvalidOrExpiredException {

        ClientSession clientSession = accessOrElseThrow(token, TokenInvalidOrExpiredException::new);

        Optional<Customer> optCustomer = customerService.getCustomer(clientSession.getId());

        return optCustomer.map(customer -> ResponseEntity.ok(customer))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/customers/coupons")
    public ResponseEntity<List<Coupon>> getAllCoupons(@RequestParam String token) throws TokenInvalidOrExpiredException {
        ClientSession clientSession = accessOrElseThrow(token, TokenInvalidOrExpiredException::new);

        List<Coupon> coupons = customerService.getAllCoupons(clientSession.getId());
        if (coupons.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(coupons);
    }

    @GetMapping("/customers/coupons/toPurchase")
    public ResponseEntity<List<Coupon>> getCouponsToPurchase(@RequestParam String token) throws TokenInvalidOrExpiredException {
        ClientSession clientSession = accessOrElseThrow(token, TokenInvalidOrExpiredException::new);

        List<Coupon> coupons = customerService.findCustomerCouponsToPurchase(clientSession.getId());
        if (coupons.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(coupons);
    }

    @GetMapping("/customers/coupons-before/{date}")
    public ResponseEntity<List<Coupon>> getAllCouponsBeforeDate(
            @RequestParam String token,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
            throws TokenInvalidOrExpiredException {

        // TODO: 16/04/2021 Check if local date is above now, if no - throw ex , else continue

        ClientSession clientSession = accessOrElseThrow(token, TokenInvalidOrExpiredException::new);
        List<Coupon> coupons = customerService.getAllCouponsBeforeDate(clientSession.getId(), date);

        if (coupons.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(coupons);
    }

    @PostMapping("/customers/coupons")
    public ResponseEntity<Customer> purchaseCoupon(@RequestParam String token, @RequestBody Coupon coupon) throws TokenInvalidOrExpiredException {
        ClientSession clientSession = accessOrElseThrow(token, TokenInvalidOrExpiredException::new);
        if (coupon != null) {
            Optional<Customer> optCustomer = customerService.addCoupon(clientSession.getId(), coupon);

            if (optCustomer.isPresent()) {
                return ResponseEntity.ok(optCustomer.get());
            }
        }
        return ResponseEntity.notFound().build();
    }

    private <X extends Throwable> ClientSession accessOrElseThrow(String token, Supplier<? extends X> supplier) throws X {
        ClientSession clientSession = tokensManager.accessOrElseThrow(token, supplier);

        if (clientSession.getRole().equalsIgnoreCase(CUSTOMER_ROLE)) {
            return clientSession.access();
        }
        throw supplier.get();
    }
}
