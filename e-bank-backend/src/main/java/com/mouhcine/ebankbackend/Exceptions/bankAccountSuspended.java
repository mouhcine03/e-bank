package com.mouhcine.ebankbackend.Exceptions;

public class bankAccountSuspended extends Exception {
    public bankAccountSuspended(String bankAcountSuspended) {
        super(bankAcountSuspended);
    }
}
