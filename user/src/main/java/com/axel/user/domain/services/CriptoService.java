package com.axel.user.domain.services;

import org.springframework.stereotype.Service;

@Service
public class CriptoService {

    public CriptoService() {}

    //encript Password
    public String encrypt(String password) {
        String encryptedPassword = password;

        //TODO
        //encriptar el password

        return encryptedPassword;
    }

    //decript Password
    public String decrypt(String encryptedPassword) {
        String password = encryptedPassword;

        //TODO
        //desencriptar el password

        return password;
    }
}
