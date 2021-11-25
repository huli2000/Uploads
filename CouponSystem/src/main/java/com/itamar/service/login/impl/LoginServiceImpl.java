package com.itamar.service.login.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itamar.repo.CompanyRepo;
import com.itamar.repo.CustomerRepo;
import com.itamar.rest.common.ClientSession;
import com.itamar.service.login.LoginService;
import com.itamar.service.login.ex.InvalidLoginException;

import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {

    private static final int LENGTH_TOKEN = 15;
    private final CompanyRepo companyDao;
    private final CustomerRepo customerDao;

    private static final String COMPANY_ROLE = "company";
    private static final String CUSTOMER_ROLE = "customer";

    @Autowired
    public LoginServiceImpl(CompanyRepo companyDao, CustomerRepo customerDao) {
        this.companyDao = companyDao;
        this.customerDao = customerDao;
    }

    @Override
    public ClientSession createSession(String email, String password) throws InvalidLoginException {

        if (companyDao.findByEmailAndPassword(email, password).isPresent()) {
            return ClientSession.of(
                    companyDao.findByEmailAndPassword(email, password)
                            .orElseThrow(InvalidLoginException::new)
                            .getId(), COMPANY_ROLE);
        } else if (customerDao.findByEmailAndPassword(email, password).isPresent()) {
            return ClientSession.of(
                    customerDao.findByEmailAndPassword(email, password)
                            .orElseThrow(InvalidLoginException::new)
                            .getId(), CUSTOMER_ROLE);
        }
        throw new InvalidLoginException();

    }

    @Override
    public String generateToken() {

        //23e4567-e89b-12d3-a456-426614174000
        return UUID.randomUUID()
                .toString()
                //23e4567e89b12d3a456426614174000
                .replaceAll("-", "")
                //23e4567e89b12d3
                .substring(0, LENGTH_TOKEN);
    }
}
