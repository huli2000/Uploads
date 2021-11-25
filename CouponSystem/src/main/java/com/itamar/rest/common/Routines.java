package com.itamar.rest.common;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.itamar.entities.Coupon;
import com.itamar.repo.CouponsRepo;
import com.itamar.service.TokensManager;

import java.time.LocalDate;
import java.util.List;


@Component
public class Routines {
    private final CouponsRepo couponsDao;
    private final TokensManager tokensManager;

    private static final long CLIENT_SESSION_GRANT = 1_800_000; // 30 Minutes

    private static final long CHECK_INTERVAL_SESSION = 3_600_000; //1 Hour
    private static final long CHECK_INTERVAL_COUPONS = 86_400_000; //24 Hours


    public Routines(CouponsRepo couponsDao, TokensManager tokensManager) {

        this.couponsDao = couponsDao;
        this.tokensManager = tokensManager;
    }

    @Scheduled(fixedDelay = CHECK_INTERVAL_COUPONS)
    private void clearExpiredCoupons() {
        System.out.println("Start clean expired coupon operation....");

        List<Coupon> expiredCoupons = couponsDao.findExpiredCoupons();
        LocalDate today = LocalDate.now();

        if (expiredCoupons != null) {
            for (Coupon expiredCoupon : expiredCoupons) {
                couponsDao.deleteById(expiredCoupon.getId());
                System.out.println("Coupon with id:" + expiredCoupon.getId() + " is expired, deleting...");
            }
        }
        System.out.println("Done cleaning expired coupon operation.");
    }

    @Scheduled(fixedDelay = CHECK_INTERVAL_SESSION)
    private void ClearExpiredClientSessions() {
        System.out.println("Start clean expired client session operation....");
        if (!tokensManager.getTokensMap().isEmpty()) {
            for (String token : tokensManager.getTokensMap().keySet()) {
                ClientSession clientSession = tokensManager.getTokensMap().get(token);

                if (clientSession != null) {
                    if ((System.currentTimeMillis() - clientSession.getLastAccessMillis()) > CLIENT_SESSION_GRANT) {
                        System.out.println("The token: " + token + " is expired, deleting...");
                        tokensManager.getTokensMap().remove(token);
                    }
                }
            }
        }
        System.out.println("Done cleaning expired client session operation.");
    }

}
