package com.itamar.service.login;


import com.itamar.rest.common.ClientSession;
import com.itamar.service.login.ex.InvalidLoginException;

public interface LoginService {
    /**
     * Create a session object for a fresh login of a single client.
     *
     * @param email    The email of the client.
     * @param password The password of the client.
     * @return A {@link ClientSession}
     * @throws InvalidLoginException I email and/or password are incorrect.
     */
    ClientSession createSession(String email, String password) throws InvalidLoginException;

    /**
     * @return A random token for identifying a client.
     */
    String generateToken();
}
