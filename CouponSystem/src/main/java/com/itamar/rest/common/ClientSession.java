package com.itamar.rest.common;

public class ClientSession {
    private final long id;
    private long lastAccessMillis;
    private final String role;


    private ClientSession(long id, long lastAccessMillis, String role) {
        this.role = role;
        this.id = id;
        this.lastAccessMillis = lastAccessMillis;
    }

    public static ClientSession of(long id, String role) {
        return new ClientSession(id, System.currentTimeMillis(), role);
    }

    public String getRole() {
        return role;
    }

    public long getId() {
        return id;
    }

    public long getLastAccessMillis() {
        return lastAccessMillis;
    }

    public ClientSession access() {
        lastAccessMillis = System.currentTimeMillis();
        return this;
    }


}
