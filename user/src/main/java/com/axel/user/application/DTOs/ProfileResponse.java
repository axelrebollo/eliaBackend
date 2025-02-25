package com.axel.user.application.DTOs;

public class ProfileResponse {
    private String name;
    private String surname1;
    private String surname2;

    public ProfileResponse(String name, String surname1, String surname2) {
        this.name = name;
        this.surname1 = surname1;
        this.surname2 = surname2;
    }

    public String getName() {
        return name;
    }

    public String getSurname1() {
        return surname1;
    }

    public String getSurname2() {
        return surname2;
    }
}
