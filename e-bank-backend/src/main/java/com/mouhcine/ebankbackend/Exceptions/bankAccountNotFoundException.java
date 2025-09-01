package com.mouhcine.ebankbackend.Exceptions;

public class bankAccountNotFoundException extends Exception {
    public bankAccountNotFoundException(String bankAccountNotFind) {
        super(bankAccountNotFind);
    }
}
