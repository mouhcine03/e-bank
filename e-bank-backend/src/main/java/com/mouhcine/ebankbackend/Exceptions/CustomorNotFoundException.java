package com.mouhcine.ebankbackend.Exceptions;

public class CustomorNotFoundException extends Exception {
    public CustomorNotFoundException(String custumorNotFound) {
        super(custumorNotFound);

    }
}
