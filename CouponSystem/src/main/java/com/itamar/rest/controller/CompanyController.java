package com.itamar.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.itamar.entities.Company;
import com.itamar.entities.Coupon;
import com.itamar.rest.common.ClientSession;
import com.itamar.rest.controller.ex.CouponAccessForbiddenException;
import com.itamar.rest.controller.ex.TokenInvalidOrExpiredException;
import com.itamar.service.TokensManager;
import com.itamar.service.Company.CompanyService;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api")
public class CompanyController {
    private final CompanyService companyService;
    private final TokensManager tokensManager;
    private static final String COMPANY_ROLE = "company";

    @Autowired
    public CompanyController(CompanyService companyService, TokensManager tokensManager) {
        this.companyService = companyService;
        this.tokensManager = tokensManager;
    }

    @GetMapping("/companies")
    public ResponseEntity<Company> getCompany(@RequestParam String token) throws TokenInvalidOrExpiredException {
        ClientSession clientSession = accessOrElseThrow(token, TokenInvalidOrExpiredException::new);

        Optional<Company> optCompany = companyService.getCompany(clientSession.getId());

        return optCompany.map(company -> ResponseEntity.ok(company))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/companies/coupons")
    public ResponseEntity<List<Coupon>> getAllCoupons(@RequestParam String token) throws TokenInvalidOrExpiredException {
        ClientSession clientSession = accessOrElseThrow(token, TokenInvalidOrExpiredException::new);

        List<Coupon> coupons = companyService.getAllCoupons(clientSession.getId());
        if (coupons.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(coupons);
    }

    @DeleteMapping("/companies/coupons/{id}")
    public ResponseEntity DeleteCoupon(@RequestParam String token, @PathVariable Long id) throws TokenInvalidOrExpiredException, CouponAccessForbiddenException {
        ClientSession clientSession = accessOrElseThrow(token, TokenInvalidOrExpiredException::new);

        return companyService.deleteCoupon(clientSession.getId(), id);
    }

    @PostMapping("/companies/coupons")
    public ResponseEntity<Coupon> CreateCoupon(@RequestParam String token, @RequestBody Coupon coupon) throws TokenInvalidOrExpiredException {
        ClientSession clientSession = accessOrElseThrow(token, TokenInvalidOrExpiredException::new);

        if (coupon != null) {
            Optional<Coupon> optCoupon = companyService.addCoupon(clientSession.getId(), coupon);

            if (optCoupon.isPresent()) {
                return ResponseEntity.ok(optCoupon.get());
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/companies/coupons")
    public ResponseEntity<Coupon> UpdateCoupon(@RequestParam String token, @RequestBody Coupon coupon) throws TokenInvalidOrExpiredException, CouponAccessForbiddenException {
        ClientSession clientSession = accessOrElseThrow(token, TokenInvalidOrExpiredException::new);

        if (coupon != null) {
            Optional<Coupon> opt = companyService.updateCoupon(clientSession.getId(), coupon);

            if (opt.isPresent()) {
                return ResponseEntity.ok(opt.get());
            }
        }

        return ResponseEntity.notFound().build();
    }

    private <X extends Throwable> ClientSession accessOrElseThrow(String token, Supplier<? extends X> supplier) throws X {
        ClientSession clientSession = tokensManager.accessOrElseThrow(token, supplier);

        if (clientSession.getRole().equalsIgnoreCase(COMPANY_ROLE)) {
            return clientSession.access();
        }
        throw supplier.get();
    }

}
