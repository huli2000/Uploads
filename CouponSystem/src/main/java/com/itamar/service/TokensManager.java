package com.itamar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.itamar.rest.common.ClientSession;

import java.util.Map;
import java.util.function.Supplier;

@Service
public class TokensManager {

    private final Map<String, ClientSession> tokensMap;

    @Autowired
    public TokensManager(@Qualifier("tokens") Map<String, ClientSession> tokensMap) {

        this.tokensMap = tokensMap;
    }

    public ClientSession put(String token, ClientSession session) {
        return tokensMap.put(token, session);
    }

    public <X extends Throwable> ClientSession accessOrElseThrow(String token, Supplier<? extends X> supplier) throws X {
        if (tokensMap.containsKey(token)) {
            return tokensMap.get(token);
        }

        throw supplier.get();
    }

    public Map<String, ClientSession> getTokensMap() {
        return tokensMap;
    }

}
