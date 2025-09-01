package com.mouhcine.ebankbackend.Exceptions;

public class BalanceInsufisentException extends Exception {
    public BalanceInsufisentException(String insuficientBalanceForDebitOperation) {
        super(insuficientBalanceForDebitOperation);
    }
}
