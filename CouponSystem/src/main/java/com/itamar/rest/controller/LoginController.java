package com.itamar.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itamar.rest.common.ClientSession;
import com.itamar.service.TokensManager;
import com.itamar.service.login.LoginService;
import com.itamar.service.login.ex.InvalidLoginException;


@RestController
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;
    private final TokensManager tokensManager;

    @Autowired
    public LoginController(LoginService loginService, TokensManager tokensManager) {
        this.loginService = loginService;
        this.tokensManager = tokensManager;
    }

    // http://localhost:8080/login?email=james@gmail.com&password=1234
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) throws InvalidLoginException {
        String token = loginService.generateToken();
        ClientSession session = loginService.createSession(email, password);

        tokensManager.put(token, session);

        return ResponseEntity.ok(token);
    }
}
