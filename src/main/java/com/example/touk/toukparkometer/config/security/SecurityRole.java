package com.example.touk.toukparkometer.config.security;

public enum SecurityRole {
    USER, OPERATOR, OWNER;

    private final String role;
    SecurityRole(){
        this.role = this.name();
    }

    public String getRole() {
        return role;
    }
}
